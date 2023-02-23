package engine.node.UI.container;

import engine.node.Node;
import engine.node.Node2D;

public class MarginContainer extends Container {

    private float marginLeft = 0;
    private float marginRight = 0;
    private float marginTop = 0;
    private float marginBottom = 0;

    public float getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public float getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public float getMarginTop() {
        return this.marginTop;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public float getMarginBottom() {
        return this.marginBottom;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginAll(float margin) {
        setMarginLeft(margin);
        setMarginRight(margin);
        setMarginTop(margin);
        setMarginBottom(margin);
    }

    public void resize() {
        for (Node c : getChildren()) {
            if (c instanceof Node2D)
                continue;

            // Node2D child = (Node2D) c;
            // TODO: Make a TOP-LEFT/CENTER coordinate option
        }
    }

}
