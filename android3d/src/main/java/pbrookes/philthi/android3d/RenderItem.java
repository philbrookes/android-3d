package pbrookes.philthi.android3d;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public abstract class RenderItem {
    protected Position pos;
    protected Rotation rot;
    protected int renderMode;

    public RenderItem(){
        renderMode = GLES20.GL_TRIANGLES;
        pos = new Position();
        rot = new Rotation();
    }

    public Position getPos() {
        return pos;
    }

    public abstract void processLogic();

    public void render(Renderer renderer, float[] modelMatrix) {
        float[] pos = this.pos.getXYZ();

        Matrix.translateM(modelMatrix, 0, pos[0], pos[1], pos[2]);

        Matrix.rotateM(modelMatrix, 0, this.rot.getPitch(), 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, this.rot.getYaw(), 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(modelMatrix, 0, this.rot.getRoll(), 0.0f, 0.0f, 1.0f);
    }

    public int getNumVertices() {
        return 0;
    }

    public int getRenderMode() {
        return renderMode;
    }
}
