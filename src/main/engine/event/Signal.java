package engine.event;

import java.util.LinkedList;
import java.util.function.Consumer;

import engine.util.Log;

public final class Signal<T> {

    private LinkedList<Consumer<T>> listeners = new LinkedList<>();

    public void emit(T value) {
        for (Consumer<T> l : listeners)
            l.accept(value);
    }

    public void connect(Consumer<T> listener) {
        if (listeners.contains(listener)) {
            Log.error("Signal already connected from to " + listener.toString());
            return;
        }

        listeners.add(listener);
    }

    public void disconnect(Consumer<T> listener) {
        listeners.remove(listener);
    }

}
