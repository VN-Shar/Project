package engine;

import org.joml.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import engine.editor.scenes.EditorScene;
import engine.event.EventHandler;
import engine.event.EventType;
import engine.input.InputListener;
import engine.renderer.VisualServer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.util.Objects;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;

    private static final int DEFAULT_WINDOW_WIDTH = 1080;
    private static final int DEFAULT_WINDOW_HEIGHT = 720;

    private static Scene scene = null;
    private static Window window = null;

    private float deltaTime = -1;
    private float fps = 144;

    public static long frame = 0;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window(Window.DEFAULT_WINDOW_WIDTH, Window.DEFAULT_WINDOW_HEIGHT, "Engine");
        }

        return Window.window;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void changeScene(Scene newScene) {
        if (Window.scene != null)
            Window.scene.free();
        Window.scene = newScene;
    }

    public static Scene getScene() {
        return Window.scene;
    }

    public long getGLFWWindow() {
        return this.glfwWindow;
    }

    public float getDeltaTime() {
        return this.deltaTime;
    }

    public float getFps() {
        return this.fps;
    }

    public void run() {

        init();
        loop();
        close();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        EventHandler.invoke(EventType.EngineInit);

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        // No title
        // glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        // Enable full screen
        // glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Get screen height and width
        GLFWVidMode vidMode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));
        int local_height = Math.min(height, vidMode.height());
        int local_width = Math.min(width, vidMode.width());

        // Create the window
        glfwWindow = glfwCreateWindow(local_width, local_height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // Callbacks
        glfwSetCursorPosCallback(glfwWindow, InputListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, InputListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, InputListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, InputListener::keyCallback);
        glfwSetWindowSizeCallback(glfwWindow, this::windowSizeChanged);

        glfwSetWindowRefreshCallback(glfwWindow, window -> render());

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        // Init the default scene
        Window.scene = new EditorScene(window);

        windowSizeChanged(glfwWindow, local_width, local_height);

        EventHandler.invoke(EventType.EngineLoad);

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

    }

    private void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float totalTime = 0;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT);
            glClearColor(0, 0, 0, 1);
            beginFrame();

            update(deltaTime);
            render();

            if (InputListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            glfwSwapBuffers(glfwWindow);
            endFrame();

            endTime = (float) glfwGetTime();
            deltaTime = endTime - beginTime;
            beginTime = endTime;

            frame += 1;
            totalTime += deltaTime;
            if (frame % 60 == 0) {
                fps = Math.round(60 / totalTime);
                totalTime = 0;

                System.out.println("FPS: " + fps);
            }
        }
    }

    public static float getTime() {
        return (float) glfwGetTime();
    }

    private void close() {
        EventHandler.invoke(EventType.EngineClose);

        Window.scene.free();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void windowSizeChanged(long glfwWindow, int width, int height) {
        this.glfwWindow = glfwWindow;

        GLFWVidMode vidMode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));
        int local_height = Math.min(height, vidMode.height());
        int local_width = Math.min(width, vidMode.width());

        setHeight(local_height);
        setWidth(local_width);
        glViewport(0, 0, local_width, local_height);
        Window.scene.getCamera().setSize(new Vector2f(local_width, local_height));
    }

    private void update(float delta) {
        Window.scene.update(delta);
    }

    private void render() {
        VisualServer.render();
        Window.scene.render();
    }

    private void beginFrame() {
        Window.scene.beginFrame();
    }

    private void endFrame() {
        InputListener.endFrame();
        Window.scene.endFrame();
    }

}
