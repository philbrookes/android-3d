package pbrookes.philthi.android3d;

public class Position {
    private float x, y, z;

    public Position(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Position() {
        x = y = z = 0;
    }

    public Position(Position in) {
        this.clone(in);
    }

    public void clone(Position in) {
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

    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}
