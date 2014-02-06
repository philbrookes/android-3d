package com.example.spacemaze;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import Renderables.Triangle;
import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import engine.Camera;
import engine.Engine;
import engine.ObjParser;
import engine.ObjRenderItem;
import engine.Scene;

/**
 * This class implements our custom renderer. Note that the GL10 parameter
 * passed in is unused for OpenGL ES 2.0 renderers -- the static class GLES20 is
 * used instead.
 */
public class GameRenderer implements Renderer {
	private Engine engine;
	private Scene scene;
	private Context context;

	public GameRenderer(Context context) {
		this.context = context;
		engine = new Engine();
		scene = new Scene();

		engine.setShaderVS(
			"uniform mat4 u_MVPMatrix;        \n"
			+ "uniform mat4 u_MVMatrix;       \n"
			
			+ "attribute vec4 a_Position;     \n"
			+ "attribute vec4 a_Color;        \n"
			+ "attribute vec3 a_Normal;		  \n"
			
			+ "varying vec4 v_Color;          \n"

			+ "void main()                    \n"
			+ "{                              \n"
			+ "   vec3 modelViewNormal = vec3(u_MVMatrix * vec4(a_Normal, 0.0));     \n"
			+ "   v_Color = a_Color;                                                 \n"
			+ "   gl_Position = u_MVPMatrix * a_Position;                            \n"
			+ "}                                                                     \n"
		);

		engine.setShaderFS(
			"precision mediump float;         \n"
			+ "varying vec4 v_Color;          \n"
			+ "void main()                    \n"
			+ "{                              \n"
			+ "   gl_FragColor = v_Color;     \n"
			+ "}                              \n"
		);
	}

	@Override
	public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
		engine.setClearColor(new float[] { 0.5f, 0.5f, 0.5f, 0.5f });
		engine.init();
		Camera cam = new Camera();
		cam.setPosition(0, 0, -8);
		cam.setLookAt(0, 0, 0);
		scene.setCamera(cam);

		ObjParser parsedBlob = new ObjParser(context);

		try {
			parsedBlob.parse(R.raw.blob_obj);
		} catch (IOException e) {

		}

		ObjRenderItem blob = new ObjRenderItem(parsedBlob);
		blob.setPosition(0, 0, 0);
		scene.addItem(blob);
	}

	@Override
	public void onSurfaceChanged(GL10 glUnused, int width, int height) {
		engine.getRenderer().setBounds(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 glUnused) {
		this.engine.processLogic(this.scene);
		this.engine.render(this.scene);
	}
}