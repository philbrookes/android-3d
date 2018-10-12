package pbrookes.philthi.android3d;

import android.util.Log;

public class Camera {
    protected Vertex3D pos;
    protected Vertex3D lookAt;
    protected Vertex3D up;

    public Camera() {
        pos = new Vertex3D();
        lookAt = new Vertex3D();
        up = new Vertex3D();
        up.setY(1.0f);
    }

    public void rotate(float angle) {
        float relX = pos.getX() - lookAt.getX();
        float relZ = pos.getZ() - lookAt.getZ();
        double newX = lookAt.getX() + (pos.getX()-lookAt.getX())*Math.cos(angle) - (pos.getZ()-lookAt.getZ())*Math.sin(angle);
        double newZ = lookAt.getZ() + (pos.getX()-lookAt.getX())*Math.sin(angle) + (pos.getZ()-lookAt.getZ())*Math.cos(angle);

        pos.setX((float)newX);
        pos.setZ((float)newZ);
    }

    public Vertex3D getPos() {
        return pos;
    }

    public Vertex3D getLookAt() {
        return lookAt;
    }

    public Vertex3D getUp() {
        return up;
    }
}
