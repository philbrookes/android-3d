package pbrookes.philthi.game.util;

import java.util.HashMap;

import pbrookes.philthi.android3d.Color;
import pbrookes.philthi.android3d.HUD;
import pbrookes.philthi.android3d.Scene;

public class Level {
    private String name;
    private Color clearColor;
    private HashMap<String, Scene> scenes;
    private HashMap<String, HUD> huds;

    public Level(){
        clearColor = new Color(0.1f, 0.1f, 0.1f, 1.0f);
        scenes = new HashMap<String, Scene>();
        huds = new HashMap<String, HUD>();
    }

    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
    }

    public Scene getScene(String name) {
        return scenes.get(name);
    }

    public HashMap<String, Scene> getScenes(){
        return scenes;
    }

    public void addHUD(String name, HUD hud) {
        huds.put(name, hud);
    }

    public HUD getHUD(String name){
        return huds.get(name);
    }

    public HashMap<String, HUD> getHuds() {
        return huds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClearColor(float r, float g, float b, float a) {
        this.clearColor = new Color(r, g, b, a);
    }

    public void setClearColor(Color color) {
        this.clearColor = color;
    }

    public Color getClearColor() {
        return clearColor;
    }
}

