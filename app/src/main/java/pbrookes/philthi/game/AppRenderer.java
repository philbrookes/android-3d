package pbrookes.philthi.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

import java.io.InputStream;
import java.util.Date;

import pbrookes.philthi.android3d.*;

/**
 * This class implements our custom renderer. Note that the GL10 parameter
 * passed in is unused for OpenGL ES 2.0 renderers -- the static class GLES20 is
 * used instead.
 */
public class AppRenderer implements Renderer {
    private pbrookes.philthi.android3d.Renderer renderer;
    private Scene scene;
    private HUD hud;
    private Context context;
    private Cube cube;
    private Cube cube2;
    private HUDItem item;

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
            renderer.setClearColor(new Color(0.6f, 0.6f, 0.6f, 1.0f));
        } catch(RuntimeException e) {
            Log.e("Android-3d", "failed to initialise renderer: " + e.getMessage());
        }

        scene = new Scene();
        scene.setVertexShader(new Shader(getRawString(R.raw.vertex), GLES20.GL_VERTEX_SHADER));
        scene.setFragmentShader(new Shader(getRawString(R.raw.fragment), GLES20.GL_FRAGMENT_SHADER));
        scene.getCamera().getPos().setZ(5);
        cube = new Cube();
        cube.getPos().clone(new Vertex3D(0, 2, 0));
        scene.addItem(cube);
        cube2 = new Cube();
        cube2.getPos().clone(new Vertex3D(0, -2, 0));
        scene.addItem(cube2);

        hud = new HUD();
        hud.setVertexShader(new Shader(getRawString(R.raw.default_texture_vertex_shader), GLES20.GL_VERTEX_SHADER));
        hud.setFragmentShader(new Shader(getRawString(R.raw.default_texture_fragment_shader), GLES20.GL_FRAGMENT_SHADER));

        Bitmap arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow);
        item = new HUDItem(new Vertex2D(3f, 1f), arrow);
        item.setScale(new Vertex2D(0.1f, 0.1f));
        hud.addItem(item);

    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        hud.setAspectRatio(new Float(width / height));
        renderer.setBounds(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        item.setRotation((item.getRotation() + 1) % 360);
        renderer.reset();
        renderer.render(scene);
        renderer.renderOrthof(hud);
    }

    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}