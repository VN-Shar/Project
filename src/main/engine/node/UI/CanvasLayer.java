package engine.node.UI;

import engine.Camera.CameraPositionChanged;
import engine.Camera.CameraSizeChanged;
import engine.Camera.CameraZoomChanged;
import engine.event.EventHandler;
import engine.node.Node2D;

public class CanvasLayer extends Node2D {

    private static final int DEFAULT_Z_INDEX = 100;

    public CanvasLayer() {

        // Layer follow camera
        EventHandler.connect(CameraPositionChanged.class, (event) -> getTransform().setPosition((((CameraPositionChanged) event)).position));
        // Resize as camera
        EventHandler.connect(CameraSizeChanged.class, (event) -> getTransform().setSize((((CameraSizeChanged) event)).size));

        EventHandler.connect(CameraZoomChanged.class, (event) -> getTransform().setScale(((CameraZoomChanged) event).zoom));

        setZIndex(DEFAULT_Z_INDEX);
    }
}
