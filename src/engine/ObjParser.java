package engine;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class ObjParser extends Parser {

	private ArrayList<Float[]> verticePoints;
	public ObjParser(Context context) {
		super(context);
		this.verticePoints = new ArrayList<Float[]>();
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
	    	if(cmd == "o"){
	    		this.addObjId(chunks[1]);
	    	} else if (cmd == "v") {
	    		Float[] vPoint = {
    				Float.parseFloat(chunks[1]),
    				Float.parseFloat(chunks[2]),
    				Float.parseFloat(chunks[3])
				};
	    		this.verticePoints.add(vPoint);
	    		
	    	} else if (cmd == "f") {
	    		
	    	} else {
	    		continue;
	    	}
		}
	}
}
