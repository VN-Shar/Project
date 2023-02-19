package engine.editor.scenes;

import engine.Scene;
import engine.component._2D.Sprite;
import engine.component._2D.Text;

public class EditorScene extends Scene {

    public EditorScene() {
        queueAddObject(new Sprite("D:/Java/Project/assets/images/vscodebackground.jpg"));
        queueAddObject(new Text("How to render a text"));
    }

}
