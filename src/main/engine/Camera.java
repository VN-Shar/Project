package engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import engine.event.Event;
import engine.event.EventHandler;

public class Camera {

    public enum WindowMode {
        REMAIN, STRETCH, SCALE;
    }

    private static final float MIN_ZOOM = 0.01f;
    private static final float MAX_ZOOM = 30f;

    private WindowMode windowMode = WindowMode.REMAIN;
    private final Vector2f DEFAULT_SIZE = new Vector2f(1920, 1080);

    private Vector2f position;
    private Vector2f size = new Vector2f();

    private Matrix4f projectionMatrix, viewMatrix, inverseProjection, inverseView;
    private float aspectRatio;

    private Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
    private Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);

    private float zoom = 2f;
    private float windowZoom = 1f;

    public static class CameraSizeChanged implements Event {

        public final Vector2f size;

        public CameraSizeChanged(Vector2f size) {
            this.size = size;
        }
    }

    public static class CameraPositionChanged implements Event {

        public final Vector2f position;

        public CameraPositionChanged(Vector2f position) {
            this.position = position;
        }
    }

    public static class CameraZoomChanged implements Event {

        public final float zoom;

        public CameraZoomChanged(Float zoom) {
            this.zoom = zoom;
        }
    }

    public Camera(Vector2f position, Vector2f size) {
        this.position = position;
        this.size = size;
        this.projectionMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
        this.inverseProjection = new Matrix4f();
        this.inverseView = new Matrix4f();
        getProjection();
    }

    public void setSize(Vector2f size) {
        this.windowZoom = 1f;
        switch (windowMode) {
        case REMAIN:
            this.size = size;
            break;

        case STRETCH:
            this.size = DEFAULT_SIZE;
            break;

        case SCALE:
            float x = DEFAULT_SIZE.x / size.x;
            float y = DEFAULT_SIZE.y / size.y;
            this.size = size;
            this.windowZoom = x > y ? x : y;
            break;
        }
        this.aspectRatio = size.y / size.x;
        getProjection();

        EventHandler.invoke(new CameraSizeChanged(size));
    }

    public Vector2f getSize() {
        return new Vector2f(size);
    }

    public Vector2f getPosition() {
        return new Vector2f(position);
    }

    public void setPosition(Vector2f position) {
        this.position = position;
        EventHandler.invoke(new CameraPositionChanged(position));
    }

    public void getProjection() {
        projectionMatrix.identity();
        projectionMatrix.ortho(//
                -zoom * windowZoom / 2, //
                zoom * windowZoom / 2, //
                zoom * windowZoom * aspectRatio / 2, //
                -zoom * windowZoom * aspectRatio / 2, //
                0.0f, 100.0f);
        inverseProjection = new Matrix4f(projectionMatrix).invert();
    }

    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        viewMatrix.lookAt(new Vector3f(0, 0, 0.0f), cameraFront.add(0, 0, 0.0f), cameraUp);
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
        // Zoom can not be <= 0
        if (zoom >= MIN_ZOOM && zoom <= MAX_ZOOM) {
            this.zoom = zoom;
            getProjection();
            EventHandler.invoke(new CameraZoomChanged(zoom));
        }
    }

    public void addZoom(float value) {
        if (zoom + value >= MIN_ZOOM && zoom + value <= MAX_ZOOM) {
            this.zoom += value;
            getProjection();
            EventHandler.invoke(new CameraZoomChanged(zoom));
        }
    }

    public float getAspectRatio() {
        return aspectRatio;
    }
}
