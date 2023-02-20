package engine.util;

import org.joml.Vector2f;

public class Transform {

    private Vector2f position;
    private Vector2f size;
    private Vector2f scale;
    private float rotation = 0.0f;

    public Transform() {
        init(new Vector2f(0, 0), new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform(Vector2f position) {
        init(position, new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform(Vector2f position, Vector2f size) {
        init(position, size, new Vector2f(1, 1), 0);
    }

    public Transform(Vector2f position, Vector2f size, Vector2f scale) {
        init(position, size, scale, 0);
    }

    public Transform(Vector2f position, Vector2f size, Vector2f scale, float rotation) {
        init(position, size, scale, rotation);
    }

    public void init(Vector2f position, Vector2f size, Vector2f scale, float rotation) {
        this.position = position;
        this.size = size;
        this.scale = scale;
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Transform setPosition(Vector2f position) {
        this.position = position;
        return this;
    }

    public Transform move(Vector2f position) {
        this.position.add(position);
        return this;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Transform setSize(Vector2f size) {
        this.size = size;
        return this;
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public Transform setScale(Vector2f scale) {
        this.scale = scale;
        return this;
    }

    public Transform addScale(Vector2f scale) {
        this.scale.add(scale);
        return this;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Transform setRotation(float rotation) {
        this.rotation = rotation;
        return this;
    }

    public Transform addRotation(float rotation) {
        this.rotation += rotation;
        return this;
    }

    public Vector2f getTopLeft() {
        return rotate(new Vector2f(position).sub(new Vector2f(size).div(2).mul(scale)));
    }

    public Vector2f getBottomRight() {
        return rotate(new Vector2f(position).add(new Vector2f(size).div(2).mul(scale)));
    }

    public Vector2f getTopRight() {
        return rotate(new Vector2f(position.x + size.x * scale.x / 2, position.y - size.y * scale.y / 2));
    }

    public Vector2f getBottomLeft() {
        return rotate(new Vector2f(position.x - size.x * scale.x / 2, position.y + size.y * scale.y / 2));
    }

    public Vector2f rotate(Vector2f target) {
        double rad = Math.toRadians(rotation);
        float s = (float) Math.sin(rad);
        float c = (float) Math.cos(rad);

        float x = (target.x - position.x) * c - (target.y - position.y) * s;
        float y = (target.x - position.x) * s + (target.y - position.y) * c;
        return target.set(x + position.x, y + position.y);
    }

    public Transform copy() {
        return new Transform(new Vector2f(this.position), new Vector2f(this.size), new Vector2f(this.scale), this.rotation);
    }

    public void copy(Transform target) {
        target.position.set(this.position);
        target.size.set(this.size);
        target.scale.set(this.scale);
        target.rotation = this.rotation;
    }

    @Override
    public String toString() {
        return "position:" + position.toString() + " size: " + size.toString() + " scale:" + scale.toString() + " rotation:"
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
