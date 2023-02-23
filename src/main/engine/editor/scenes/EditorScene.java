package engine.editor.scenes;

import org.joml.Vector2f;

import engine.Camera;
import engine.Scene;
import engine.Window;
import engine.input.InputListener;
import engine.input.KeyBinding;
import engine.node.UI.CanvasLayer;
import engine.node.UI.Color;
import engine.node._2D.Label;
import engine.node._2D.Sprite;
import engine.node._2D.FlagType.PositionType;

import static engine.input.KeyBinding.Key;

public class EditorScene extends Scene {

    Label label;
    Sprite background;
    CanvasLayer canvas;

    public EditorScene(Window window) {
        super(window);
    }

    @Override
    public void ready() {

        background = new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg");
        label = new Label();
        canvas = new CanvasLayer();

        canvas.addChild(background);

        addChild(label);
        addChild(canvas);

        label.getTransform().setSize(new Vector2f(500, 70));
        label.setBackgroundColor(new Color(0.1f, 0.1f, 0.1f, 0.8f));
        label.setTextColor(new Color(1, 1, 1, 1));

        label.setPositionType(PositionType.CENTER);
    }

    @Override
    public void process(float deltaTime) {
        label.setText("FPS: " + String.valueOf(Window.get().getFps() + "  MOUSE POSITION: (" + InputListener.getScreenMousePosition().x + ", "
                + InputListener.getScreenMousePosition().y) + ")");

        Vector2f dir = new Vector2f();

        if (KeyBinding.isKeyPressed(Key.RIGHT))
            dir.x = 1;

        else if (KeyBinding.isKeyPressed(Key.LEFT))
            dir.x = -1;

        if (KeyBinding.isKeyPressed(Key.UP))
            dir.y = -1;

        else if (KeyBinding.isKeyPressed(Key.DOWN))
            dir.y = 1;

        if (KeyBinding.isKeyJustPressed(Key.UP))
            dir.y = -30;

        label.getTransform().move(dir.mul(deltaTime * 300));

        Camera camera = getCamera();
        float zoom = -InputListener.getMouseScroll().y / 10;

        label.getTransform()
                .setRotation(180 - angle(new Vector2f(InputListener.getGlobalMousePosition().sub(label.getGlobalTransform().getPosition()))));

        camera.setPosition(camera.getPosition().sub(InputListener.getMouseRelative().mul(getCamera().getZoom())));

        // System.out.println(label.getGlobalTransform().getRotation());

        if (zoom != 0)
            camera.addZoom(zoom);
    }

    public float angle(Vector2f v) {
        return (float) Math.toDegrees(Math.atan2(v.x, v.y));
    }
}
