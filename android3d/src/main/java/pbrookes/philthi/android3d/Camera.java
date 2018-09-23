package pbrookes.philthi.android3d;

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
