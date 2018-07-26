package pbrookes.philthi.android3d;

public class Color {
    private float r, g, b, alpha;
    public Color(float r, float g, float b, float alpha) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.alpha = alpha;
    }

    public float[] toArray() {
        return new float[] {r,g,b,alpha};
    }

    public float red() {
        return r;
    }

    public float blue() {
        return b;
    }

    public float green() {
        return g;
    }

    public float alpha() {
        return alpha;
    }

    public static Color Red(){
        return new Color(1.0f, 0.0f, 0.0f, 1.0f);
    }
    public static Color Green(){
        return new Color(0.0f, 1.0f, 0.0f, 1.0f);
    }
    public static Color Blue(){
        return new Color(0.0f, 0.0f, 1.0f, 1.0f);
    }
    public static Color Black(){
        return new Color(0.0f, 0.0f, 0.0f, 1.0f);
    }
    public static Color White(){
        return new Color(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
