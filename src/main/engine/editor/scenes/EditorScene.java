package engine.editor.scenes;

import org.joml.Vector2f;

import engine.Camera;
import engine.Scene;
import engine.Window;
import engine.input.InputListener;
import engine.node.UI.Color;
import engine.node._2D.Label;
import engine.node._2D.Sprite;

import static org.lwjgl.glfw.GLFW.*;

public class EditorScene extends Scene {

    Label label;

    public EditorScene(Window window) {
        super(window);
    }

    @Override
    public void ready() {

        queueAddObject(new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg"));

        label = new Label();
        label.getTransform().setSize(new Vector2f(500, 70));
        label.setBackgroundColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
        label.setTextColor(new Color(1, 1, 1, 1));

        queueAddObject(label);
    }

    @Override
    public void process(float deltaTime) {
        label.setText("FPS: " + String.valueOf(Window.get().getFps() + "  MOUSE POSITION: (" + InputListener.getScreenMousePosition().x + ", "
                + InputListener.getScreenMousePosition().y) + ")");

        if (InputListener.isKeyPressed(GLFW_KEY_D))
            label.getTransform().addRotation(55 * deltaTime);
        else if (InputListener.isKeyPressed(GLFW_KEY_A))
            label.getTransform().addRotation(-55 * deltaTime);

        Vector2f dir = new Vector2f((float) Math.cos(Math.toRadians(label.getTransform().getRotation() + 90)),
                (float) Math.sin(Math.toRadians(label.getTransform().getRotation() + 90))).mul(10 * deltaTime * 144);

        if (InputListener.isKeyPressed(GLFW_KEY_W))
            label.getTransform().move(dir.mul(-1));
        else if (InputListener.isKeyPressed(GLFW_KEY_S))
            label.getTransform().move(dir);

        Camera camera = Window.getScene().getCamera();

        float zoom = -InputListener.getMouseScroll().y / 10;

        camera.setPosition(camera.getPosition().sub(InputListener.getMouseRelative().mul(camera.getZoom())));

        if (zoom != 0)
            camera.addZoom(zoom);
    }
}
