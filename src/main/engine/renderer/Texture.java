package engine.renderer;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {
    private String fileName;
    private int width, height;
    private int textureID;
    private int components;
    private ByteBuffer image;

    public Texture() {
        textureID = -1;
        width = -1;
        height = -1;
    }

    public Texture(String fileName) {

        this.fileName = fileName;

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            // Decode the image
            image = stbi_load(fileName, w, h, comp, 0);
            if (image == null) {
                throw new RuntimeException("Failed to load image in path <" + fileName + ">: " + stbi_failure_reason());
            }

            this.width = w.get(0);
            this.height = h.get(0);
            this.components = comp.get(0);

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

            int format;
            if (components == 3) {
                if ((width & 3) != 0) {
                    glPixelStorei(GL_UNPACK_ALIGNMENT, 2 - (width & 1));
                }
                format = GL_RGB;

            } else {
                premultiplyAlpha();

                glEnable(GL_BLEND);
                glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

                format = GL_RGBA;
            }

            glTexImage2D(GL_TEXTURE_2D, 0, format, width, height, 0, format, GL_UNSIGNED_BYTE, image);
            glGenerateMipmap(GL_TEXTURE_2D);

            if (GL11.glGetError() != 0)
                System.out.println("Bug: " + GL11.glGetError());

            stbi_image_free(image);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void premultiplyAlpha() {
        int stride = width * 4;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int i = y * stride + x * 4;

                float alpha = (image.get(i + 3) & 0xFF) / 255.0f;
                image.put(i + 0, (byte) Math.round(((image.get(i + 0) & 0xFF) * alpha)));
                image.put(i + 1, (byte) Math.round(((image.get(i + 1) & 0xFF) * alpha)));
                image.put(i + 2, (byte) Math.round(((image.get(i + 2) & 0xFF) * alpha)));
            }
        }
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, textureID);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public String getFileName() {
        return fileName;
    }

    public int getTextureID() {
        return textureID;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void free() {
        glDeleteTextures(textureID);
    }
}
