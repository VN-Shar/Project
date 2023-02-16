package engine.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import engine.util.Transform;

public class Component {

    private static int OBJECT_COUNTER = 0;
    private static HashMap<String, Integer> OBJECT_MAP = new HashMap<String, Integer>();

    private int uid = -1;
    private String name;

    protected List<Component> components;
    protected boolean isAlive = true;

    public Transform transform = new Transform();

    public Component() {

        String name = getClass().getSimpleName();

        uid = OBJECT_COUNTER++;
        if (OBJECT_MAP.containsKey(name)) {
            int id = OBJECT_MAP.get(name);
            this.name = name + String.valueOf(id);
            OBJECT_MAP.put(name, ++id);

        } else {
            this.name = name;
            OBJECT_MAP.put(name, 0);
        }

        components = new ArrayList<Component>();
    }

    public int getUID() {
        return this.uid;
    }

    public String getName() {
        return this.name;
    }

    public void addComponent(Component component) {
        components.add(component);
    }

    public void update(float deltaTime) {
        for (Component component : components) {
            component.update(deltaTime);
        }
    }

    public void render() {
        for (Component component : components) {
            component.render();
        }
    }

    public void free() {
        this.isAlive = false;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
}