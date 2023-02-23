package engine.node._2D;

import engine.node.Node2D;
import engine.node._2D.FlagType.PositionType;
import engine.renderer.AssetPool;
import engine.renderer.Texture;
import engine.renderer.VisualServer;

public class Sprite extends Node2D {

    private Texture texture = new Texture();

    public Sprite(String fileName) {
        this.texture = AssetPool.getTexture(fileName);
        setPositionType(PositionType.CENTER);
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
