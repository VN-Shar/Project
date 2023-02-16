package engine.event;

import java.util.LinkedList;
import java.util.function.Consumer;

public class Observer {

    public LinkedList<Consumer<Signal>> callbacks = new LinkedList<>();

    public void emit(Signal type) {
        for (Consumer<Signal> callback : callbacks) {
            callback.accept(type);
        }
    }

    public void addListener(Consumer<Signal> callback) {
        if (callbacks.contains(callback))
            return;
        callbacks.add(callback);
    }

    public void removeListener(Consumer<Signal> callback) {
        callbacks.remove(callback);
    }

}
