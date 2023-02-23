package engine.node.UI;

import engine.Scene;
import engine.Camera.CameraPositionChanged;
import engine.Camera.CameraSizeChanged;
import engine.Camera.CameraZoomChanged;
import engine.event.EventHandler;
import engine.node.Node2D;

public class CanvasLayer extends Node2D {

    private static final int DEFAULT_Z_INDEX = 100;

    public CanvasLayer() {

        try {
            // Layer follow camera
            EventHandler.connect(CameraPositionChanged.class, (event) -> getTransform().setPosition((((CameraPositionChanged) event)).position));
            // Resize as camera
            EventHandler.connect(CameraSizeChanged.class, (event) -> getTransform().setSize((((CameraSizeChanged) event)).size));
            // Scale as zoom
            EventHandler.connect(CameraZoomChanged.class, (event) -> getTransform().setScale(((CameraZoomChanged) event).zoom));

            // Make canvas on top others
            setZIndex(DEFAULT_Z_INDEX);

        } catch (Exception exception) {
            throw exception;
        }
    }

    public void setTree(Scene tree) {
        super.setTree(tree);
        
        getTransform().setScale(tree.getCamera().getZoom());
    }
}
