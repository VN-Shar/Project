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

    private List<Node> nodes;
    private List<Node> queueNodes;
    private List<Node> queueFreeNodes;

    private boolean isPaused = false;

    public Scene(Window window) {
        this.window = window;
        this.camera = new Camera(new Vector2f(), new Vector2f(window.getWidth(), window.getHeight()));

        nodes = new ArrayList<Node>();
        queueNodes = new ArrayList<Node>();
        queueFreeNodes = new ArrayList<Node>();

        ready();
    }

    public List<Node> getNodes() {
        return nodes;
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

    // Add node to the queue
    public void addChild(Node node) {
        queueNodes.add(node);
    }

    public void queueFree(Node node) {
        queueFreeNodes.add(node);
    }

    public void update(float deltaTime) {
        if (isPaused)
            return;

        for (Node node : nodes) {
            node.update(deltaTime);
        }

        process(deltaTime);
    }

    public void render() {
        for (Node node : nodes) {
            node.render();
        }
    }

    public void beginFrame() {
        // Add nodes at be beginning of the frame
        Iterator<Node> it = queueNodes.iterator();
        Node node;
        while (it.hasNext()) {
            node = it.next();
            node.setTree(this);
            nodes.add(node);
            it.remove();
        }
    }

    public void endFrame() {
        // Remove nodes at the end of the frame
        Iterator<Node> it = queueFreeNodes.iterator();
        Node node;
        while (it.hasNext()) {
            node = it.next();
            nodes.remove(node);
            node.free();
            it.remove();
        }
    }

    public void free() {
        for (Node node : nodes) {
            node.free();
        }
    }

    // Overide by children
    public void ready() {

    }

    // Overide by children
    public void process(float deltaTime) {

    }
}
