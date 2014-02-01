package engine;

import android.opengl.Matrix;

abstract public class RenderItem {
	private float x, y, z, yaw, pitch, roll;
	
	public RenderItem() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.yaw = 0;
		this.pitch = 0;
		this.roll = 0;
	}
	
	abstract public void processLogic();
	
	public void render(Renderer renderer, float[] matrix){
		float[] pos = this.getPosition();
	     
        Matrix.translateM(matrix, 0, pos[0], pos[1], pos[2]);
        
        Matrix.rotateM(matrix, 0, this.getPitch(), 1.0f, 0.0f, 0.0f);
        Matrix.rotateM(matrix, 0, this.getYaw(), 0.0f, 1.0f, 0.0f);
        Matrix.rotateM(matrix, 0, this.getRoll(), 0.0f, 0.0f, 1.0f);
        
	}
	
	
	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setPosition(float[] position) {
		if(position.length != 3) {
			throw new RuntimeException("invalid position specified");
		}
		
		this.x = position[0];
		this.y = position[1];
		this.z = position[2];
	}
	
	
	public float[] getPosition() {
		return new float[] { this.x, this.y, this.z } ;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public void setRoll(float roll) {
		this.roll = roll;
	}
	
	public float getRoll() {
		return this.roll;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public float getPitch() {
		return this.pitch;
	}
}
