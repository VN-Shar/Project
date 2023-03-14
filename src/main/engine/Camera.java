package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.event.Signal;
import engine.node.Transform2D;

public class Camera {

    public enum WindowMode {
        REMAIN, STRETCH, SCALE;
    }

    private static final float MIN_ZOOM = 0.001f;
    private static final float MAX_ZOOM = 30f;
    private static final Vector2f DEFAULT_SIZE = new Vector2f(1920, 1080);

    private WindowMode windowMode = WindowMode.SCALE;

    private Transform2D transform = new Transform2D();

    private Matrix4f projectionMatrix, viewMatrix;
    private float aspectRatio;

    private Vector3f cameraOrigin = new Vector3f();
    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    private Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    private Vector2f windowScale = new Vector2f(1, 1);

    public Signal<Transform2D> onCameraTransformChanged = new Signal<Transform2D>();

    public Camera(Vector2f position, Vector2f size) {
        this.transform.setPosition(position);
        this.transform.setSize(size);

        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();

        transform.onTransformChanged.connect((trans) -> onCameraTransformChanged.emit(trans));
        calculateProjectionMatrix();
    }

    public void setSize(Vector2f size) {
        windowScale.set(1, 1);
        switch (windowMode) {
            case REMAIN:
                transform.setSize(size);
                break;

            case STRETCH:
                transform.setSize(DEFAULT_SIZE);
                break;

            case SCALE:
                float x = DEFAULT_SIZE.x / size.x;
                float y = DEFAULT_SIZE.y / size.y;

                transform.setSize(size);
                windowScale.set(Math.max(x, y));
                break;
        }
        this.aspectRatio = size.y / size.x;
        calculateProjectionMatrix();
        onCameraTransformChanged.emit(transform);
    }

    public Vector2f getSize() {
        return new Vector2f(transform.getSize());
    }

    public Vector2f getPosition() {
        return new Vector2f(transform.getPosition());
    }

    public Transform2D getTransform() {
        return transform;
    }

    public void setPosition(Vector2f position) {
        if (!transform.getPosition().equals(position)) {
            transform.setPosition(position);
        }
    }

    public void calculateProjectionMatrix() {
        projectionMatrix.identity();
        projectionMatrix.ortho(//
                transform.getScale().x * windowScale.x * transform.getSize().x / -2, //
                transform.getScale().x * windowScale.x * transform.getSize().x / 2, //
                transform.getScale().y * windowScale.y * aspectRatio * transform.getSize().y / 2, //
                transform.getScale().y * windowScale.y * aspectRatio * transform.getSize().y / -2, //
                0, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();

        viewMatrix.lookAt(//
                cameraOrigin.set(transform.getPosition(), 0f), //
                cameraFront.set(transform.getPosition(), -100), //
                cameraUp);

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Vector2f getZoom() {
        return new Vector2f(transform.getScale());
    }

    public void setZoom(float zoom) {
        // Zoom can not less than 0 and more than MAX
        if (zoom >= MIN_ZOOM && zoom <= MAX_ZOOM) {
            transform.setScale(zoom);
            calculateProjectionMatrix();
        }
    }

    public void addZoom(float value) {
        if (transform.getScale().x + value >= MIN_ZOOM //
                && transform.getScale().x + value <= MAX_ZOOM//
                && transform.getScale().y + value >= MIN_ZOOM//
                && transform.getScale().y + value <= MAX_ZOOM) {
            this.transform.addScale(value);
            calculateProjectionMatrix();
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}
