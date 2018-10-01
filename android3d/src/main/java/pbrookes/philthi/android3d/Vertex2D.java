package pbrookes.philthi.android3d;

public class Vertex2D {
    private float x, y;

    public Vertex2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vertex2D() {
        x = y = 0;
    }

    public Vertex2D(Vertex2D in) {
        this.clone(in);
    }

    public void clone(Vertex2D in) {
        x = in.x;
        y = in.y;
    }

    public void setX(float newX) {
        x = newX;
    }

    public void setY(float newY) {
        y = newY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
    public float[] getXY() {
        return new float[]{x, y};
    }

    public boolean equals(Vertex2D in) {
        return (in.getX() == x && in.getY() == y);
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
