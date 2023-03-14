package engine.node._2D;

import engine.node.Node2D;
import engine.node.Transform2D;
import engine.node.FlagType.Alignment;
import engine.node.FlagType.PositionType;
import engine.node.UI.Color;
import engine.renderer.Font;

public class Label extends Node2D {

    private Text text;
    private Frame frame;

    public Alignment verticalAlignment = Alignment.BEGIN;
    public Alignment horizontalAlignment = Alignment.BEGIN;

    public Label() {
        this(new Transform2D());
    }

    public Label(Transform2D transform) {

        frame = new Frame(transform);
        text = new Text(transform);

        addChild(frame);
        addChild(text);

        setPositionType(PositionType.CENTER);

        text.horizontalAlignment = horizontalAlignment;
        text.verticalAlignment = verticalAlignment;

        getTransform().onTransformChanged.connect(this::reposition);

        getTransform().copyFrom(transform);
    }

    public void reposition(Transform2D trans) {

    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return this.text.getText();
    }

    public void setTextColor(Color color) {
        this.text.setColor(color);
        dirty();
    }

    public Color getTextColor() {
        return this.text.getColor();
    }

    public void setFontSize(int size) {
        this.text.getFont().setFontHeight(size);
        dirty();
    }

    public int getFontSize() {
        return this.text.getFont().getFontHeight();
    }

    public void setFont(Font font) {
        this.text.setFont(font);
        dirty();
    }

    public Font getFont() {
        return this.text.getFont();
    }

    public void setBackgroundColor(Color color) {
        this.frame.setColor(color);
        dirty();
    }

    public Color getBackgroundColor() {
        return this.frame.getColor();
    }

    public void setPositionType(PositionType positionType) {
        super.setPositionType(positionType);
        frame.setPositionType(positionType);
        text.setPositionType(positionType);
        dirty();
    }
}
