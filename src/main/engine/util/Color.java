package engine.util;

public class Color {
    public float r, g, b, a;

    public Color(float r, float b, float g, float a) {
        this.r = r;
        this.b = b;
        this.g = g;
        this.a = a;
    }

    @Override
    public String toString() {
        return "color: (" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
