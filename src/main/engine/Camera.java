package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    public Vector2f position;
    private Matrix4f projectionMatrix, viewMatrix, inverseProjection, inverseView;

    private Vector2f size = new Vector2f();
    private float aspectRatio;

    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    private Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    private float zoom = 1f;

    public Camera(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        adjustProjection();
    }

    public void setCameraSize(Vector2f size) {
        this.size = size;
        adjustProjection();
    }

    public void adjustProjection() {
        this.aspectRatio = size.y / size.x;
        projectionMatrix.identity();
        projectionMatrix.ortho(
                -1 * zoom, 1 * zoom,
                aspectRatio * zoom, -aspectRatio * zoom,
                0.0f, 100.0f);
        inverseProjection = new Matrix4f(projectionMatrix).invert();
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 0.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);
        inverseView = new Matrix4f(this.viewMatrix).invert();

        return this.viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4f getInverseProjection() {
        return this.inverseProjection;
    }

    public Matrix4f getInverseView() {
        return this.inverseView;
    }

    public float getZoom() {
        return zoom;
    }

    public void setZoom(float zoom) {
        adjustProjection();
        this.zoom = zoom;
    }

    public void addZoom(float value) {
        adjustProjection();
        this.zoom += value;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}
