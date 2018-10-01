package pbrookes.philthi.android3d;

import java.util.ArrayList;

public class HUD {
    public ArrayList<HUDRenderable> items;
    private Shader fragment, vertex;
    private float aspectRatio = 1.0f;

    public HUD() {
        items = new ArrayList<HUDRenderable>();
    }

    public void addItem(HUDRenderable item) {
        this.items.add(item);
    }
    public ArrayList<HUDRenderable> getItems() {
        return this.items;
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
