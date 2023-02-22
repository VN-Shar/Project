package engine.node._2D;

import java.util.LinkedList;
import java.util.function.Consumer;

import org.joml.Vector2f;

public class Transform2D {

    private Vector2f position = new Vector2f();
    private Vector2f size = new Vector2f();
    private Vector2f scale = new Vector2f();
    private float rotation = 0.0f;

    public LinkedList<Consumer<Transform2D>> callbacks = new LinkedList<>();

    public Transform2D() {
        init(new Vector2f(0, 0), new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position) {
        init(position, new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position, Vector2f size) {
        init(position, size, new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position, Vector2f size, Vector2f scale) {
        init(position, size, scale, 0);
    }

    public Transform2D(Vector2f position, Vector2f size, Vector2f scale, float rotation) {
        init(position, size, scale, rotation);
    }

    public Transform2D(Transform2D transform) {
        init(transform.position, transform.size, transform.scale, transform.rotation);
    }

    public void init(Vector2f position, Vector2f size, Vector2f scale, float rotation) {
        this.position.set(position);
        this.scale.set(scale);
        this.size.set(size);
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Transform2D setPosition(Vector2f position) {
        this.position = position;
        invoke();
        return this;
    }

    public Transform2D move(Vector2f position) {
        this.position.add(position);
        invoke();
        return this;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Transform2D setSize(Vector2f size) {
        this.size = size;
        invoke();
        return this;
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public Transform2D setScale(Vector2f scale) {
        this.scale = scale;
        invoke();
        return this;
    }

    public Transform2D addScale(Vector2f scale) {
        this.scale.mul(scale);
        invoke();
        return this;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Transform2D setRotation(float rotation) {
        this.rotation = rotation;
        invoke();
        return this;
    }

    public Transform2D addRotation(float rotation) {
        this.rotation += rotation;
        invoke();
        return this;
    }

    public Transform2D translate(Transform2D transform) {
        return this.move(transform.position)//
                .addScale(transform.scale)//
                .addRotation(transform.rotation);
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

    private Vector2f rotate(Vector2f target) {
        return rotate(position, target, rotation);
    }

    public Vector2f rotate(Vector2f center, Vector2f target, float rotation) {

        double rad = Math.toRadians(rotation);
        float s = (float) Math.sin(rad);
        float c = (float) Math.cos(rad);

        float x = (target.x - center.x) * c - (target.y - center.y) * s;
        float y = (target.x - center.x) * s + (target.y - center.y) * c;
        return target.set(x + center.x, y + center.y);
    }

    public Transform2D copy() {
        return new Transform2D(new Vector2f(this.position), new Vector2f(this.size), new Vector2f(this.scale), this.rotation);
    }

    public void copy(Transform2D target) {
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

        if (!(o instanceof Transform2D))
            return false;

        Transform2D t = (Transform2D) o;

        return t.position.equals(this.position) && t.scale.equals(this.scale) && t.rotation == this.rotation;
    }

    public void invoke() {
        for (Consumer<Transform2D> callback : callbacks) {
            callback.accept(copy());
        }
    }

    public void onTransformChanged(Consumer<Transform2D> callback) {
        if (callbacks.contains(callback))
            return;
        callbacks.add(callback);

    }
}
