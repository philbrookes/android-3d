package pbrookes.philthi.android3d;

import java.util.ArrayList;

public class HUD {
    public ArrayList<HUDItem> items;

    public void addItem(HUDItem item) {
        this.items.add(item);
    }

    public ArrayList<HUDItem> getItems() {
        return this.items;
    }

    public void removeItem(HUDItem item) {
        this.items.remove(item);
    }

    public void clearItems(){
        this.items.clear();
    }

    public boolean hasItem(HUDItem item) {
        return this.items.contains(item);
    }
    public void render() {

    }
}
