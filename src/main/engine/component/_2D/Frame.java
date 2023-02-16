package engine.component._2D;

import org.joml.Vector2f;

import engine.component.GraphicComponent;
import engine.util.Transform;

public class Frame extends GraphicComponent {

    public Frame(Transform transform) {
        this.transform = transform;
    }

    public Frame(Vector2f position, Vector2f size) {
        this.transform = new Transform(position, size);
    }
}
