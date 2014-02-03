package engine;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class ObjParser extends Parser {

	private ArrayList<Float[]> verticePoints, texturePoints, normalPoints;
	
	public ObjParser(Context context) {
		super(context);
		this.verticePoints = new ArrayList<Float[]>();
		this.texturePoints = new ArrayList<Float[]>();
		this.normalPoints = new ArrayList<Float[]>();
	}

	@Override
	public void parse(int resId) throws IOException {
		super.parse(resId);
		String line = null;
		
		    while( ( line = this.file.readLine() ) != null ) {
		    	if(line.matches("^#.*")) {
		    		continue;
		    	}
		    	String[] chunks = line.split(" ");
		    	String cmd = chunks[0].toLowerCase();
		    	if(cmd.equals("o")){
		    		this.addObjId(chunks[1]);
		    	} else if (cmd.equals("v")) {
		    		Float[] vPoint = {
	    				Float.parseFloat(chunks[1]),
	    				Float.parseFloat(chunks[2]),
	    				Float.parseFloat(chunks[3])
					};
		    		Log.d("android-3d", this.verticePoints.size() + " set to: " + String.valueOf(vPoint[0]) + ", " + String.valueOf(vPoint[1]) + ", " + String.valueOf(vPoint[2]));
		    		this.verticePoints.add(vPoint);
		    	} else if (cmd.equals("vn")) {
		    		Float[] nPoint = {
	    				Float.parseFloat(chunks[1]),
	    				Float.parseFloat(chunks[2]),
	    				Float.parseFloat(chunks[3])
					};
		    		this.normalPoints.add(nPoint);
		    	} else if (cmd.equals("vt")) {
		    		Float[] tPoint = {
	    				Float.parseFloat(chunks[1]),
	    				Float.parseFloat(chunks[2]),
	    				Float.parseFloat(chunks[3])
					};
		    		this.texturePoints.add(tPoint);
		    	} else if (cmd.equals("f")) {
		    		for(int i = 1; i < chunks.length; i++) {
		    			this.processFaceChunk(chunks[i]);
		    		}
		    		
		    	} else {
		    		continue;
		    	}
			}
	}
	
	private void processFaceChunk(String chunk) {
		String[] bits = chunk.split("/");
		switch(bits.length) {
		case 1:
			int vp = 0;
			try{
				vp = Integer.parseInt(bits[0]) - 1;
			} catch(NumberFormatException e) {
				vp = 0;
			}
			this.addVertice(this.verticePoints.get(vp));
		case 2:
			int tp;
			try{
				tp = Integer.parseInt(bits[0]) - 1;
			} catch(NumberFormatException e) {
				tp = 0;
			}
			//this.addTexture(this.texturePoints.get(tp));
		case 3:
			int np;
			try{
				np = Integer.parseInt(bits[0]) - 1;
			} catch(NumberFormatException e) {
				np = 0;
			}
			//this.addTexture(this.normalPoints.get(np));
		}
	}
}
