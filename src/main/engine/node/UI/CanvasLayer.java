package engine.node.UI;

import engine.Scene;
import engine.node.Node2D;

public class CanvasLayer extends Node2D {

    private static final int DEFAULT_Z_INDEX = 100;

    public CanvasLayer() {

        // Layer follow camera
        onTreeEntered
                .connect((tree) -> tree.getCamera().onCameraTransformChanged.connect((trans) -> setTransform(trans)));

        // Make canvas on top others
        setZIndex(DEFAULT_Z_INDEX);
    }

    public void setTree(Scene tree) {
        super.setTree(tree);
        getTransform().setScale(tree.getCamera().getZoom());
    }
}
