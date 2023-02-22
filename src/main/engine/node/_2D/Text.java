package engine.node._2D;

import engine.node.Node2D;
import engine.node._2D.Alignment.HorizontalAlignment;
import engine.node._2D.Alignment.VerticalAlignment;
import engine.renderer.AssetPool;
import engine.renderer.Font;
import engine.renderer.VisualServer;

public class Text extends Node2D {

    private Font font;

    private String content = new String();

    private String defaultFontPath = "D:/Java/Project/assets/fonts/NotoSans-Black.ttf";

    public boolean isKerning = false;
    public boolean autoWarp = true;

    public VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
    public HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;

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

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
    }

    public void setText(String content) {
        this.content = content;
    }

    public String getText() {
        return this.content;
    }

}
