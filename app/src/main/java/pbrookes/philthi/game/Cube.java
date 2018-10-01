package pbrookes.philthi.game;

import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import pbrookes.philthi.android3d.Color;
import pbrookes.philthi.android3d.RenderItem;
import pbrookes.philthi.android3d.Renderer;

public class Cube extends RenderItem {
    private float[] vertices = {
            -1.0f,-1.0f,-1.0f, // triangle 1 : begin
            -1.0f,-1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f, // triangle 1 : end
            1.0f, 1.0f,-1.0f, // triangle 2 : begin
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f, // triangle 2 : end
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            -1.0f,-1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f,-1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f,-1.0f,
            1.0f,-1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f,-1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f,-1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f,-1.0f, 1.0f
    };

    private float[] colors = {
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
    };

    private Color frontColor = new Color(1.0f, 0.0f, 0.0f, 1.0f);

    private Color backColor = new Color(0.0f, 1.0f, 0.0f, 1.0f);

    private FloatBuffer verticesBuffer, colorsBuffer;


    public Cube() {
        super();

        verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesBuffer.put(vertices).position(0);
        refreshColorsBuffer();
    }

    private void refreshColorsBuffer() {
        colorsBuffer = ByteBuffer.allocateDirect(((vertices.length / 3) * 4) * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        for(int i=0;i<(vertices.length / 3); i++) {
            int zIndex = (i*3) + 2;
            float zVal = vertices[zIndex];
            if(zVal == 1.0f){
                colorsBuffer.put(frontColor.toArray());
            } else {
                colorsBuffer.put(backColor.toArray());
            }
        }
        colorsBuffer.position(0);
    }

    public void processLogic() {
        long time = SystemClock.uptimeMillis() % 10000L;
        float angle = (360.0f / 10000.0f) * ((int) time);
        this.rot.setYaw(angle);
        this.rot.setRoll(angle*2);
    }

    @Override
    public void render(Renderer renderer, float[] modelMatrix) {
        super.render(renderer, modelMatrix);

        verticesBuffer.position(0);
        GLES20.glVertexAttribPointer(renderer.positionHandle, 3, GLES20.GL_FLOAT, false, 0, verticesBuffer);
        GLES20.glEnableVertexAttribArray(renderer.positionHandle);

        colorsBuffer.position(0);
        GLES20.glVertexAttribPointer(renderer.colorHandle, 4, GLES20.GL_FLOAT, false, 0, colorsBuffer);
        GLES20.glEnableVertexAttribArray(renderer.colorHandle);
    }

    public void setFrontColor(Color color){
        this.frontColor = color;
        refreshColorsBuffer();
    }

    public void setBackColor(Color backColor) {
        this.backColor = backColor;
        refreshColorsBuffer();
    }

    @Override
    public int getNumVertices() {
        return this.vertices.length / 3;
    }
}
