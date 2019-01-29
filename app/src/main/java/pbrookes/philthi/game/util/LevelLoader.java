package pbrookes.philthi.game.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.util.Log;

import org.json.*;

import java.io.InputStream;

import pbrookes.philthi.android3d.Camera;
import pbrookes.philthi.android3d.HUD;
import pbrookes.philthi.android3d.HUDItem;
import pbrookes.philthi.android3d.RenderItem;
import pbrookes.philthi.android3d.Scene;
import pbrookes.philthi.android3d.Shader;
import pbrookes.philthi.android3d.Vertex2D;
import pbrookes.philthi.game.Cube;
import pbrookes.philthi.game.Triangle;


public class LevelLoader {
    private Context context;
    public LevelLoader(Context context){
        this.context = context;
    }

    private String getRawString(int resId){
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(resId);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch(Exception e){
            // TODO: This is gross
            return "";
        }
    }

    public Level load(String levelName) throws Exception {
        int resourceId = context.getResources().getIdentifier("pbrookes.philthi.game:raw/" + levelName, null, null);
        if(resourceId <= 0) {
            throw new Exception("could not find level definition: " + levelName);
        }

        String json = getRawString(resourceId);
        Level level = new Level();

        JSONObject obj = new JSONObject(json);
        level.setName(obj.getString("name"));
        level.setClearColor(
                ((float) obj.getJSONObject("clear_color").getDouble("r")),
                ((float) obj.getJSONObject("clear_color").getDouble("g")),
                ((float) obj.getJSONObject("clear_color").getDouble("b")),
                ((float) obj.getJSONObject("clear_color").getDouble("a"))
        );

        JSONArray scenes = obj.getJSONArray("scenes");
        for (int i = 0; i < scenes.length(); i++)
        {
            JSONObject sceneObj = scenes.getJSONObject(i);
            level.addScene(sceneObj.getString("name"), loadScene(sceneObj));
        }

        JSONArray huds = obj.getJSONArray("huds");
        for (int i = 0; i < scenes.length(); i++)
        {
            JSONObject hudObj = huds.getJSONObject(i);
            level.addHUD(hudObj.getString("name"), loadHUD(hudObj));
        }

        return level;
    }

    private Scene loadScene(JSONObject sceneObj) throws org.json.JSONException {
        Scene scene = new Scene();
        JSONObject camObj = sceneObj.getJSONObject("camera");
        scene.setCamera(loadCamera(camObj));
        scene.setVertexShader(loadShader(sceneObj.getJSONObject("shaders").getString("vertex"), GLES20.GL_VERTEX_SHADER));
        scene.setFragmentShader(loadShader(sceneObj.getJSONObject("shaders").getString("fragment"), GLES20.GL_FRAGMENT_SHADER));
        JSONArray objects = sceneObj.getJSONArray("objects");
        for(int i = 0; i<objects.length();i++){
            JSONObject renderObj = objects.getJSONObject(i);
            scene.addItem(loadRenderable(renderObj));
        }
        return scene;
    }

    private RenderItem loadRenderable(JSONObject renderObj) throws org.json.JSONException {
        RenderItem item;
        Log.i("Android-3d", "rendering object type: " + renderObj.getString("type"));
        switch(renderObj.getString("type")){
            case "cube":
                item = new Cube();
                break;
            case "triangle":
                item = new Triangle();
                break;
            default:
                item = new Cube();
        }

        JSONObject posObj = renderObj.getJSONObject("position");
        item.getPos().setXYZ(
                ((float) posObj.getDouble("x")),
                ((float) posObj.getDouble("y")),
                ((float) posObj.getDouble("z"))
        );
        JSONObject rotObj = renderObj.getJSONObject("rotation");
        item.getRotation().setYaw(((float) rotObj.getDouble("yaw")));
        item.getRotation().setPitch(((float) rotObj.getDouble("pitch")));
        item.getRotation().setRoll(((float) rotObj.getDouble("roll")));
        return item;
    }

    private Camera loadCamera(JSONObject camObj) throws org.json.JSONException{
        Camera cam = new Camera();
        cam.getPos().setXYZ(
                ((float) camObj.getJSONObject("position").getDouble("x")),
                ((float) camObj.getJSONObject("position").getDouble("y")),
                ((float) camObj.getJSONObject("position").getDouble("z"))
        );
        cam.getLookAt().setXYZ(
                ((float) camObj.getJSONObject("look_at").getDouble("x")),
                ((float) camObj.getJSONObject("look_at").getDouble("y")),
                ((float) camObj.getJSONObject("look_at").getDouble("z"))
        );
        cam.getUp().setXYZ(
                ((float) camObj.getJSONObject("up").getDouble("x")),
                ((float) camObj.getJSONObject("up").getDouble("y")),
                ((float) camObj.getJSONObject("up").getDouble("z"))
        );
        return cam;
    }

    private Shader loadShader(String name, int type) {
        int resourceId;
        if(type == GLES20.GL_FRAGMENT_SHADER) {
            resourceId = context.getResources().getIdentifier("pbrookes.philthi.game:raw/fragment_" + name, null, null);
        } else {
            resourceId = context.getResources().getIdentifier("pbrookes.philthi.game:raw/vertex_" + name, null, null);
        }
        String source = getRawString(resourceId);
        Shader shader = new Shader(source, type);
        return shader;
    }

    private HUD loadHUD(JSONObject hudObj) throws org.json.JSONException {
        HUD hud = new HUD();

        hud.setVertexShader(loadShader(hudObj.getJSONObject("shaders").getString("vertex"), GLES20.GL_VERTEX_SHADER));
        hud.setFragmentShader(loadShader(hudObj.getJSONObject("shaders").getString("fragment"), GLES20.GL_FRAGMENT_SHADER));

        JSONArray objects = hudObj.getJSONArray("objects");
        for (int i = 0; i < objects.length(); i++)
        {
            JSONObject hudItemObj = objects.getJSONObject(i);
            hud.addItem(loadHudItem(hudItemObj));
        }
        return hud;
    }

    private HUDItem loadHudItem(JSONObject hudItemObj) throws org.json.JSONException {
        Vertex2D pos = new Vertex2D(
                ((float) hudItemObj.getJSONObject("position").getDouble("x")),
                ((float) hudItemObj.getJSONObject("position").getDouble("y"))
        );
        Vertex2D scale = new Vertex2D(
                ((float) hudItemObj.getJSONObject("scale").getDouble("x")),
                ((float) hudItemObj.getJSONObject("scale").getDouble("y"))
        );
        int resourceId = context.getResources().getIdentifier("pbrookes.philthi.game:/drawable/" + hudItemObj.getString("texture"), null, null);
        HUDItem item = new HUDItem(pos, BitmapFactory.decodeResource(context.getResources(), resourceId));
        item.setScale(scale);
        return item;
    }
}
