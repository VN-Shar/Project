package engine.component._2D;

import engine.component.GraphicComponent;
import engine.renderer.Font;
import engine.util.Color;
import engine.util.Alignment.HorizontalAlignment;
import engine.util.Alignment.VerticalAlignment;
import engine.util.math.Transform;

public class Label extends GraphicComponent {

    private Text text;
    private Frame frame;

    public VerticalAlignment verticalAlignment = VerticalAlignment.BEGIN;
    public HorizontalAlignment horizontalAlignment = HorizontalAlignment.BEGIN;

    public Label() {
        init(new Transform());
    }

    public Label(Transform transform) {
        init(transform);
    }

    private void init(Transform transform) {

        frame = new Frame();
        text = new Text();

        text.horizontalAlignment = horizontalAlignment;
        text.verticalAlignment = verticalAlignment;

        setTransform(transform);
        reposition();
    }

    public void reposition() {
        frame.setTransform(getTransform().copy());
        text.setTransform(getTransform().copy().setPosition(frame.getTransform().copy().getTopLeft()));
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
