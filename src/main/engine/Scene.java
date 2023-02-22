package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;

import org.joml.Vector2f;

import engine.node.Node;

public class Scene {

    private Window window;
    private Camera camera;

    private List<Node> gameObjects;
    private List<Node> queueObjects;
    private List<Node> queueFreeObjects;

    private boolean isPaused = false;

    public Scene(Window window) {
        this.window = window;
        this.camera = new Camera(new Vector2f(), new Vector2f(window.getWidth(), window.getHeight()));

        gameObjects = new ArrayList<Node>();
        queueObjects = new ArrayList<Node>();
        queueFreeObjects = new ArrayList<Node>();

        ready();
    }

    public List<Node> getComponents() {
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

    public void queueAddObject(Node component) {
        queueObjects.add(component);
    }

    public void queueRemoveObject(Node component) {
        queueFreeObjects.add(component);
    }

    public void addObject(Node component) {
        gameObjects.add(component);
    }

    public void removeObject(Node component) {
        gameObjects.remove(component);
    }

    public void update(float deltaTime) {
        if (isPaused)
            return;

        for (Node component : gameObjects) {
            component.update(deltaTime);
        }

        process(deltaTime);
    }

    public void render() {
        for (Node component : gameObjects) {
            component.render();
        }
    }

    public void beginFrame() {
        Iterator<Node> it = queueObjects.iterator();
        Node component;
        while (it.hasNext()) {
            component = it.next();
            addObject(component);
            it.remove();
        }
    }

    public void endFrame() {
        Iterator<Node> it = queueFreeObjects.iterator();
        Node component;
        while (it.hasNext()) {
            component = it.next();
            it.remove();
            component.free();
        }
    }

    public void free() {
        for (Node Component : gameObjects) {
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
