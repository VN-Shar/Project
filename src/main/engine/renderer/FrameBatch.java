package engine.renderer;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import engine.Window;
import engine.component._2D.Frame;

public class FrameBatch extends RenderBatch {

    private static final int[] vertexAttribute = {
            2, // Position size
            4, // Color size
            2, // Frame coords size
            1, // Frame id size
    };

    private List<Frame> frames = new ArrayList<Frame>(); // Max 8 textures for each RenderBatch

    private Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public FrameBatch(int zIndex, int maxBatchSize) {
        super(zIndex, maxBatchSize, vertexAttribute);
        this.shader = new Shader("D:/Java/Project/assets/shaders/SpriteShader.glsl");

    }

    @Override
    public void render() {

        Iterator<Frame> iter = frames.iterator();
        Frame frame;
        int spriteIndex = 0;

        while (iter.hasNext()) {
            frame = iter.next();

            if (!frame.isAlive())
                iter.remove();

            else {
                if (frame.isDirty()) {
                    loadVertexProperties(frame, spriteIndex);
                    spriteIndex += 1;
                }
            }
        }

        // If there is frame need to be re-buffer
        if (spriteIndex > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        shader.bind();
        shader.uploadMat4f("uProjection", Window.getScene().getCamera().getProjectionMatrix());
        shader.uploadMat4f("uView", Window.getScene().getCamera().getViewMatrix());

        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        // Test square
        // glBegin(GL_QUADS);
        // glVertex2f(0.0f, 0.0f);
        // glVertex2f(1.0f, 0.0f);
        // glVertex2f(1.0f, 1.0f);
        // glVertex2f(0.0f, 1.0f);
        // glEnd();

        glDrawElements(GL_TRIANGLES, frames.size() * 6, GL_UNSIGNED_INT, 0);

        glBindVertexArray(0);
        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        shader.unbind();
    }

    private void loadVertexProperties(Frame frame, int index) {
        // Find offset within array (4 vertices per frame)
        int offset = index * 4 * VERTEX_SIZE;

        Vector4f color = frame.getColor();

        int texId = 100;

        boolean isRotated = frame.transform.rotation != 0.0f;
        Matrix4f transformMatrix = new Matrix4f().identity();

        if (isRotated) {
            transformMatrix.translate(frame.transform.position.x, frame.transform.position.y, 0f);
            transformMatrix.rotate(
                    (float) Math.toRadians(frame.transform.rotation),
                    0,
                    0,
                    1);
            transformMatrix.scale(frame.transform.scale.x,
                    frame.transform.scale.y * Window.getScene().getCamera()
                            .getAspectRatio(),
                    1);
        }

        // Add vertices with the appropriate properties
        float xOffset = 0.5f;
        float yOffset = 0.5f;
        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                yOffset = -0.5f;
            } else if (i == 2) {
                xOffset = -0.5f;
            } else if (i == 3) {
                yOffset = 0.5f;
            }

            Vector4f currentPosition = new Vector4f(
                    frame.transform.position.x + (xOffset * frame.transform.scale.x),
                    frame.transform.position.y
                            + (yOffset * frame.transform.scale.y * Window.getScene().getCamera().getAspectRatio()),
                    0, 1);

            if (isRotated) {
                currentPosition = new Vector4f(xOffset, yOffset, 0, 1).mul(transformMatrix);
            }

            loadVertexProperties(offset, currentPosition, color, texCoords[i], texId);
            offset += VERTEX_SIZE;
        }
    }

    private void loadVertexProperties(int offset, Vector4f currentPosition, Vector4f color,
            Vector2f texCoords, int texId) {
        // Load position
        vertices[offset] = currentPosition.x;
        vertices[offset + 1] = currentPosition.y;

        // Load color
        vertices[offset + 2] = color.x;
        vertices[offset + 3] = color.y;
        vertices[offset + 4] = color.z;
        vertices[offset + 5] = color.w;

        // Load texture coordinates
        vertices[offset + 6] = texCoords.x;
        vertices[offset + 7] = texCoords.y;

        // Load texture id
        vertices[offset + 8] = texId;

    }

    public void draw(Frame frame) {
        frames.add(frame);
    }

    public boolean hasRoom() {
        return frames.size() < maxBatchSize - 1;
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

    public int getZIndex() {
        return zIndex;
    }
}
