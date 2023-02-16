package engine.component._2D;

import engine.KeyListener;
import engine.component.GraphicComponent;
import engine.renderer.AssetPool;
import engine.renderer.Texture;
import engine.renderer.VisualServer;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2f;

public class Sprite extends GraphicComponent {

    private Texture texture = new Texture();

    public Sprite(String fileName) {
        this.texture = AssetPool.getTexture(fileName);
        VisualServer.draw(this);
    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        this.isDirty = true;
    }

    @Override
    public void free() {
        this.isAlive = false;
        this.texture.free();
    }

    @Override
    public void update(float deltaTime) {
        if (KeyListener.isKeyPressed(GLFW_KEY_D))
            transform.rotation += 55 * deltaTime;
        else if (KeyListener.isKeyPressed(GLFW_KEY_A))
            transform.rotation -= 55 * deltaTime;

        Vector2f dir = new Vector2f((float) Math.cos(Math.toRadians(transform.rotation + 90)), (float) Math.sin(
                Math.toRadians(transform.rotation + 90)));
        dir.mul(deltaTime);

        if (KeyListener.isKeyPressed(GLFW_KEY_W))
            transform.position.sub(dir);
        else if (KeyListener.isKeyPressed(GLFW_KEY_S))
            transform.position.add(dir);
    }
}
