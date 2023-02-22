package engine.node.UI;

public class Color {
    public float r, g, b, a;

    public Color(float r, float b, float g, float a) {
        this.r = r;
        this.b = b;
        this.g = g;
        this.a = a;
    }

    public Color lerp(Color target, float value) {
        return new Color(//
                r + (target.r - r) * value, //
                g + (target.g - g) * value, //
                b + (target.b - b) * value, //
                a + (target.a - a) * value);
    }

    @Override
    public String toString() {
        return "color: (" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
