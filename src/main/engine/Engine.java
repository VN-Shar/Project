package engine;

import static org.lwjgl.glfw.GLFW.*;

import engine.input.KeyBinding;

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
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste);
        }
    }
}
