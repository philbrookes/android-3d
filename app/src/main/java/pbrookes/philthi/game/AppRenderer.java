package pbrookes.philthi.game;

import android.content.Context;
import android.content.res.Resources;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

import java.io.InputStream;

import pbrookes.philthi.android3d.*;

/**
 * This class implements our custom renderer. Note that the GL10 parameter
 * passed in is unused for OpenGL ES 2.0 renderers -- the static class GLES20 is
 * used instead.
 */
public class AppRenderer implements Renderer {
    private pbrookes.philthi.android3d.Renderer renderer;
    private Scene scene;
    private Scene blackScene;
    private Context context;

    public AppRenderer(Context ctx){
        context = ctx;
    }

    private String getRawString(int resId){
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(resId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch(Exception e){
            // TODO: This is gross
            return "";
        }
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        try {
            renderer = new pbrookes.philthi.android3d.Renderer();
        } catch(RuntimeException e) {
            Log.e("Android-3d", "failed to initialise renderer: " + e.getMessage());
        }

        scene = new Scene();
        scene.setVertexShader(new Shader(getRawString(R.raw.vertex), GLES20.GL_VERTEX_SHADER));
        scene.setFragmentShader(new Shader(getRawString(R.raw.fragment), GLES20.GL_FRAGMENT_SHADER));
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        renderer.setBounds(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        renderer.reset();
        renderer.render(scene);
        renderer.render(blackScene);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}