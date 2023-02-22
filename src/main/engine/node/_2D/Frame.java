package engine.node._2D;

import org.joml.Vector2f;

import engine.node.Node2D;
import engine.renderer.VisualServer;

public class Frame extends Node2D {

    public Frame() {
        init(new Transform2D());
    }

    public Frame(Transform2D transform) {
        init(transform);
    }

    public Frame(Vector2f position, Vector2f size) {
        init(new Transform2D(position, size));
    }

    public void init(Transform2D transform) {
        setTransform(transform);
        VisualServer.draw(this);
    }
}
