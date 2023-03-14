package engine.input;

import java.util.concurrent.ConcurrentHashMap;

import engine.Window;

import static org.lwjgl.glfw.GLFW.*;

public class KeyBinding {

    public static enum Key {

        UP(GLFW_KEY_W, GLFW_KEY_UP),

        DOWN(GLFW_KEY_S, GLFW_KEY_DOWN),

        LEFT(GLFW_KEY_A, GLFW_KEY_LEFT),

        RIGHT(GLFW_KEY_D, GLFW_KEY_RIGHT);

        private int[] keyCode;

        Key(int... keyCode) {
            this.keyCode = keyCode;
        }
    }

    private static ConcurrentHashMap<String, KeyState> keyMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, String> bindMap = new ConcurrentHashMap<>();

    static {
        for (Key k : Key.values()) {
            for (int code : k.keyCode)
                addKeyBind(code, k.name());
        }
    }

    public static void addKeyBind(int GLFW_KEY, String bindName) {
        bindMap.put(GLFW_KEY, bindName);
        keyMap.put(bindName, new KeyBinding().new KeyState(false, -1));
    }

    public static void onKeyCallback(int key, boolean isPressed) {
        String bindName = bindMap.get(key);
        if (bindName == null)
            return;

        if (keyMap.containsKey(bindName)) {
            KeyState state = keyMap.get(bindName);
            if (state.isPressed != isPressed)
                state.frame = Window.getFrame();
            state.isPressed = isPressed;
        }
    }

    public static boolean isKeyPressed(String bindName) {
        if (keyMap.containsKey(bindName)) {
            return keyMap.get(bindName).isPressed;
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static boolean isKeyJustPressed(String bindName) {
        if (keyMap.containsKey(bindName)) {
            return keyMap.get(bindName).isValue(true, Window.getFrame());
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static boolean isKeyJustReleased(String bindName) {
        if (keyMap.containsKey(bindName)) {
            return keyMap.get(bindName).isValue(false, Window.getFrame());
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static boolean isKeyPressed(Key bindName) {
        if (keyMap.containsKey(bindName.name())) {
            return keyMap.get(bindName.name()).isPressed;
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static boolean isKeyJustPressed(Key bindName) {
        if (keyMap.containsKey(bindName.name())) {
            return keyMap.get(bindName.name()).isValue(true, Window.getFrame());
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static boolean isKeyJustReleased(Key bindName) {
        if (keyMap.containsKey(bindName.name())) {
            return keyMap.get(bindName.name()).isValue(false, Window.getFrame());
        } else
            throw new IllegalArgumentException("No key binding for <" + bindName + ">");
    }

    public static void removeKeyBind(int GLFW_KEY) {
        String bindName = bindMap.get(GLFW_KEY);
        if (bindName != null) {
            keyMap.remove(bindName);
        }
        bindMap.remove(GLFW_KEY);
    }

    private class KeyState {

        KeyState(boolean isPressed, long frame) {
            this.isPressed = isPressed;
            this.frame = frame;
        }

        private boolean isPressed = false;
        private long frame = 0;

        private boolean isValue(boolean isPressed, long frame) {
            return this.isPressed == isPressed && this.frame == frame;
        }
    }
}
