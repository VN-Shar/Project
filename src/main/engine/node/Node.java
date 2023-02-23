package engine.node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import engine.Scene;
import engine.node._2D.Transform2D;

public class Node {

    private static int object_counter = 0;
    private static HashMap<String, Integer> object_map = new HashMap<String, Integer>();

    private int uid = -1;
    private String name;

    private Node parent;
    private List<Node> children;
    private boolean isAlive = true;
    private int zIndex = 0;

    private Transform2D transform;
    private Scene tree;

    public Node() {

        String name = getClass().getSimpleName();

        uid = object_counter++;
        if (object_map.containsKey(name)) {
            int id = object_map.get(name);
            this.name = name + String.valueOf(id);
            object_map.put(name, ++id);

        } else {
            this.name = name;
            object_map.put(name, 0);
        }

        System.out.println(this.name);

        transform = new Transform2D();
        children = new ArrayList<Node>();

        ready();
    }

    public int getUID() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public void setTree(Scene tree) {
        this.tree = tree;
        for (Node child : getChildren())
            child.setTree(tree);
    }

    public Scene getTree() {
        return tree;
    }

    public void addChild(Node child) {
        child.parent = this;
        child.tree = tree;
        children.add(child);
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getNode(String nodeName) {
        for (Node node : children)
            if (node.name.equals(nodeName))
                return node;
        return null;
    }

    public Node getParent() {
        return this.parent;
    }

    public Transform2D getTransform() {
        return this.transform;
    }

    public Transform2D getGlobalTransform() {
        if (this.parent == null)
            return transform.copy();

        return transform.copy().translate(parent.getGlobalTransform());
    }

    public void setTransform(Transform2D transform) {
        this.transform = transform;
    }

    public void update(float deltaTime) {
        for (Node child : children) {
            child.update(deltaTime);
        }

        process(deltaTime);
    }

    public void render() {
        for (Node child : children) {
            child.render();
        }
    }

    public void free() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean value) {
        this.isAlive = value;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return this.zIndex;
    }

    public int getGlobalZIndex() {
        if (getParent() == null)
            return getZIndex();
        return getZIndex() + getParent().getGlobalZIndex();
    }

    // Overide
    public void ready() {

    }

    // Overide
    public void process(float deltaTime) {

    }
}
