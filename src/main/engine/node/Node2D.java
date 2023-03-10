package engine.node;

import org.joml.Vector2f;

import engine.node.FlagType.PositionFlag;
import engine.node.FlagType.PositionType;
import engine.node.FlagType.SizeFlag;
import engine.node.UI.Color;
import engine.node.UI.container.Container;

public class Node2D extends Node {

    private boolean isDirty = true;
    private boolean isShow = true;
    private Color color = new Color(1, 1, 1, 1);

    private PositionFlag positionFlag = PositionFlag.CONTAINER;
    private SizeFlag sizeFlag = SizeFlag.EXPAND;
    private PositionType positionType = PositionType.TOP_LEFT;

    public static Vector2f[] texCoords = { new Vector2f(1, 1), new Vector2f(1, 0), new Vector2f(0, 0),
            new Vector2f(0, 1) };

    public Node2D() {
        getTransform().onTransformChanged.connect((trans) -> dirty());
    }

    public void clean() {
        this.isDirty = false;
    }

    public void dirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty && isShowing();
    }

    public void show() {
        this.isShow = true;
    }

    public void hide() {
        this.isShow = false;
    }

    public boolean isShowing() {
        return this.isShow;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void setPositionFlag(PositionFlag flag) {
        this.positionFlag = flag;
        flagUpdated();
    }

    public PositionFlag getPositionFlag() {
        return this.positionFlag;
    }

    public void setSizeFlag(SizeFlag flag) {
        this.sizeFlag = flag;
        flagUpdated();
    }

    public SizeFlag getSizeFlag() {
        return this.sizeFlag;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    private void flagUpdated() {
        Node parent = getParent();
        if (parent == null)
            return;

        if (parent instanceof Container) {
            ((Container) parent).resize();
        }
    }
}
