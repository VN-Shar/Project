package engine;

import org.joml.Vector2f;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import engine.editor.scenes.EditorScene;
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

    public void setWidth(int width) {
        this.width = width;
    }

    public void changeScene(Scene newScene) {
        if (Window.scene != null)
            Window.scene.free();
        Window.scene = newScene;
    }

    public static Scene getScene() {
        return Window.scene;
    }

    public int getGLFWWindow() {
        return this.getGLFWWindow();
    }

    public float getDeltaTime() {
        return deltaTime;
    }

    public float getFps() {
        fps = Math.round(1 / deltaTime);
        return fps;
    }

    public void run() {

        init();
        loop();
        close();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_SAMPLES, 8);
        // glEnable(GL_MULTISAMPLE);
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
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
        glfwSetWindowRefreshCallback(glfwWindow, window -> render());
        glfwSetWindowSizeCallback(glfwWindow, this::windowSizeChanged);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        Window.scene = new EditorScene();

        windowSizeChanged(glfwWindow, local_width, local_height);

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

            if (deltaTime >= 0) {
                update(deltaTime);
                render();
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
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
                System.out.println("FPS: " + Math.round(60 / totalTime));
                totalTime = 0;
            }
        }
    }

    private void close() {
        // Free the memory
        Window.scene.free();

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void windowSizeChanged(long glfwWindow, int width, int height) {
        this.glfwWindow = glfwWindow;
        setHeight(height);
        setWidth(width);
        glViewport(0, 0, width, height);
        Window.scene.getCamera().setSize(new Vector2f(width, height));
    }

    private void update(float delta) {
        Window.scene.update(delta);
    }

    private void render() {
        VisualServer.render();
    }

    private void beginFrame() {
        Window.scene.beginFrame();
    }

    private void endFrame() {
        KeyListener.endFrame();
        MouseListener.endFrame();
        Window.scene.endFrame();

    }

}
