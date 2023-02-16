package engine.editor.scenes;

import org.joml.Vector2f;

import engine.Scene;
import engine.component._2D.Frame;
import engine.component._2D.Sprite;

public class EditorScene extends Scene {

    public EditorScene() {
        queueAddObject(new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg"));
        queueAddObject(new Frame(new Vector2f(0, 0), new Vector2f(100, 100)));
    }

}
