package pbrookes.philthi.android3d;

public class Vertex4D {
    private float x, y, z, a;

    public Vertex4D(float x, float y, float z, float a) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.a = a;
    }

    public Vertex4D() {
        x = y = z = a = 0;
    }

    public Vertex4D(float[] floats){
        float[] newFloats = {0,0,0,0};
        for(int i=0; i<=floats.length && i<4;i++){
            newFloats[i] = floats[i];
        }
        x = newFloats[0];
        y = newFloats[1];
        z = newFloats[2];
        a = newFloats[3];
    }

    public Vertex4D(Vertex4D in) {
        this.clone(in);
    }

    public void clone(Vertex4D in) {
        x = in.x;
        y = in.y;
        z = in.z;
        a = in.a;
    }

    public Vertex4D normal(){
        float mag = (float)Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2) + Math.pow(this.a, 2));
        return new Vertex4D(x / mag, y / mag, z / mag, a / mag);
    }

    public void normalise() {
        Vertex4D newVal = normal();
        this.clone(newVal);
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

    public void setA(float newA) {
        a = newA;
    }

    public void setXY(float newX, float newY) { x = newX; y = newY; }

    public void setXYZ(float newX, float newY, float newZ) { x = newX; y = newY; z = newZ; }

    public void setXYZA(float newX, float newY, float newZ, float newA) { x = newX; y = newY; z = newZ; a = newA; }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getA() {
        return a;
    }

    public float[] getXY() {
        return new float[]{x, y};
    }

    public float[] getXYZ() {
        return new float[]{x, y, z};
    }

    public float[] getXYZA() {
        return new float[]{x, y, z, a};
    }

    public void add(Vertex4D in) {
        x += in.x;
        y += in.y;
        z += in.z;
        a += in.a;
    }

    public boolean equals(Vertex4D in) {
        return (in.getX() == x && in.getY() == y && in.getZ() == z && in.getA() == a);
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ", a: " + a + ")";
    }
}
