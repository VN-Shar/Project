package engine.component._2D;

import engine.component.GraphicComponent;
import engine.renderer.VisualServer;
import engine.util.AssetPool;
import engine.util.Font;
import engine.util.Transform;

public class Text extends GraphicComponent {

    private Font font;

    public String content = new String();
    public Transform transform = new Transform();

    public boolean isKerning = false;
    public String defaultFontPath = "D:/Java/Project/assets/fonts/Roboto-Black.ttf";

    public Text() {
        init(AssetPool.getFont(defaultFontPath), new String());
    }

    public Text(String content) {
        init(AssetPool.getFont(defaultFontPath), content);
    }

    public Text(Font font) {
        init(font, new String());
    }

    public Text(Font font, String content) {
        init(font, content);
    }

    private void init(Font font, String content) {
        this.font = font;
        this.content = content;
        VisualServer.draw(this);
    }

    public Font getFont() {
        return this.font;
    }

}
