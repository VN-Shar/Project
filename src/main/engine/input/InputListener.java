package engine.input;

import org.joml.Vector2f;

import engine.Camera;
import engine.Window;
import engine.event.Signal;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class InputListener {

    // Keyboard listener
    private static boolean keyPressed[] = new boolean[350];

    // Mouse listener
    private static Vector2f scroll = new Vector2f();
    private static Vector2f position = new Vector2f(), lastPosition = new Vector2f(), relative = new Vector2f();
    private static boolean mouseButtonPressed[] = new boolean[9];
    private static boolean isMouseButtonPressed;

    public static Signal<Integer> onKeyPressed = new Signal<>();

    public static void endFrame() {
        scroll.set(0, 0);
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {

        if (action == GLFW_PRESS) {
            KeyBinding.onKeyCallback(key, true);
            keyPressed[key] = true;

            onKeyPressed.emit(key);

        } else if (action == GLFW_RELEASE) {
            KeyBinding.onKeyCallback(key, false);
            keyPressed[key] = false;
        } else {

            // Key hold
        }
    }

    public static void mousePosCallback(long window, double newPositionX, double newPositionY) {

        lastPosition.x = position.x;
        lastPosition.y = position.y;

        position.x = (float) newPositionX;
        position.y = (float) newPositionY;

        relative.x = position.x - lastPosition.x;
        relative.y = position.y - lastPosition.y;

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

    public static boolean isDragging() {
        return position.x != lastPosition.x && position.y != lastPosition.y && isMouseButtonPressed;
    }

    public static Vector2f getScreenMousePosition() {
        return new Vector2f(position);
    }

    public static Vector2f getGlobalMousePosition() {
        Camera camera = Window.getScene().getCamera();

        return new Vector2f(
                camera.getPosition().add(getScreenMousePosition().add(camera.getSize().div(-2)).mul(camera.getZoom())));
    }

    public static Vector2f getMouseRelative() {
        if (isDragging()) {
            return new Vector2f(relative);
        }
        return new Vector2f();
    }

    public static Vector2f getMouseScroll() {
        return new Vector2f(scroll);
    }
}
