package pbrookes.philthi.android3d;

import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

public class HUD {
    public ArrayList<HUDRenderable> items;
    private Shader fragment, vertex;
    private float aspectRatio = 1.0f;
    private int viewHeight, viewWidth;

    public boolean onTouchEvent(MotionEvent event) {
        Log.d("android-3d", "touch event: " + event);
        //invert y-axis for HUD
        Vertex2D point = new Vertex2D(event.getX(), this.viewHeight-event.getY());
        for(HUDRenderable item: items) {
            if(item.isTouched(point)){
                item.onTouch();
                return true;
            }
        }
        return false;
    }

    public HUD() {
        items = new ArrayList<HUDRenderable>();
    }

    public void addItem(HUDRenderable item) {
        this.items.add(item);
    }
    public ArrayList<HUDRenderable> getItems() {
        return this.items;
    }
    public void setBounds(int x1, int y1, int x2, int y2) {
        this.viewWidth = x2 - x1;
        this.viewHeight = y2 - y1;
        this.setAspectRatio(new Float(this.viewWidth /this.viewHeight));
    }


    public void setAspectRatio(float ar) {
        aspectRatio = ar;
        for(HUDRenderable item: items) {
            item.setAspectRatio(aspectRatio);
        }
    }
    public void setVertexShader(Shader vs) {
        vertex = vs;
    }

    public void setFragmentShader(Shader fs) {
        fragment = fs;
    }
    public Shader getFragmentShader() {
        return fragment;
    }

    public Shader getVertexShader() {
        return vertex;
    }
    public void removeItem(HUDRenderable item) {
        this.items.remove(item);
    }

    public void clearItems(){
        this.items.clear();
    }

    public boolean hasItem(HUDRenderable item) {
        return this.items.contains(item);
    }
}
