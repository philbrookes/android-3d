package Renderables;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;

import engine.RenderItem;
import engine.Renderer;

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
			1.0f, 0.0f, 0.0f, 1.0f,
			
			//bottom-right
			0.0f, 1.0f, 0.0f, 1.0f,
			
			//bottom-left
			0.0f, 0.0f, 1.0f, 1.0f
	};

	private FloatBuffer verticesBuffer, colorsBuffer;
	
	public Triangle() {
		super();
		verticesBuffer = ByteBuffer.allocateDirect(vertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		verticesBuffer.put(vertices).position(0);
		
		colorsBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		colorsBuffer.put(colors).position(0);
	}
	
	@Override
	public void processLogic() {
		long time = SystemClock.uptimeMillis() % 10000L;
        float angle = (360.0f / 10000.0f) * ((int) time);
    	this.setPitch(angle);
        
	}

	@Override
	public void render(Renderer renderer, float[] matrix) {
		
		super.render(renderer, matrix);
		
		verticesBuffer.position(0);
		GLES20.glVertexAttribPointer(renderer.getEngine().getPositionHandle(), 3, GLES20.GL_FLOAT, false, 0, verticesBuffer);
		GLES20.glEnableVertexAttribArray(renderer.getEngine().getPositionHandle());
		
		colorsBuffer.position(0);
		GLES20.glVertexAttribPointer(renderer.getEngine().getColorHandle(), 4, GLES20.GL_FLOAT, false, 0, colorsBuffer);
		GLES20.glEnableVertexAttribArray(renderer.getEngine().getColorHandle());
	}

}
