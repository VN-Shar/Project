package engine.node._2D;

import org.joml.Vector2f;

import engine.node.Node2D;
import engine.node.Transform2D;
import engine.renderer.VisualServer;

public class Frame extends Node2D {

    public Frame() {
        this(new Transform2D());
    }

    public Frame(Vector2f position, Vector2f size) {
        this(new Transform2D(position, size));
    }

    public Frame(Transform2D transform) {
        getTransform().copyFrom(transform);
        VisualServer.draw(this);
    }
}
