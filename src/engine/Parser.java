package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import android.content.Context;

public abstract class Parser {
	private Map<String, ArrayList<Float[]>> vertices;
	private Map<String, ArrayList<Float[]>> normals;
	private Map<String, ArrayList<Float[]>> textures;
	
	private ArrayList<String> objIds;
	
	private String currObjId;
	
	protected BufferedReader file;
	
	protected Context context;
	
	public Parser(Context context) {
		this.context = context;
	}
	
	public void parse(int resId) throws IOException {
		InputStream in = context.getResources().openRawResource(resId);
		InputStreamReader is = new InputStreamReader(in);
		this.file = new BufferedReader(is);
	}
	
	protected void addVertice(Float[] vertice) {
		ArrayList<Float[]> Verts = this.vertices.get(currObjId);
		Verts.add(vertice);
	}
	protected void addNormal(Float[] normal) {
		ArrayList<Float[]> Norms = this.normals.get(currObjId);
		Norms.add(normal);
	}
	protected void addTexture(Float[] texture) {
		ArrayList<Float[]> Texts = this.textures.get(currObjId);
		Texts.add(texture);
	}
	protected void addObjId(String objId) {
		this.currObjId = objId;
		this.objIds.add(objId);
	}
	
	public ArrayList<String> getObjectIds() {
		return this.objIds;
	}
	
	public ArrayList<Float[]> getObjectVertices(String objId) {
		return this.vertices.get(objId);
	}
	public ArrayList<Float[]> getObjectNormals(String objId) {
		return this.normals.get(objId);
	}
	public ArrayList<Float[]> getObjectTextures(String objId) {
		return this.textures.get(objId);
	}
	
	
	
	
}
