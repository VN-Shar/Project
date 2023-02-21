package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.joml.Vector2f;

import engine.component.Component;

public class Scene {

    private Window window;
    private Camera camera;

    private List<Component> gameObjects;
    private List<Component> queueObjects;
    private List<Component> queueFreeObjects;

    private boolean isPaused = false;

    public Scene(Window window) {
        this.window = window;
        this.camera = new Camera(new Vector2f(), new Vector2f(window.getWidth(), window.getHeight()));

        gameObjects = new ArrayList<Component>();
        queueObjects = new ArrayList<Component>();
        queueFreeObjects = new ArrayList<Component>();

        ready();
    }

    public List<Component> getComponents() {
        return gameObjects;
    }

    public Window getWindow() {
        return window;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(@Nonnull Camera camera) {
        this.camera = camera;
    }

    public void queueAddObject(Component component) {
        queueObjects.add(component);
    }

    public void queueRemoveObject(Component component) {
        queueFreeObjects.add(component);
    }

    public void addObject(Component component) {
        gameObjects.add(component);
    }

    public void removeObject(Component component) {
        gameObjects.remove(component);
    }

    public void update(float deltaTime) {
        if (isPaused)
            return;

        for (Component component : gameObjects) {
            component.update(deltaTime);
        }

        process(deltaTime);
    }

    public void render() {
        for (Component component : gameObjects) {
            component.render();
        }
    }

    public void beginFrame() {
        Iterator<Component> it = queueObjects.iterator();
        Component component;
        while (it.hasNext()) {
            component = it.next();
            addObject(component);
            it.remove();
        }
    }

    public void endFrame() {
        Iterator<Component> it = queueFreeObjects.iterator();
        Component component;
        while (it.hasNext()) {
            component = it.next();
            it.remove();
            component.free();
        }
    }

    public void free() {
        for (Component Component : gameObjects) {
            Component.free();
        }
    }

    // Overide by children
    public void ready() {

    }

    // Overide by children
    public void process(float deltaTime) {

    }
}
