package engine.node.UI;

import engine.event.EventHandler;
import engine.event.EventType.CameraPositionChanged;
import engine.event.EventType.CameraSizeChanged;
import engine.node.Node2D;

public class CanvasLayer extends Node2D {

    public CanvasLayer() {

        // Layer follow camera
        EventHandler.connect(CameraPositionChanged.class, (event) -> {
            getTransform().setPosition((((CameraPositionChanged) event)).position);
        });

        // Resize as camera
        EventHandler.connect(CameraSizeChanged.class, (event) -> {
            getTransform().setSize((((CameraSizeChanged) event)).size);
        });

    }
}
