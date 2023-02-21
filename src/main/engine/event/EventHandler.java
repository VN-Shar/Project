package engine.event;

import java.util.HashMap;
import java.util.function.Consumer;

public class EventHandler {

    // Signals container
    public static HashMap<String, Observer<Event>> observers = new HashMap<>();

    private static final String GLOBAL = "Global ";

    public static <T extends Event> void connect(Object source, Class<T> type, Consumer<Event> callback) {
        String eventName = getSignalName(source, type);

        if (observers.containsKey(eventName)) {
            throw new IllegalArgumentException("Signal from " + source + " with type " + type + " already exits");
        } else {
            Observer<Event> observer = new Observer<Event>();
            observer.addListener(callback);
            observers.put(eventName, observer);
        }
    }

    public static <T extends Event> void connect(Class<T> type, Consumer<Event> callback) {
        String eventName = getSignalName(GLOBAL, type);

        if (observers.containsKey(eventName)) {
            throw new IllegalArgumentException("Signal from global with type " + type + " already exits");
        } else {
            Observer<Event> observer = new Observer<Event>();
            observer.addListener(callback);
            observers.put(eventName, observer);
        }
    }

    public static <T extends Event> void disconnect(Object source, Class<T> type, Consumer<Event> callback) {
        String eventName = getSignalName(source, type);
        observers.remove(eventName);
    }

    public static <T extends Event> void disconnect(Class<T> type, Consumer<Event> callback) {
        String eventName = getSignalName(GLOBAL, type);
        observers.remove(eventName);
    }

    public static <T extends Event> void invoke(Object source, T type) {
        Observer<Event> observer = observers.get(getSignalName(source, type));
        if (observer != null)
            observer.invoke(type);
    }

    public static <T extends Event> void invoke(T type) {
        Observer<Event> observer = observers.get(getSignalName(GLOBAL, type));
        if (observer != null)
            observer.invoke(type);
    }

    private static <T> String getSignalName(Object source, Class<T> type) {
        return source.toString() + "class " + type.getTypeName();
    }

    private static <T> String getSignalName(Object source, T type) {
        return source.toString() + type.getClass();

    }
}
