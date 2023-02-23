package engine;

public class Engine {

    public Engine() {
        Window window = Window.get();
        window.run();
    }

    public static void printStackTrace() {
        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(ste);
        }
    }
}
