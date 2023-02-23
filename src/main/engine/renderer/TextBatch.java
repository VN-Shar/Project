package engine.renderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import engine.Window;
import engine.node.UI.Color;
import engine.node._2D.Text;
import engine.node._2D.Transform2D;
import engine.node._2D.FlagType.PositionType;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;
import org.lwjgl.stb.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.stb.STBTruetype.*;
import static org.lwjgl.system.MemoryStack.*;

public class TextBatch extends RenderBatch {

    private static final int[] vertexAttribute = { //
            2, // Position size
            4, // Color size
            2, // Texture coords size
            1, // Font id size
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
                    textIndex += text.getText().length();
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

        for (int i = 0; i < fonts.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            fonts.get(i).bind();
        }

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // 6 vertices per texture
        glDrawElements(GL_TRIANGLES, textIndex * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        shader.unbind();
        // Unbind all textures
        for (int i = 0; i < fonts.size(); i++) {
            fonts.get(i).unbind();
        }
    }

    private void loadVertexProperties(Text text, int index) {

        Font font = text.getFont();

        String content = text.getText();
        float scale = stbtt_ScaleForPixelHeight(font.getFontInfo(), font.getFontHeight());

        try (MemoryStack stack = stackPush()) {
            IntBuffer codePointBuffer = stack.mallocInt(1);
            IntBuffer pAdvancedWidth = stack.mallocInt(1);
            IntBuffer pLeftSideBearing = stack.mallocInt(1);

            float width = 0;
            int i = 0;
            int to = content.length() - 1;
            int lastSpaceIndex = 0;

            StringBuilder string = new StringBuilder(content);

            while (i < to) {
                if (text.getTransform().getSize().x == 1)
                    return;

                i += getCodePoint(content, to, i, codePointBuffer);
                int charCode = codePointBuffer.get(0);

                if (charCode == '\n')
                    width = 0;

                else if (charCode == ' ')
                    lastSpaceIndex = i - 1;

                stbtt_GetCodepointHMetrics(font.getFontInfo(), charCode, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if (text.isKerning && i < to) {
                    getCodePoint(content, to, i, codePointBuffer);
                    width += stbtt_GetCodepointKernAdvance(font.getFontInfo(), charCode, codePointBuffer.get(0));
                }

                if (width * scale > text.getTransform().getSize().x) {
                    string.setCharAt(lastSpaceIndex, '\n');
                    i = lastSpaceIndex;
                    width = 0;
                }
            }
            content = string.toString();
        }

        try (MemoryStack stack = stackPush()) {
            IntBuffer codePointBuffer = stack.mallocInt(1);

            FloatBuffer xBuffer = stack.floats(0);
            FloatBuffer yBuffer = stack.floats(0);

            // Find offset within array (4 vertices per text)

            STBTTAlignedQuad quad = STBTTAlignedQuad.malloc(stack);

            int charCode;
            float lineY = 0;
            float textSizeX = 0, textSizeY = 0;

            Transform2D transform = text.getGlobalTransform();

            List<Vector2f> positions = new ArrayList<Vector2f>(4 * content.length());
            List<Vector2f> texCoords = new ArrayList<Vector2f>(4 * content.length());

            float lineGap = (font.getAscent() - font.getDescent() + font.getLineGap()) * scale;

            for (int i = 0, to = content.length(); i < to;) {
                i += getCodePoint(content, to, i, codePointBuffer);

                charCode = codePointBuffer.get(0);

                // New line when reach \n
                if (charCode == '\n') {

                    yBuffer.put(0, lineY = lineY + lineGap / 2);
                    xBuffer.put(0, 0.0f);
                    continue;
                }
                try {

                    // Get font quad and texture coordinate
                    stbtt_GetBakedQuad(font.getCharData(), font.getBitmapWidth(), font.getBitmapHeight(), charCode - 32, xBuffer, yBuffer, quad,
                            true);

                    if (text.isKerning && i < to) {
                        getCodePoint(content, to, i, codePointBuffer);
                        xBuffer.put(0, xBuffer.get(0) + stbtt_GetCodepointKernAdvance(font.getFontInfo(), charCode, codePointBuffer.get(0)) * scale);
                    }
                    float x0 = quad.x0(), x1 = quad.x1(), y0 = quad.y0() + lineY + lineGap, y1 = quad.y1() + lineY + lineGap;

                    if (x1 > transform.getSize().x || y1 > transform.getSize().y)
                        continue;

                    textSizeX = Math.max(x1, textSizeX);
                    textSizeY = Math.max(y1, textSizeY);

                    positions.add(new Vector2f(x0, y0));
                    positions.add(new Vector2f(x1, y0));
                    positions.add(new Vector2f(x1, y1));
                    positions.add(new Vector2f(x0, y1));

                    texCoords.add(new Vector2f(quad.s0(), quad.t0()));
                    texCoords.add(new Vector2f(quad.s1(), quad.t0()));
                    texCoords.add(new Vector2f(quad.s1(), quad.t1()));
                    texCoords.add(new Vector2f(quad.s0(), quad.t1()));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            int fontId = fonts.indexOf(font);
            int offset = index * 4 * VERTEX_SIZE;
            Color color = text.getColor();

            Vector2f cameraSize = Window.getScene().getCamera().getSize();
            Vector2f cameraPosition = Window.getScene().getCamera().getPosition();
            float aspectRatio = Window.getScene().getCamera().getAspectRatio();

            float positionX = (transform.getPosition().x - cameraPosition.x) / cameraSize.x;
            float positionY = (transform.getPosition().y - cameraPosition.y) / cameraSize.y * aspectRatio;

            float sizeX = transform.getSize().x;
            float sizeY = transform.getSize().y * aspectRatio;

            Vector4f currentPosition;

            boolean isRotated = transform.getRotation() != 0.0f;
            Matrix4f transformMatrix = new Matrix4f().identity();

            float offsetX = 0;
            float offsetY = 0;

            PositionType positionType = text.getPositionType();

            switch (positionType) {
            case TOP_LEFT:
                offsetX = 0;
                offsetY = 0;
                break;
            case CENTER:
                offsetX = sizeX / 2;
                offsetY = sizeY / 2;
                break;
            }

            switch (text.verticalAlignment) {
            case BEGIN:
                offsetX += 0;
                break;

            case CENTER:
                offsetX += (transform.getSize().x - textSizeX) / 2;
                break;

            case END:
                offsetX += (transform.getSize().x - textSizeX);
                break;
            }

            switch (text.horizontalAlignment) {
            case BEGIN:
                offsetY += 0;
                break;

            case CENTER:
                offsetY += (transform.getSize().y - textSizeY) / 2;
                break;

            case END:
                offsetY += (transform.getSize().y - textSizeY);
                break;
            }

            if (isRotated) {
                transformMatrix.translate(positionX, positionY, 0f);
                transformMatrix.rotate((float) Math.toRadians(transform.getRotation()), 0, 0, 1);
                transformMatrix.scale(transform.getScale().x, transform.getScale().y, 1);
            }

            for (int i = 0; i < positions.size(); i++) {

                if (isRotated)
                    currentPosition = new Vector4f(//
                            (positions.get(i).x + offsetX) / cameraSize.x, //
                            (positions.get(i).y + offsetY) / cameraSize.y, 0, 1).mul(transformMatrix);
                else
                    currentPosition = new Vector4f(//
                            positionX + (positions.get(i).x + offsetX) / cameraSize.x * transform.getScale().x,
                            positionY + (positions.get(i).y + offsetY) / cameraSize.y * transform.getScale().y, 0, 1);

                loadVertexProperties(offset, currentPosition, color, texCoords.get(i), fontId);
                offset += VERTEX_SIZE;

            }
        }
    }

    private void loadVertexProperties(int offset, Vector4f position, Color color, Vector2f texCoord, int fontId) {
        // Load position

        vertices[offset] = position.x;
        vertices[offset + 1] = position.y;

        // Load color
        vertices[offset + 2] = color.r;
        vertices[offset + 3] = color.g;
        vertices[offset + 4] = color.b;
        vertices[offset + 5] = color.a;

        // Load texture coordinates
        vertices[offset + 6] = texCoord.x;
        vertices[offset + 7] = texCoord.y;

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

            String content = text.getText();

            int i = from;
            while (i < to) {
                i += getCodePoint(content, to, i, codePointBuffer);
                int charCode = codePointBuffer.get(0);

                stbtt_GetCodepointHMetrics(info, charCode, pAdvancedWidth, pLeftSideBearing);
                width += pAdvancedWidth.get(0);

                if (text.isKerning && i < to) {
                    getCodePoint(content, to, i, codePointBuffer);
                    width += stbtt_GetCodepointKernAdvance(info, charCode, codePointBuffer.get(0));
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
