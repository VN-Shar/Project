package engine.editor.scenes;

import engine.Scene;
import engine.component._2D.Sprite;

public class EditorScene extends Scene {

    public EditorScene() {
        queueAddObject(new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg"));
    }

    public void update(float deltaTime) {

    }
}
