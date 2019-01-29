package pbrookes.philthi.game;

import android.content.Context;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLES20;

import java.util.Iterator;

import pbrookes.philthi.android3d.*;
import pbrookes.philthi.game.util.Level;
import pbrookes.philthi.game.util.LevelLoader;

/**
 * This class implements our custom renderer. Note that the GL10 parameter
 * passed in is unused for OpenGL ES 2.0 renderers -- the static class GLES20 is
 * used instead.
 */
public class LevelRenderer implements Renderer {
    private pbrookes.philthi.android3d.Renderer renderer;
    private Context context;
    private Level level;

    private boolean cont;
    private String levelName;

    public LevelRenderer(LevelActivity ctx){
        context = ctx;
        this.cont = ctx.cont;
        this.levelName = ctx.levelName;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {

        try {
            LevelLoader levelLoader = new LevelLoader(context);
            level = levelLoader.load(levelName);
        } catch(Exception e) {
            Log.e("Android-3d", "failed to load level: " + e.getMessage());
        }
        try {
            renderer = new pbrookes.philthi.android3d.Renderer();
            renderer.setClearColor(level.getClearColor());
        } catch(RuntimeException e) {
            Log.e("Android-3d", "failed to initialise renderer: " + e.getMessage());
        }
    }

    @Override
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        for(HUD hud: level.getHuds().values()) {
            hud.setBounds(0, 0, width, height);
        }
        renderer.setBounds(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        renderer.reset();
        for (Scene scene : level.getScenes().values()) {
            renderer.render(scene);
        }

        for (HUD hud: level.getHuds().values()) {
            renderer.renderOrthof(hud);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        for(HUD hud: level.getHuds().values()) {
            if (hud.onTouchEvent(event)) {
                return true;
            }
        }
        for(Scene scene: level.getScenes().values()) {
            if (renderer.onTouchEvent(event, scene, false)) {
                return true;
            }
        }

        return false;
    }
}