package engine.event;

import org.joml.Vector2f;

import engine.Scene;

public enum EventType implements Event {
    // Engine
    EngineInit, // before glfwInit get called
    EngineLoad, //
    EngineClose; // before freeing everything

    public static class SceneChanged implements Event {
        public final Scene scene;

        public SceneChanged(Scene scene) {
            this.scene = scene;
        }
    }

    public static class WindowResized implements Event {

        public final Vector2f size;

        public WindowResized(Vector2f size) {
            this.size = size;
        }
    }
}
