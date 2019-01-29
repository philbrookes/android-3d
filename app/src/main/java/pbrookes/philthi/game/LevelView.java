package pbrookes.philthi.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class LevelView extends GLSurfaceView {
    private LevelRenderer renderer;

    public LevelView(Context context)
    {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        if (event != null)
        {
            if (event.getAction() == MotionEvent.ACTION_DOWN)
            {
                if (renderer != null)
                {
                    renderer.onTouchEvent(event);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    // Hides superclass method.
    public void setRenderer(LevelRenderer renderer)
    {
        this.renderer = renderer;
        super.setRenderer(renderer);
    }
}
