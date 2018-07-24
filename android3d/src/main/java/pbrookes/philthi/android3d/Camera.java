package pbrookes.philthi.android3d;

public class Camera {
    protected Position pos;
    protected Position lookAt;
    protected Position up;

    public Camera() {
        pos = new Position();
        lookAt = new Position();
        up = new Position();
        up.setY(1.0f);
    }

    public Position getPos() {
        return pos;
    }

    public Position getLookAt() {
        return lookAt;
    }

    public Position getUp() {
        return up;
    }
}
