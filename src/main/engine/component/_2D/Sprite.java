package engine.component._2D;

import engine.component.GraphicComponent;
import engine.renderer.AssetPool;
import engine.renderer.Texture;
import engine.renderer.VisualServer;


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
        setDirty();
    }

    @Override
    public void free() {
        this.setAlive(false);
        this.texture.free();
    }
}
