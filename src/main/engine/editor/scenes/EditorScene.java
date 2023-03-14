package engine.editor.scenes;

import org.joml.Vector2f;

import engine.Scene;
import engine.Window;
import engine.input.InputListener;
import engine.input.KeyBinding;
import engine.input.KeyBinding.Key;
import engine.node.FlagType.PositionType;
import engine.node.FlagType.SizeFlag;
import engine.node.UI.CanvasLayer;
import engine.node.UI.Color;
import engine.node._2D.Label;
import engine.node._2D.Sprite;

import static org.lwjgl.glfw.GLFW.*;

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

        label.getTransform().setSize(new Vector2f(500, 70));
        label.setBackgroundColor(new Color(0f, 0f, 0f, 0f));
        label.setTextColor(new Color(1, 0, 1, 1));
        label.setSizeFlag(SizeFlag.EXPAND);
        label.setPositionType(PositionType.CENTER);

        addChild(label);

        StringBuffer sb = new StringBuffer();

        InputListener.onKeyPressed.connect((key) -> {

            if (key == GLFW_KEY_DELETE || key == GLFW_KEY_BACKSPACE) {
                if (!sb.isEmpty())
                    sb.deleteCharAt(sb.length() - 1);
            } else {
                sb.append(String.valueOf((char) key.intValue()));
            }
            label.setText(sb.toString());
        });
    }

    @Override
    public void process(float deltaTime) {

        if (KeyBinding.isKeyJustPressed(Key.DOWN))
            System.out.println(label.getTransform());

        
    }
}
