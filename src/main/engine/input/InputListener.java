package engine.input;

import java.util.Arrays;

import org.joml.Vector2f;

import engine.Camera;
import engine.Window;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class InputListener {

    // Keyboard listener
    private static boolean keyPressed[] = new boolean[350];
    private static boolean keyJustPressed[] = new boolean[350];

    // Mouse listener
    private static Vector2f scroll = new Vector2f();
    private static Vector2f position = new Vector2f(), lastPosition = new Vector2f();
    private static boolean mouseButtonPressed[] = new boolean[9];
    private static boolean isMouseButtonPressed;

    public static void endFrame() {
        Arrays.fill(keyJustPressed, false);

        scroll.set(0, 0);
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            keyPressed[key] = true;
            keyJustPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            keyPressed[key] = false;
            // keyJustPressed[key] = false;
        }
    }

    public static void mousePosCallback(long window, double newPositionX, double newPositionY) {

        lastPosition.x = position.x;
        lastPosition.y = position.y;

        position.x = (float) newPositionX;
        position.y = (float) newPositionY;

    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {

        if (action == GLFW_PRESS) {
            isMouseButtonPressed = true;

            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = true;
            }
        } else if (action == GLFW_RELEASE) {
            isMouseButtonPressed = false;

            if (button < mouseButtonPressed.length) {
                mouseButtonPressed[button] = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double offsetX, double offsetY) {
        scroll.x = (float) offsetX;
        scroll.y = (float) offsetY;
    }

    public static boolean isKeyPressed(int keyCode) {
        return keyPressed[keyCode];
    }

    public static boolean isKeyJustPressed(int keyCode) {
        return keyJustPressed[keyCode];
    }

    public static boolean isDragging() {
        return position.x != lastPosition.x && position.y != lastPosition.y && isMouseButtonPressed;
    }

    public static Vector2f getScreenMousePosition() {
        return position;
    }

    public static Vector2f getGlobalMousePosition() {
        Camera camera = Window.getScene().getCamera();
        return new Vector2f(camera.getPosition());
    }
}
