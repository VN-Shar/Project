package engine.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import engine.renderer.Shader;
import engine.renderer.Texture;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();
    public static Map<String, Font> fonts = new HashMap<>();

    public static Shader getShader(String resourceName) {
        File file = new File(resourceName);
        String absolutePath = file.getAbsolutePath();

        if (AssetPool.shaders.containsKey(absolutePath)) {
            return AssetPool.shaders.get(absolutePath);

        } else {
            Shader shader = new Shader(resourceName);
            shader.compile();
            AssetPool.shaders.put(absolutePath, shader);
            return shader;
        }
    }

    public static Texture getTexture(String resourceName) {
        File file = new File(resourceName);
        String absolutePath = file.getAbsolutePath();

        if (AssetPool.textures.containsKey(absolutePath)) {
            return AssetPool.textures.get(absolutePath);

        } else {
            Texture texture = new Texture(resourceName);
            AssetPool.textures.put(absolutePath, texture);
            return texture;
        }
    }

    public static Font getFont(String resourceName) {
        File file = new File(resourceName);
        String absolutePath = file.getAbsolutePath();

        if (AssetPool.fonts.containsKey(absolutePath)) {
            return AssetPool.fonts.get(absolutePath);

        } else {
            Font font = new Font(resourceName, 24, 4 * 512, 4 * 512);
            AssetPool.fonts.put(absolutePath, font);
            return font;
        }
    }
}
