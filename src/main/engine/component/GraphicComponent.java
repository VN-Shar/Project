package engine.component;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class GraphicComponent extends Component {

    private boolean isDirty = true;
    private boolean isShow = true;
    private int zIndex = 0;
    private Vector4f color = new Vector4f(1, 1, 1, 1);

    public static Vector2f[] texCoords = { new Vector2f(1, 1), new Vector2f(1, 0), new Vector2f(0, 0), new Vector2f(0, 1) };

    public void setClean() {
        this.isDirty = false;
    }

    public void setDirty() {
        this.isDirty = true;
    }

    public boolean isDirty() {
        return this.isDirty && isShowing();
    }

    public void show() {
        this.isShow = true;
    }

    public void hide() {
        this.isShow = false;
    }

    public boolean isShowing() {
        return this.isShow;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return this.zIndex;
    }

    public void setColor(Vector4f color) {
        this.color = color;
    }

    public Vector4f getColor() {
        return this.color;
    }

}
