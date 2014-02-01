package engine;

public class Light {
	private float x, y, z;
	private float[] color;
	
	public Light() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
		
		this.color = new float[] {0.8f, 0.8f, 0.8f};
	}
	
	public void setPosition(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public float[] getPosition() {
		return new float[] {this.x, this.y, this.z};
	}
	
	public void setColor(float[] newColor){
		if(newColor.length > 3) {
			throw new RuntimeException("Invalid color specified");
		}
		this.color = newColor;
	}
	
	public float[] getColor() {
		return this.color;
	}
	 
}
