package engine.editor.scenes;

import org.joml.Vector2f;
import org.joml.Vector4f;

import engine.Scene;
import engine.Window;
import engine.component._2D.Label;
import engine.component._2D.Sprite;

public class EditorScene extends Scene {

    Label label;

    @Override
    public void ready() {
        queueAddObject(new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg"));

        label = new Label();
        label.getTransform().setSize(new Vector2f(100, 60));
        Vector2f size = new Vector2f(getCamera().getSize());
        label.getTransform().setPosition(size.div(-1.5f));
        
        queueAddObject(label);
        
    }
    
    @Override
    public void process(float deltaTime) {
        label.setText("FPS: " + String.valueOf(Window.get().getFps()));
        label.setBackgroundColor(new Vector4f(0, 0, 0, 0.8f));
        label.setTextColor(new Vector4f(0, 1, 0, 1));
    }

}
