package engine.util;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f size;
    public Vector2f scale;
    public float rotation = 0.0f;

    public Transform() {
        init(new Vector2f(), new Vector2f(1, 1), new Vector2f(1, 1));
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f(1, 1), new Vector2f(1, 1));
    }

    public Transform(Vector2f position, Vector2f size) {
        init(position, size, new Vector2f(1, 1));
    }

    public Transform(Vector2f position, Vector2f size, Vector2f scale) {
        init(position, size, scale);
    }

    public void init(Vector2f position, Vector2f size, Vector2f scale) {
        this.position = position;
        this.size = size;
        this.scale = scale;
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.scale));
    }

    public void copy(Transform target) {
        target.position.set(this.position);
        target.scale.set(this.scale);
        target.rotation = this.rotation;
    }

    @Override
    public String toString() {
        return "position:" + position.toString() + " scale:" + scale.toString() + " rotation:"
                + String.valueOf(rotation);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!(o instanceof Transform))
            return false;

        Transform t = (Transform) o;

        return t.position.equals(this.position) && t.scale.equals(this.scale) && t.rotation == this.rotation;
    }
}
