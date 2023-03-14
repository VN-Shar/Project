package engine.node.UI.container;

import engine.node.Node;
import engine.node.Node2D;
import engine.node.FlagType.PositionType;

public abstract class Container extends Node2D {

    public Container() {
    }

    @Override
    public void addChild(Node child) {
        super.addChild(child);
        if (child instanceof Node2D) {
            Node2D c = (Node2D) child;
            c.setPositionType(PositionType.TOP_LEFT);
            resize();
        }
    }

    @Override
    public void setParent(Node parent) {
        super.setParent(parent);
        parent.getTransform().onTransformChanged.connect((trans) -> resize());
    }

    public abstract void resize();

}
