package engine.node.UI.container;

import engine.node.Node;
import engine.node.Node2D;

public abstract class Container extends Node2D {

    public void addChild(Node child) {
        addChild(child);
        resize();
    }

    public abstract void resize();

}
