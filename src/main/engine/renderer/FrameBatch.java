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
import java.util.List;

import engine.Window;
import engine.node.UI.Color;
import engine.node._2D.Frame;
import engine.node._2D.Transform2D;

public class FrameBatch extends RenderBatch {

    private static final int[] vertexAttribute = { //
            2, // Position size
            4, // Color size
    };

    private List<Frame> frames = new ArrayList<Frame>(); // Max 8 textures for each RenderBatch

    public FrameBatch(int zIndex, int maxBatchSize) {
        super(zIndex, maxBatchSize, vertexAttribute);
        this.shader = new Shader("D:/Java/Project/assets/shaders/FrameShader.glsl");

    }

    @Override
    public void render() {

        Iterator<Frame> iter = frames.iterator();
        Frame frame;
        int frameIndex = 0;

        while (iter.hasNext()) {
            frame = iter.next();

            if (!frame.isAlive())
                iter.remove();

            else {
                if (frame.isDirty()) {
                    loadVertexProperties(frame, frameIndex);
                    frameIndex += 1;
                }
            }
        }

        // If there is frame need to be re-buffer
        if (frameIndex > 0) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        }

        glDisable(GL_TEXTURE_2D);

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

        Color color = frame.getColor();

        // Add vertices with the appropriate properties
        Vector2f cameraSize = Window.getScene().getCamera().getSize();
        Vector2f cameraPosition = Window.getScene().getCamera().getPosition();
        float aspectRatio = Window.getScene().getCamera().getAspectRatio();

        Transform2D transform = frame.getGlobalTransform();

        // System.out.println(transform);

        float positionX = (transform.getPosition().x - cameraPosition.x) / cameraSize.x;
        float positionY = (transform.getPosition().y - cameraPosition.y) / cameraSize.y * aspectRatio;

        float sizeX = transform.getSize().x / cameraSize.x;
        float sizeY = transform.getSize().y / cameraSize.y * aspectRatio;

        float offsetX = sizeX / 2;
        float offsetY = sizeY / 2;

        Vector4f currentPosition;

        boolean isRotated = transform.getRotation() != 0.0f;
        Matrix4f transformMatrix = new Matrix4f().identity();

        if (isRotated) {
            transformMatrix.translate(positionX, positionY, 0f);
            transformMatrix.rotate((float) Math.toRadians(transform.getRotation()), 0, 0, 1);
            transformMatrix.scale(transform.getScale().x, transform.getScale().y, 1);
        }

        for (int i = 0; i < 4; i++) {
            if (i == 1) {
                offsetY = -sizeY / 2;
            } else if (i == 2) {
                offsetX = -sizeX / 2;
            } else if (i == 3) {
                offsetY = sizeY / 2;
            }

            currentPosition = new Vector4f(positionX + offsetX * transform.getScale().x, positionY + offsetY * transform.getScale().x, 0, 1);

            if (isRotated)
                currentPosition = new Vector4f(offsetX, offsetY, 0, 1).mul(transformMatrix);

            loadVertexProperties(offset, currentPosition, color);
            offset += VERTEX_SIZE;
        }
    }

    private void loadVertexProperties(int offset, Vector4f position, Color color) {
        // Load position
        vertices[offset] = position.x;
        vertices[offset + 1] = position.y;
        // Load color
        vertices[offset + 2] = color.r;
        vertices[offset + 3] = color.g;
        vertices[offset + 4] = color.b;
        vertices[offset + 5] = color.a;

        vertices[offset + 6] = 0;
        vertices[offset + 7] = 0;
        vertices[offset + 8] = 0;
        vertices[offset + 9] = 1;
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
