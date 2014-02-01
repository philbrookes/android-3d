package com.example.spacemaze;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Renderables.Triangle;
import android.opengl.GLSurfaceView.Renderer;

import engine.Camera;
import engine.Engine;
import engine.Scene;

/**
 * This class implements our custom renderer. Note that the GL10 parameter passed in is unused for OpenGL ES 2.0
 * renderers -- the static class GLES20 is used instead.
 */
public class GameRenderer implements Renderer 
{
	private Engine engine;
	private Scene scene;

	public GameRenderer()
	{
		engine = new Engine();
		scene = new Scene();
		
		engine.setShaderVS(
			"uniform mat4 u_MVPMatrix;        \n"		// A constant representing the combined model/view/projection matrix.
	
			+ "attribute vec4 a_Position;     \n"		// Per-vertex position information we will pass in.
			+ "attribute vec4 a_Color;        \n"		// Per-vertex color information we will pass in.			  
	
			+ "varying vec4 v_Color;          \n"		// This will be passed into the fragment shader.
	
			+ "void main()                    \n"		// The entry point for our vertex shader.
			+ "{                              \n"
			+ "   v_Color = a_Color;          \n"		// Pass the color through to the fragment shader. 
			  											// It will be interpolated across the triangle.
			+ "   gl_Position = u_MVPMatrix   \n" 	// gl_Position is a special variable used to store the final position.
			+ "               * a_Position;   \n"     // Multiply the vertex by the matrix to get the final point in 			                                            			 
			+ "}                              \n"    // normalized screen coordinates.
		);

		engine.setShaderFS(
			"precision mediump float;         \n"		// Set the default precision to medium. We don't need as high of a 
														// precision in the fragment shader.				
			+ "varying vec4 v_Color;          \n"		// This is the color from the vertex shader interpolated across the 
			  											// triangle per fragment.			  
			+ "void main()                    \n"		// The entry point for our fragment shader.
			+ "{                              \n"
			+ "   gl_FragColor = v_Color;     \n"		// Pass the color directly through the pipeline.		  
			+ "}                              \n"
		);
	}
	
	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) 
	{
		engine.init();
		Camera cam = new Camera();
		cam.setPosition(0, 0, 5);
		cam.setLookAt(0, 0, 0);
		scene.setCamera(cam);
		
		Triangle tri = new Triangle();
		tri.setPosition(-1, 0, 0);
		scene.addItem(tri);
		
		tri = new Triangle();
		tri.setPosition(1, 0, 0);
		scene.addItem(tri);
	}	
	
	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) 
	{
		engine.getRenderer().setBounds(0, 0, width, height);
	}	

	@Override
	public void onDrawFrame(GL10 glUnused) 
	{
		this.engine.processLogic(this.scene);
		this.engine.render(this.scene);
	}	
}