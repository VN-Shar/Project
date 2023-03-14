package engine;

import static org.lwjgl.glfw.GLFW.*;

import engine.input.KeyBinding;
import engine.util.Log;

public class Engine {

    public Engine() {
        try {
            Window window = Window.get();

            KeyBinding.addKeyBind(GLFW_KEY_W, "UP");
            KeyBinding.addKeyBind(GLFW_KEY_S, "DOWN");
            KeyBinding.addKeyBind(GLFW_KEY_A, "LEFT");
            KeyBinding.addKeyBind(GLFW_KEY_D, "RIGHT");

            window.run();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void printStackTrace() {
        StringBuffer buffer = new StringBuffer();
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            buffer.append(ste.toString() + "\n");
        }
        Log.error(buffer.toString());
    }
}
