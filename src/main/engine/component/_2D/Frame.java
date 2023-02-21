package engine.component._2D;

import org.joml.Vector2f;

import engine.component.GraphicComponent;
import engine.renderer.VisualServer;
import engine.util.math.Transform;

public class Frame extends GraphicComponent {

    public Frame() {
        init(new Transform());
    }

    public Frame(Transform transform) {
        init(transform);
    }

    public Frame(Vector2f position, Vector2f size) {
        init(new Transform(position, size));
    }

    public void init(Transform transform) {
        setTransform(transform);
        VisualServer.draw(this);
    }
}
