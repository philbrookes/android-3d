package pbrookes.philthi.android3d;

import java.nio.FloatBuffer;

public interface HUDRenderable {
    public void render(Renderer renderer, int maPositionHandle, int maTextureHandle, float[] modelMatrix);
    public void setAspectRatio(float ar);
    public void setScale(Vertex2D scale);
    public Vertex2D getScale();
    public float getRotation();
    public void setRotation(float rot);
    public FloatBuffer getVertexBuffer();
    public boolean isTouched(Vertex2D point);
    public void onTouch();
    public void processLogic();
}
