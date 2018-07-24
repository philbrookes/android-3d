package pbrookes.philthi.android3d;

import java.util.ArrayList;

public class Scene {
    private ArrayList<RenderItem> items;
    private ArrayList<Light> lights;
    private Camera camera;

    public Scene() {
        this.items = new ArrayList<RenderItem>();
        this.lights = new ArrayList<Light>();
        this.camera = new Camera();
    }

    public void addItem(RenderItem item) {
        this.items.add(item);
    }

    public ArrayList<RenderItem> getItems() {
        return this.items;
    }

    public void removeItem(RenderItem item) {
        this.items.remove(item);
    }

    public void clearItems(){
        this.items.clear();
    }

    public void addLight(Light light) {
        this.lights.add(light);
    }

    public ArrayList<Light> getLights() {
        return this.lights;
    }

    public void removeLight(Light light) {
        this.lights.remove(light);
    }

    public void clearLights(){
        this.lights.clear();
    }

    public void setCamera(Camera camera){
        this.camera = camera;
    }

    public Camera getCamera(){
        return this.camera;
    }
}
