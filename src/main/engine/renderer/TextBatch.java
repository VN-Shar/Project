package engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import engine.Window;
import engine.component._2D.Text;
import engine.util.Font;

import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;

public class TextBatch extends RenderBatch {

    private static final int[] vertexAttribute = { 2, // Position size
            4, // Color size
            2, // Texture coords size
            1 // Font id
    };
    private static final int[] FONT_SLOTS = { 0, 1, 2, 3, 4, 5, 6, 7 };

    private List<Text> texts = new LinkedList<Text>();
    private List<Font> fonts = new ArrayList<Font>();

    public TextBatch(int zIndex, int maxBatchSize) {
        super(zIndex, maxBatchSize, vertexAttribute);
        this.shader = new Shader("D:/Java/Project/assets/shaders/TextShader.glsl");

    }

    @Override
    public void render() {

        Iterator<Text> iter = texts.iterator();
        Text text;
        int textIndex = 0;

        while (iter.hasNext()) {
            text = iter.next();

            if (!text.isAlive())
                iter.remove();

            else {
                if (text.isDirty()) {
                    loadVertexProperties(text, textIndex);
                    textIndex += text.content.length();
                }
            }
        }

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // If there is text need to be re-buffer
        if (textIndex > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        shader.bind();
        shader.uploadMat4f("uProjection", Window.getScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().getCamera().getViewMatrix());
        shader.uploadIntArray("uTextures", FONT_SLOTS);
        // Test square

        for (int i = 0; i < fonts.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            fonts.get(i).bind();
        }

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, texts.size() * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        shader.unbind();

        for (int i = 0; i < fonts.size(); i++) {
            fonts.get(i).unbind();
        }
    }

    private void loadVertexProperties(Text text, int index) {

        // Add vertices with the appropriate properties
        Vector2f cameraSize = Window.getScene().getCamera().getSize();
        float aspectRatio = Window.getScene().getCamera().getAspectRatio();

        Vector4f currentPosition;

        boolean isRotated = text.transform.rotation != 0.0f;
        Matrix4f transformMatrix = new Matrix4f().identity();

        if (isRotated) {
            transformMatrix.translate(text.transform.position.x / cameraSize.x, text.transform.position.y / cameraSize.y * aspectRatio, 0f);
            transformMatrix.rotate((float) Math.toRadians(text.transform.rotation), 0, 0, 1);
            transformMatrix.scale(text.transform.scale.x, text.transform.scale.y, 1);

        }
        currentPosition = new Vector4f(text.transform.position.x / cameraSize.x, (text.transform.position.y / cameraSize.y) * aspectRatio, 0, 1);

        if (isRotated)
            currentPosition = new Vector4f(0, 0 * aspectRatio, 0, 1).mul(transformMatrix);

        Font font = text.getFont();
        int fontId = fonts.indexOf(font);
        float factorX = 1.0f / cameraSize.x;
        float factorY = 1.0f / cameraSize.y;

        float scale = stbtt_ScaleForPixelHeight(font.getFontInfo(), font.getFontHeight());

        try (MemoryStack stack = stackPush()) {
            IntBuffer codePointBuffer = stack.mallocInt(1);

            FloatBuffer xBuffer = stack.floats(0);
            FloatBuffer yBuffer = stack.floats(0);

            // Find offset within array (4 vertices per text)
            int offset = index * 4 * VERTEX_SIZE;
            Vector4f color = text.color;
            color.x = 0;

            STBTTAlignedQuad quad = STBTTAlignedQuad.malloc(stack); // TexCoords

            float codePointX;

            float lineY = currentPosition.y;
            String content = text.content;

            for (int i = 0, to = content.length(); i < to;) {
                i += getCodePoint(content, to, i, codePointBuffer);

                int cp = codePointBuffer.get(0);
                if (cp == '\n') {

                    yBuffer.put(0, lineY = yBuffer.get(0) - (font.getAscent() - font.getDescent() + font.getLineGap()) * scale);
                    xBuffer.put(0, 0.0f);

                    continue;

                } else if (cp < 32 || 128 <= cp) {
                    continue;
                }

                codePointX = xBuffer.get(0);
                stbtt_GetBakedQuad(font.getCharData(), font.getBitmapWidth(), font.getBitmapHeight(), cp - 32, xBuffer, yBuffer, quad, true);
                xBuffer.put(0, scale(codePointX, xBuffer.get(0), factorX));

                if (text.isKerning && i < to) {
                    getCodePoint(content, to, i, codePointBuffer);
                    xBuffer.put(0, xBuffer.get(0) + stbtt_GetCodepointKernAdvance(font.getFontInfo(), cp, codePointBuffer.get(0)) * scale);
                }

                float x0 = scale(codePointX, quad.x0(), factorX), x1 = scale(codePointX, quad.x1(), factorX), y0 = scale(lineY, quad.y0(), factorY), y1 = scale(lineY, quad.y1(), factorY);

                 font.bind();

                glBegin(GL_QUADS);
                glTexCoord2f(quad.s0(), quad.t0());
                glVertex2f(x0, -y0);

                glTexCoord2f(quad.s1(), quad.t0());
                glVertex2f(x1, -y0);

                glTexCoord2f(quad.s1(), quad.t1());
                glVertex2f(x1, -y1);

                glTexCoord2f(quad.s0(), quad.t1());
                glVertex2f(x0, -y1);

                glEnd();

                x0 *= 2;
                x1 *= 3;
                

                loadVertexProperties(offset, x0, -y0, color, quad.s0(), quad.t0(), fontId);
                offset += VERTEX_SIZE;

                loadVertexProperties(offset, x1, -y0, color, quad.s1(), quad.t0(), fontId);
                offset += VERTEX_SIZE;

                loadVertexProperties(offset, x1, -y1, color, quad.s1(), quad.t1(), fontId);
                offset += VERTEX_SIZE;

                loadVertexProperties(offset, x0, -y1, color, quad.s0(), quad.t1(), fontId);
                offset += VERTEX_SIZE;
            }
        }
    }

    private static float scale(float center, float offset, float factor) {
        return (offset - center) * factor + center;
    }

    private void loadVertexProperties(int offset, float positionX, float positionY, Vector4f color, float texCoordX, float texCoordY, int fontId) {
        // Load position

        vertices[offset] = positionX;
        vertices[offset + 1] = positionY;

        // Load color
        vertices[offset + 2] = color.x;
        vertices[offset + 3] = color.y;
        vertices[offset + 4] = color.z;
        vertices[offset + 5] = color.w;

        // Load texture coordinates
        vertices[offset + 6] = texCoordX;
        vertices[offset + 7] = texCoordY;

        vertices[offset + 8] = fontId;
    }

    public void draw(Text text) {
        texts.add(text);
        Font font = text.getFont();
        if (!fonts.contains(font))
            fonts.add(font);

    }

    public boolean hasRoom() {
        return texts.size() < maxBatchSize - 1 && fonts.size() < 8;
    }

    protected int[] generateIndices() {
        // 6 indices per quad (3 per triangle)
        int[] elements = new int[maxBatchSize * 6];

        for (int i = 0; i < maxBatchSize; i++) {
            int arrayIndexOffset = 6 * i;
            int indicesOffset = 4 * i;

            // 3, 2, 0, 0, 2, 1 7, 6, 4, 4, 6, 5
            // Triangle 1
            elements[arrayIndexOffset] = indicesOffset + 3;
            elements[arrayIndexOffset + 1] = indicesOffset + 2;
            elements[arrayIndexOffset + 2] = indicesOffset + 0;

            // Triangle 2
            elements[arrayIndexOffset + 3] = indicesOffset + 0;
            elements[arrayIndexOffset + 4] = indicesOffset + 2;
            elements[arrayIndexOffset + 5] = indicesOffset + 1;
        }

        return elements;
    }

    public float getStringWidth(STBTTFontinfo info, Text text, int from, int to, int fontHeight) {
        int width = 0;
        try (MemoryStack stack = stackPush()) {
            IntBuffer codePointBuffer = stack.mallocInt(1);
            IntBuffer pAdvancedWidth = stack.mallocInt(1);
            IntBuffer pLeftSideBearing = stack.mallocInt(1);

            String content = text.content;
            int i = from;
            while (i < to) {
                i += getCodePoint(content, to, i, codePointBuffer);
                int cp = codePointBuffer.get(0);

                stbtt_GetCodepointHMetrics(info, cp, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if (text.isKerning && i < to) {
                    getCodePoint(content, to, i, codePointBuffer);
                    width += stbtt_GetCodepointKernAdvance(info, cp, codePointBuffer.get(0));
                }
            }
        }

        return width * stbtt_ScaleForPixelHeight(info, fontHeight);
    }

    private static int getCodePoint(String text, int to, int i, IntBuffer cpOut) {
        char c1 = text.charAt(i);
        if (Character.isHighSurrogate(c1) && i + 1 < to) {
            char c2 = text.charAt(i + 1);
            if (Character.isLowSurrogate(c2)) {
                cpOut.put(0, Character.toCodePoint(c1, c2));
                return 2;
            }
        }
        cpOut.put(0, c1);
        return 1;
    }
}
