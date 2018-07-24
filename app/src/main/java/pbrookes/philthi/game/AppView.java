package pbrookes.philthi.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class AppView extends GLSurfaceView {
    private AppRenderer renderer;

    public AppView(Context context)
    {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return super.onTouchEvent(event);
    }

    // Hides superclass method.
    public void setRenderer(AppRenderer renderer)
    {
        this.renderer = renderer;
        super.setRenderer(renderer);
    }
}
