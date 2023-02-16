package engine.event;

import engine.Scene;

public enum SignalType implements Signal {
    // Engine
    engineInit,
    engineLoad,
    engineClose;

    public static class sceneChanged implements Signal {
        public final Scene scene;

        public sceneChanged(Scene scene) {
            this.scene = scene;
        }
    }
}
