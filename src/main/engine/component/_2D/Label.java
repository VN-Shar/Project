package engine.component._2D;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.KeyListener;
import engine.component.GraphicComponent;
import engine.util.Font;
import engine.util.Alignment.HorizontalAlignment;
import engine.util.Alignment.VerticalAlignment;

import static org.lwjgl.glfw.GLFW.*;

public class Label extends GraphicComponent {

    private Text text;
    private Frame frame;

    public VerticalAlignment verticalAlignment = VerticalAlignment.BEGIN;
    public HorizontalAlignment horizontalAlignment = HorizontalAlignment.BEGIN;

    public Label() {
        frame = new Frame(getTransform());
        text = new Text();

        text.horizontalAlignment = horizontalAlignment;
        text.verticalAlignment = verticalAlignment;
    }

    private void init() {

        frame.setTransform(getTransform().copy());
        text.setTransform(getTransform().copy().setPosition(frame.getTransform().copy().getTopLeft()));
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getText() {
        return this.text.getText();
    }

    public void setTextColor(Vector4f color) {
        this.text.setColor(color);
    }

    public Vector4f getTextColor() {
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

    public void setBackgroundColor(Vector4f color) {
        this.frame.setColor(color);
    }

    public Vector4f getBackgroundColor() {
        return this.frame.getColor();
    }

    @Override
    public void process(float deltaTime) {
        if (KeyListener.isKeyPressed(GLFW_KEY_D))
            getTransform().addRotation(55 * deltaTime);
        else if (KeyListener.isKeyPressed(GLFW_KEY_A))
            getTransform().addRotation(-55 * deltaTime);

        Vector2f dir = new Vector2f((float) Math.cos(Math.toRadians(getTransform().getRotation() + 90)),
                (float) Math.sin(Math.toRadians(getTransform().getRotation() + 90))).mul(10 * deltaTime * 144);

        if (KeyListener.isKeyPressed(GLFW_KEY_W))
            getTransform().move(dir.mul(-1));
        else if (KeyListener.isKeyPressed(GLFW_KEY_S))
            getTransform().move(dir);

        init();
    }
}
