package engine.renderer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AssetPool {
    private static Map<String, Shader> shaders = new HashMap<>();
    private static Map<String, Texture> textures = new HashMap<>();

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
}
