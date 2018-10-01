package pbrookes.philthi.android3d;

public class Vertex3D {
    private float x, y, z;

    public Vertex3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex3D() {
        x = y = z = 0;
    }

    public Vertex3D(Vertex3D in) {
        this.clone(in);
    }

    public void clone(Vertex3D in) {
        x = in.x;
        y = in.y;
        z = in.z;
    }

    public void setX(float newX) {
        x = newX;
    }

    public void setY(float newY) {
        y = newY;
    }

    public void setZ(float newZ) {
        z = newZ;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float[] getXY() {
        return new float[]{x, y};
    }

    public float[] getXYZ() {
        return new float[]{x, y, z};
    }

    public boolean equals(Vertex3D in) {
        return (in.getX() == x && in.getY() == y && in.getZ() == z);
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}
