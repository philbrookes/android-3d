package engine;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import android.opengl.GLES20;
import android.os.SystemClock;

public class ObjRenderItem extends RenderItem {

	private HashMap<String, FloatBuffer> verticesBuffer, texBuffer, normalsBuffer, colorsBuffer;
	private ObjParser parsedObject;
	
	public ObjRenderItem(ObjParser parsedObject) {
		super();
		
		this.verticesBuffer = new HashMap<String, FloatBuffer>();
		this.texBuffer = new HashMap<String, FloatBuffer>();
		this.normalsBuffer = new HashMap<String, FloatBuffer>();
		this.colorsBuffer = new HashMap<String, FloatBuffer>();
		
		this.parsedObject = parsedObject;
		
		ArrayList<String> objIds = parsedObject.getObjectIds();
		
		for(String objId : objIds) {
			float[] verts = parsedObject.getObjectVertices(objId);
			FloatBuffer vertBuffer;
			vertBuffer = ByteBuffer.allocateDirect(verts.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			vertBuffer.put(verts).position(0);
			
			verticesBuffer.put(objId, vertBuffer);

			float[] colors = new float[verts.length];

			for(int i=0; i < colors.length; i++){
				colors[i] = 0.9f;
			}
			FloatBuffer colBuffer;
			colBuffer = ByteBuffer.allocateDirect(colors.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			colBuffer.put(colors).position(0);

			colorsBuffer.put(objId, colBuffer);

			float[] texts = parsedObject.getObjectTextures(objId);
			FloatBuffer textsBuffer;
			textsBuffer = ByteBuffer.allocateDirect(texts.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			textsBuffer.put(texts).position(0);
			
			texBuffer.put(objId, textsBuffer);
			
			float[] norms = parsedObject.getObjectNormals(objId);
			FloatBuffer normBuffer;
			normBuffer = ByteBuffer.allocateDirect(norms.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
			normBuffer.put(norms).position(0);
			
			normalsBuffer.put(objId, normBuffer);
		
		}
	}
	
	@Override
	public void processLogic() {
		long time = SystemClock.uptimeMillis() % 10000L;
        float angle = (360.0f / 10000.0f) * ((int) time);
    	this.setYaw(angle);
    	this.setRoll(angle*2);		
	}

	@Override
	public int getNumVertices() {
		ArrayList<String> objIds = parsedObject.getObjectIds();
		int totalNum = 0;
		for(String objId : objIds) {
			float[] verts = parsedObject.getObjectVertices(objId);
			totalNum += verts.length;
		}
		return totalNum / 3;
	}
	
	@Override
	public void render(Renderer renderer, float[] matrix) {
		// TODO Auto-generated method stub
		super.render(renderer, matrix);
		
		ArrayList<String> objIds = parsedObject.getObjectIds();
		for(String objId : objIds) {
			String[] textureNames = parsedObject.getObjectTextureFiles(objId);
			for(String textureFile : textureNames) {
				int textureData = Engine.loadTexture(textureFile);

				GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
			   	GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureData);
			    GLES20.glUniform1i(renderer.getEngine().getTextureUniformHandle(), 0);
			}
			
			FloatBuffer vertBuffer = verticesBuffer.get(objId);
			vertBuffer.position(0);
			GLES20.glVertexAttribPointer(renderer.getEngine().getPositionHandle(), 3, GLES20.GL_FLOAT, false, 0, vertBuffer);
			GLES20.glEnableVertexAttribArray(renderer.getEngine().getPositionHandle());

			FloatBuffer colBuffer = colorsBuffer.get(objId);
			colBuffer.position(0);
			GLES20.glVertexAttribPointer(renderer.getEngine().getColorHandle(), 3, GLES20.GL_FLOAT, false, 0, colBuffer);
			GLES20.glEnableVertexAttribArray(renderer.getEngine().getColorHandle());
			
			FloatBuffer norBuffer = normalsBuffer.get(objId);
			norBuffer.position(0);
			GLES20.glVertexAttribPointer(renderer.getEngine().getNormalHandle(), 3, GLES20.GL_FLOAT, false, 0, norBuffer);
			GLES20.glEnableVertexAttribArray(renderer.getEngine().getNormalHandle());
			
			FloatBuffer textBuffer = texBuffer.get(objId);
			textBuffer.position(0);
			GLES20.glVertexAttribPointer(renderer.getEngine().getTextureCoordinateHandle(), 2, GLES20.GL_FLOAT, false, 0, textBuffer);
			GLES20.glEnableVertexAttribArray(renderer.getEngine().getTextureCoordinateHandle());
			
		}
	}
	
}
