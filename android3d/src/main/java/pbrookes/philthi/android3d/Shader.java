package pbrookes.philthi.android3d;

import android.opengl.GLES20;
import android.util.Log;

import java.util.LinkedList;

public class Shader {
    private String script;
    private int type;
    private int handle;
    private LinkedList<String> attributes;

    public Shader(String script, int type) throws RuntimeException {
        this.script = script;
        this.type = type;
        this.attributes = new LinkedList<String>();
        this.compile();
    }


    public int getHandle() {
        return this.handle;
    }

    public void addAttribute(String attr) {
        attributes.add(attr);
    }

    public LinkedList<String> getAttributes() {
        return attributes;
    }

    public String getLogs() {
        return GLES20.glGetShaderInfoLog(handle);
    }

    public void compile() throws RuntimeException {
        // Load in the vertex shader.
        this.handle = GLES20.glCreateShader(this.type);

        if (this.handle == 0)
        {
            Log.e("Android-3d", "error creating shader type: " + this.type);
            throw new RuntimeException("Error creating shader!");
        }

        if (this.handle != 0)
        {
            // Pass in the shader source.
            GLES20.glShaderSource(this.handle, this.script);

            // Compile the shader.
            GLES20.glCompileShader(this.handle);

            // Get the compilation status.
            final int[] compileStatus = new int[1];
            GLES20.glGetShaderiv(this.handle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

            // If the compilation failed, delete the shader.
            if (compileStatus[0] == 0)
            {
                Log.e("Android-3d", "error compiling shader: " + GLES20.glGetShaderInfoLog(this.handle));
                GLES20.glDeleteShader(this.handle);
                this.handle = 0;
                throw new RuntimeException("Error compiling shader.");
            }
        }
    }
}
