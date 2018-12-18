package pbrookes.philthi.android3d;

public class Vertex3D {
    private float x, y, z;

    public Vertex3D(float x, float y, float  z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vertex3D() {
        x = y = z = 0;
    }


    public Vertex3D(float[] floats){
        float[] newFloats = {0,0,0};
        for(int i=0; i<=floats.length && i<3;i++){
            newFloats[i] = floats[i];
        }
        x = newFloats[0];
        y = newFloats[1];
        z = newFloats[2];
    }

    public Vertex3D(Vertex3D in) {
        this.clone(in);
    }

    public void clone(Vertex3D in) {
        x = in.x;
        y = in.y;
        z = in.z;
    }

    public Vertex3D normal(){
        float mag = (float)Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
        return new Vertex3D(x / mag, y / mag, z / mag);
    }

    public void normalise() {
        Vertex3D newVal = normal();
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

    public void add(Vertex3D in) {
        x += in.x;
        y += in.y;
        z += in.z;
    }

    public float distance(Vertex3D in) {
        return (float)Math.sqrt(Math.pow(x - in.getX(), 2) + Math.pow(y - in.getY(), 2) + Math.pow(z - in.getZ(), 2));
    }


    public boolean equals(Vertex3D in) {
        return (in.getX() == x && in.getY() == y && in.getZ() == z);
    }

    public String toString() {
        return "(x: " + x + ", y: " + y + ", z: " + z + ")";
    }
}
