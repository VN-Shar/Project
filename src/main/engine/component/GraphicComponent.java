package engine.component;

import org.joml.Vector2f;
import org.joml.Vector4f;

public class GraphicComponent extends Component {

    protected boolean isDirty = true;
    protected boolean isShow = true;
    protected int zIndex = 0;

    public static Vector2f[] texCoords = {
            new Vector2f(1, 1),
            new Vector2f(1, 0),
            new Vector2f(0, 0),
            new Vector2f(0, 1)
    };

    public Vector4f color = new Vector4f(1, 1, 1, 1);

    public void setClean() {
        this.isDirty = false;
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

}
