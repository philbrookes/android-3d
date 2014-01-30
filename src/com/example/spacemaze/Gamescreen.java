package com.example.spacemaze;

import android.os.Bundle;
import android.app.Activity;
import android.opengl.GLSurfaceView;


public class Gamescreen extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new OpenGLRenderer());
        setContentView(view);
    }

}
