package engine;

public class Camera {
	private float x, y, z;
	private float lookatX, lookatY, lookatZ;
	private float upX, upY, upZ;
	
	public Camera() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.lookatX = 0;
		this.lookatY = 0;
		this.lookatZ = 0;
		this.upX = 0;
		this.upY = 1;
		this.upZ = 0;
	}
	
	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getPosition() {
		return new float[] {this.x, this.y, this.z};
	}
	
	public void setLookAt(float x, float y, float z) {
		this.lookatX = x;
		this.lookatY = y;
		this.lookatZ = z;
	}
	
	public float[] getLookAt() {
		return new float[] {this.lookatX, this.lookatY, this.lookatZ};
	}
	
	public void setUp(float x, float y, float z) {
		this.upX = x;
		this.upY = y;
		this.upZ = z;
	}
	
	public float[] getUp() {
		return new float[] {this.upX, this.upY, this.upZ};
	}
}
