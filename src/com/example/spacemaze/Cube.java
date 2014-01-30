package com.example.spacemaze;

import javax.microedition.khronos.opengles.GL10;

public class Cube {
	private Square face;
	private float angle;
	
	public float x, y, z, yaw, pitch, roll;
	
	public Cube(float x, float y, float z)
	{
		this.setPos(x, y, z);
		face = new Square();
	}
	
	
	public void setPos(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRot(float yaw, float pitch, float roll)
	{
		this.yaw = yaw;
		this.pitch = pitch;
		this.roll = roll;
				
	}
	
	public void draw(GL10 gl)
	{
		gl.glTranslatef(x, y, z);
		
		gl.glRotatef(yaw, 1, 0, 0);
		gl.glRotatef(pitch, 0, 1, 0);
		gl.glRotatef(roll, 0, 0, 1);
	
		
		gl.glPushMatrix();
			
			gl.glPushMatrix();
				//front
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.8f, 0.0f, 0.0f, 1.0f);
				face.draw(gl);
			gl.glPopMatrix();
			
			gl.glPushMatrix();
				//back
				gl.glRotatef(180, 0, 1, 0);
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.0f, 0.8f, 0.0f, 1.0f);
				face.draw(gl);
			gl.glPopMatrix();
			
			
			gl.glPushMatrix();
				//top
				gl.glRotatef(90, 1, 0, 0);
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.0f, 0.0f, 0.8f, 1.0f);
				face.draw(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
				//bottom
				gl.glRotatef(270, 1, 0, 0);
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.8f, 0.8f, 0.0f, 1.0f);
				face.draw(gl);
			gl.glPopMatrix();
			

		gl.glPopMatrix();
		
		gl.glPushMatrix();
			
			gl.glPushMatrix();
			//left
				gl.glRotatef(90, 0, 1, 0);
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.8f, 0.0f, 0.8f, 1.0f);
				face.draw(gl);
			
			gl.glPopMatrix();

				
			gl.glPushMatrix();
			//left
				gl.glRotatef(270, 0, 1, 0);
				gl.glTranslatef(0, 0, 1);
				gl.glColor4f(0.0f, 0.8f, 0.8f, 1.0f);
				face.draw(gl);
			
			gl.glPopMatrix();
			
		gl.glPopMatrix();
	}
}
