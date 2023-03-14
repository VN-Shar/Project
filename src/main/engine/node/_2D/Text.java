package engine.node._2D;

import engine.node.Node2D;
import engine.node.Transform2D;
import engine.node.FlagType.Alignment;
import engine.renderer.AssetPool;
import engine.renderer.Font;
import engine.renderer.VisualServer;

public class Text extends Node2D {

    private static String defaultFontPath = "D:/Java/Project/assets/fonts/NotoSans-Black.ttf";

    private Font font;

    private String content = new String();

    public boolean isKerning = false;
    public boolean autoWarp = true;

    public Alignment verticalAlignment = Alignment.CENTER;
    public Alignment horizontalAlignment = Alignment.CENTER;

    public Text() {
        this(AssetPool.getFont(defaultFontPath), new String(), new Transform2D());
    }

    Text(Transform2D transform) {
        this(AssetPool.getFont(defaultFontPath), new String(), transform);
    }

    public Text(String content) {
        this(AssetPool.getFont(defaultFontPath), content, new Transform2D());
    }

    public Text(Font font) {
        this(font, new String(), new Transform2D());
    }

    public Text(Font font, String content, Transform2D transform) {
        this.setTransform(transform);
        this.font = font;
        this.content = content;
        VisualServer.draw(this);
    }

    public void setFont(Font font) {
        if (this.font.equals(font))
            return;

        this.font = font;
        dirty();
    }

    public Font getFont() {
        return font;
    }

    public void setText(String content) {
        if (this.content.equals(content))
            return;

        this.content = content;
        dirty();
    }

    public String getText() {
        return content;
    }

}
