package pbrookes.philthi.android3d;

import java.util.ArrayList;

public class HUD {
    public ArrayList<HUDTextItem> items;
    private Shader fragment, vertex;
    private float aspectRatio = 1.0f;

    public HUD() {
        items = new ArrayList<HUDTextItem>();
    }

    public void addItem(HUDTextItem item) {
        this.items.add(item);
    }
    public ArrayList<HUDTextItem> getItems() {
        return this.items;
    }
    public void setAspectRatio(float ar) {
        aspectRatio = ar;
        for(HUDTextItem item: items) {
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
    public void removeItem(HUDTextItem item) {
        this.items.remove(item);
    }

    public void clearItems(){
        this.items.clear();
    }

    public boolean hasItem(HUDTextItem item) {
        return this.items.contains(item);
    }
}
