package engine.node;

import org.joml.Vector2f;

import engine.event.Signal;

public class Transform2D {

    private Vector2f position = new Vector2f();
    private Vector2f size = new Vector2f();
    private Vector2f scale = new Vector2f();
    private float rotation = 0.0f;

    public Signal<Transform2D> onTransformChanged = new Signal<Transform2D>();

    public Transform2D() {
        this(new Vector2f(0, 0), new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position) {
        this(position, new Vector2f(1, 1), new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position, Vector2f size) {
        this(position, size, new Vector2f(1, 1), 0);
    }

    public Transform2D(Vector2f position, Vector2f size, Vector2f scale) {
        this(position, size, scale, 0);
    }

    public Transform2D(Transform2D transform) {
        this(transform.position, transform.size, transform.scale, transform.rotation);
    }

    public Transform2D(Vector2f position, Vector2f size, Vector2f scale, float rotation) {
        this.position.set(position);
        this.scale.set(scale);
        this.size.set(size);
        this.rotation = rotation;
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector2f getPositionCopy() {
        return new Vector2f(this.position);
    }

    public Transform2D setPosition(Vector2f position) {
        this.position = position;
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D move(Vector2f position) {
        if (position.x == 0 && position.y == 0)
            return this;

        this.position.add(position);
        onTransformChanged.emit(this);
        return this;
    }

    public Vector2f getSize() {
        return this.size;
    }

    public Vector2f getSizeCopy() {
        return new Vector2f(this.size);
    }

    public Transform2D setSize(Vector2f size) {
        this.size = size;
        onTransformChanged.emit(this);
        return this;
    }

    public Vector2f getScale() {
        return this.scale;
    }

    public Vector2f getScaleCopy() {
        return new Vector2f(this.scale);
    }

    public Transform2D setScale(Vector2f scale) {
        this.scale = scale;
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D setScale(float scale) {
        this.scale = new Vector2f(scale, scale);
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D addScale(Vector2f scale) {
        this.scale.add(scale);
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D addScale(float scale) {
        this.scale.add(scale, scale);
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D mulScale(Vector2f scale) {
        this.scale.mul(scale);
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D mulScale(float scale) {
        this.scale.mul(scale);
        onTransformChanged.emit(this);
        return this;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Transform2D setRotation(float rotation) {
        this.rotation = rotation;
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D addRotation(float rotation) {
        this.rotation += rotation;
        onTransformChanged.emit(this);
        return this;
    }

    public Transform2D translate(Transform2D transform) {
        return this.move(transform.position)//
                .mulScale(transform.scale)//
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

    public Vector2f rotate(Vector2f target) {
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
        return new Transform2D(new Vector2f(this.position), new Vector2f(this.size), new Vector2f(this.scale),
                this.rotation);
    }

    public void copyFrom(Transform2D target) {
        this.position.set(target.position);
        this.size.set(target.size);
        this.scale.set(target.scale);
        this.rotation = target.rotation;

        onTransformChanged.emit(this);
    }

    @Override

    public String toString() {
        return "position: (" + position.x + ", " + position.y + ") size: (" + size.x + ", " + size.y + ") scale: ("
                + scale.x + ", " + scale.y
                + ") rotation:" + String.valueOf(rotation);
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (!(o instanceof Transform2D))
            return false;

        Transform2D t = (Transform2D) o;

        return t.position.equals(this.position) && t.scale.equals(this.scale) && t.rotation == this.rotation;
    }
}
