package engine.event;

import java.util.HashMap;
import java.util.function.Consumer;

public class SignalHandler {

    // Signals container
    public static HashMap<String, Observer> observers = new HashMap<>();

    public static <T> void connect(Object source, Class<T> type, Consumer<Signal> callback) {
        String eventName = getSignalName(source, type);

        if (observers.containsKey(eventName)) {
            throw new IllegalArgumentException("Signal from " + source + " with type " + type + " already exits");
        } else {
            Observer observer = new Observer();
            observer.addListener(callback);
            observers.put(eventName, observer);
        }
    }

    public static <T> void disconnect(Object source, Class<T> type, Consumer<Signal> callback) {
        String eventName = getSignalName(source, type);
        observers.remove(eventName);
    }

    public static void emit(Object source, Signal type) {
        Observer observer = observers.get(getSignalName(source, type));
        if (observer != null)
            observer.emit(type);
    }
    
    private static <T> String getSignalName(Object source, Class<T> type) {
        return source.toString() + "class " + type.getTypeName();
    }

    private static String getSignalName(Object source, Signal type) {
        return source.toString() + type.getClass();

    }
}
