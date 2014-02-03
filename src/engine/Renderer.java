package engine;

import android.opengl.GLES20;
import android.opengl.Matrix;

public class Renderer {
	private Engine engine;	
	
	public Renderer(Engine engine) {
		this.engine = engine;		
	}
	
	public Engine getEngine() {
		return engine;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		GLES20.glViewport(x, y, width, height);
		
		final float ratio = (float) width / height;
		final float left = -ratio;
		final float right = ratio;
		final float bottom = -1.0f;
		final float top = 1.0f;
		final float near = 1.0f;
		final float far = 10.0f;
		
		Matrix.frustumM(engine.getProjectionMatrix(), 0, left, right, bottom, top, near, far);
	}
	
	public void renderScene(Scene scene) {
		GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
		GLES20.glFrontFace(GLES20.GL_CCW);
		GLES20.glCullFace(GLES20.GL_BACK);
		GLES20.glEnable(GLES20.GL_CULL_FACE);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		
		this.applyCamera(scene.getCamera());
		
		for(Light light : scene.getLights()) {
			this.applyLight(light);
		}

		for(RenderItem item : scene.getItems()) {
			this.applyItem(item);
		}
	}
	
	private void applyCamera(Camera cam) {
		float[] pos = cam.getPosition();
		float[] look = cam.getLookAt();
		float[] up = cam.getUp();
		
		//set the camera position, focus and rotation
		Matrix.setLookAtM(
			engine.getViewMatrix(), 0, 
			pos[0], pos[1], pos[2], 
			look[0], look[1], look[2],
			up[0], up[1], up[2]
		);
	}
	
	private void applyLight(Light light) {
		
	}
	
	private void applyItem(RenderItem item) {
		
		//reset the model matrix
		Matrix.setIdentityM(engine.getModelMatrix(), 0);
		
		//let the item draw push it's data into GLES20, and apply matrix modification
		item.render(this, engine.getModelMatrix());
		
		//apply the matrix modifications to the MVP matrix
        Matrix.multiplyMM(engine.getMVPMatrix(), 0, engine.getViewMatrix(), 0, engine.getModelMatrix(), 0);
        Matrix.multiplyMM(engine.getMVPMatrix(), 0, engine.getProjectionMatrix(), 0, engine.getMVPMatrix(), 0);

        //give the MVP matrix to GLES20
        GLES20.glUniformMatrix4fv(engine.getMVPMatrixHandle(), 1, false, engine.getMVPMatrix(), 0);
		
        //draw the buffered triangles
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, item.getNumVertices());
	}

}

