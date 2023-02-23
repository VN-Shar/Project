package engine.event;


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
}
