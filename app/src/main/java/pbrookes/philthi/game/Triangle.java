package pbrookes.philthi.game;

import android.opengl.GLES20;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import pbrookes.philthi.android3d.RenderItem;
import pbrookes.philthi.android3d.Renderer;

public class Triangle extends RenderItem {
    private float[] vertices = {
            //top
            0.0f, 0.5f, 0.0f,

            //bottom-right
            0.5f, -0.5f, 0.0f,

            //bottom-left
            -0.5f, -0.5f, 0.0f
    };

    private float[] colors = {
            //top
            1.0f, 0.0f, 0.0f, 0.5f,

            //bottom-right
            0.0f, 1.0f, 0.0f, 0.5f,

            //bottom-left
            0.0f, 0.0f, 1.0f, 0.5f
    };

    private FloatBuffer verticesBuffer, colorsBuffer, normalsBuffer;


    public Triangle() {
        super();
        verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        verticesBuffer.put(vertices).position(0);

        colorsBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorsBuffer.put(colors).position(0);
    }

    public void processLogic(float timePassed) {
        long time = SystemClock.uptimeMillis() % 10000L;
        float angle = (360.0f / 10000.0f) * ((int) time);
        this.rot.setYaw(angle);
        this.rot.setRoll(angle*2);
    }
    public void onTouch(MotionEvent event){
        Log.d("ANDROID-3D", "Triangle touched!");
    };

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

    @Override
    public int getNumVertices() {
        return this.vertices.length / 3;
    }
}
