package engine.node._2D;

import engine.node.Node2D;
import engine.node.UI.Color;
import engine.node._2D.Alignment.HorizontalAlignment;
import engine.node._2D.Alignment.VerticalAlignment;
import engine.renderer.Font;

public class Label extends Node2D {

    private Text text;
    private Frame frame;

    public VerticalAlignment verticalAlignment = VerticalAlignment.BEGIN;
    public HorizontalAlignment horizontalAlignment = HorizontalAlignment.BEGIN;

    public Label() {
        init(new Transform2D());
    }

    public Label(Transform2D transform) {
        init(transform);
    }

    private void init(Transform2D transform) {

        frame = new Frame();
        text = new Text();

        addChild(frame);
        addChild(text);

        text.horizontalAlignment = horizontalAlignment;
        text.verticalAlignment = verticalAlignment;

        setTransform(transform);
    }

    @Override
    public void setTransform(Transform2D transform) {
        transform.onTransformChanged((trans) -> {
            frame.getTransform().setSize(trans.getSize());
            text.getTransform().setSize(trans.getSize());
        });
        super.setTransform(transform);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return this.text.getText();
    }

    public void setTextColor(Color color) {
        this.text.setColor(color);
    }

    public Color getTextColor() {
        return this.text.getColor();
    }

    public void setFontSize(int size) {
        this.text.getFont().setFontHeight(size);
    }

    public int getFontSize() {
        return this.text.getFont().getFontHeight();
    }

    public void setFont(Font font) {
        this.text.setFont(font);
    }

    public Font getFont() {
        return this.text.getFont();
    }

    public void setBackgroundColor(Color color) {
        this.frame.setColor(color);
    }

    public Color getBackgroundColor() {
        return this.frame.getColor();
    }
}
