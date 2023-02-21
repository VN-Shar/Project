package engine.editor.scenes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Scene;
import engine.Window;
import engine.component._2D.Label;
import engine.component._2D.Sprite;
import engine.event.EventHandler;
import engine.event.EventType.WindowResize;
import engine.input.InputListener;

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

        Vector2f size = new Vector2f(getCamera().getSize());

        label.getTransform().setPosition(size.div(-2).add(label.getTransform().copy().getSize().div(2)));
        label.setFontSize(30);

        queueAddObject(label);

        EventHandler.connect(WindowResize.class, (event) -> {

            Vector2f newWindowSize = ((WindowResize) event).size;
            label.getTransform().setPosition(newWindowSize.div(-1).add(label.getTransform().copy().getSize().div(2)));
            label.setFontSize(30);
        });
    }

    @Override
    public void process(float deltaTime) {
        label.setText("FPS: " + String.valueOf(Window.get().getFps() + "  MOUSE POSITION: (" + InputListener.getScreenMousePosition().x + ", " + InputListener
                .getScreenMousePosition().y) + ")");
        label.setBackgroundColor(new Vector4f(0, 0, 0, 0.8f));
        label.setTextColor(new Vector4f(0, 1, 0, 1));
    }

}
