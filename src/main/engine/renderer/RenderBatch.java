package engine.renderer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class RenderBatch {

    protected final int VERTEX_SIZE;
    protected final int VERTEX_SIZE_BYTES;

    protected int vaoID, vboID;
    protected float[] vertices;

    protected int zIndex = 0;
    protected int maxBatchSize = 0;
    protected Shader shader;

    public RenderBatch(int zIndex, int maxBatchSize, int[] vertexAttribute) {
        this.zIndex = zIndex;
        this.maxBatchSize = maxBatchSize;

        // Calculate vertex attributes
        int offset = 0;
        int size = 0;

        for (int i = 0; i < vertexAttribute.length; i++) {
            size += vertexAttribute[i];
        }

        VERTEX_SIZE = size;
        VERTEX_SIZE_BYTES = VERTEX_SIZE * Float.BYTES;
        vertices = new float[maxBatchSize * 4 * VERTEX_SIZE];
        size = 0;

        // Generate and bind a Vertex Array Object
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Allocate space for vertices
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        // Create and upload indices buffer
        int eboID = glGenBuffers();
        int[] indices = generateIndices();

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        for (int i = 0; i < vertexAttribute.length; i++) {
            // Enable the buffer attribute pointers
            offset += size;
            size = vertexAttribute[i];
            glVertexAttribPointer(i, size, GL_FLOAT, false, VERTEX_SIZE_BYTES, offset * Float.BYTES);
            glEnableVertexAttribArray(i);

        }
    }

    public abstract void render();

    // Test square
    // glBegin(GL_QUADS);
    // glVertex2f(0.0f, 0.0f);
    // glVertex2f(1.0f, 0.0f);
    // glVertex2f(1.0f, 1.0f);
    // glVertex2f(0.0f, 1.0f);
    // glEnd();

    public abstract boolean hasRoom();

    public void draw(Object object) {

    }

    protected abstract int[] generateIndices();

    public int getZIndex() {
        return zIndex;
    }
}
