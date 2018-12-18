package pbrookes.philthi.android3d;

public class Vertex2D {
    private float x, y;

    public Vertex2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vertex2D(float[] floats){
        float[] newFloats = {0,0};
        for(int i=0; i<=floats.length && i<2;i++){
            newFloats[i] = floats[i];
        }
        x = newFloats[0];
        y = newFloats[1];
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

    public Vertex2D normal(){
        float mag = (float)Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2));
        return new Vertex2D(x / mag, y / mag);
    }

    public void normalise() {
        Vertex2D newVal = normal();
        this.clone(newVal);
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

    public void add(Vertex2D in) {
        x += in.x;
        y += in.y;
    }

    public boolean equals(Vertex2D in) {
        return (in.getX() == x && in.getY() == y);
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ")";
    }
}
