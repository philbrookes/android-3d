package engine;

import android.opengl.GLES20;

public class Engine {
	private String shaderFS = 
			  "precision mediump float;																	\n"
			
            + "varying vec2 vTextureCoord;																\n" 
            + "varying vec3 vLightWeighting; 															\n"
            
            + "uniform sampler2D uSampler;																\n"
            
            + "void main(void) {																		\n"  
            + "   vec4 textureColor = texture2D(uSampler, vec2(vTextureCoord.s, vTextureCoord.t));		\n"
            + "   gl_FragColor = vec4(textureColor.rgb * vLightWeighting, textureColor.a);				\n"
            + "}																						\n";
	
    private String shaderVS = 
    		  "attribute vec3 aVertexPosition;															\n" 
            + "attribute vec3 aVertexNormal;															\n"
            + "attribute vec2 aTextureCoord;															\n" 
            + "uniform mat4 uMVMatrix;																	\n"
            + "uniform mat4 uPMatrix;																	\n" 
            + "uniform mat3 uNMatrix;																	\n"
            + "uniform vec3 uAmbientColor;																\n"
            + "uniform vec3 uPointLightingLocation;														\n"
            + "uniform vec3 uPointLightingColor;														\n"
            + "varying vec2 vTextureCoord;																\n"
            + "varying vec3 vLightWeighting;															\n"
            + "void main(void) {																		\n"
            + "   vec4 mvPosition = uMVMatrix * vec4(aVertexPosition, 1.0);								\n"
            + "   gl_Position = uPMatrix * mvPosition;													\n"
            + "   vTextureCoord = aTextureCoord;														\n"
            + "   vec3 lightDirection = normalize(uPointLightingLocation - mvPosition.xyz);				\n" 
            + "   vec3 transformedNormal = uNMatrix * aVertexNormal;									\n"
            + "   float directionalLightWeighting = max(dot(transformedNormal, lightDirection), 0.0);	\n"
            + "   vLightWeighting = uAmbientColor + uPointLightingColor * directionalLightWeighting;	\n"
            + "}																						\n";
    
    private float[] clearColor = {0.5f, 0.5f, 0.5f, 0.5f};

	private int vertexShader;

	private int fragmentShader;

	private int mVPMatrixHandle;

	private int positionHandle;
	
	private float[] projectionMatrix = new float[16];
	private float[] modelMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private float[] mVPMatrix = new float[16];

	private int colorHandle;

	private Renderer renderer; 
    
    public Engine() {
		// Set the background clear color to gray.
		this.setClearColor(new float[] {0.5f, 0.5f, 0.5f, 0.5f});
		this.renderer = new Renderer(this);
	}
    
    public void render(Scene scene) {
    	this.renderer.renderScene(scene);
    }
    
    
    public Renderer getRenderer() {
		return renderer;
	}
    
    public void setShaderFS(String shaderFS) {
    	//update the script and restart GL
		this.shaderFS = shaderFS;
	}
    
    public void setShaderVS(String shaderVS) {
    	//update the script and restart GL
    	this.shaderVS = shaderVS;
	}
    
    public void init() {
    	this.vertexShader = this.createVertexShader(this.shaderVS);
    	this.fragmentShader = this.createFragmentShader(this.shaderFS);
    	this.startGlProgram(this.vertexShader, this.fragmentShader);
    }
    
    public float[] getProjectionMatrix() {
		return projectionMatrix;
	}
    
    public float[] getModelMatrix() {
		return modelMatrix;
	}
    
    public float[] getViewMatrix() {
		return viewMatrix;
	}
    
    public float[] getMVPMatrix() {
		return mVPMatrix;
	}
    
    public int getMVPMatrixHandle() {
		return mVPMatrixHandle;
	}
    
    public int getPositionHandle() {
		return positionHandle;
	}
    
    public int getColorHandle() {
		return colorHandle;
	}
    
    public void processLogic(Scene scene) {
    	for(RenderItem item : scene.getItems()) {
			item.processLogic();
		}
    }
    
    private int createVertexShader(String vertexScript){
		// Load in the vertex shader.
		int vertexShaderHandle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);

		if (vertexShaderHandle != 0) 
		{
			// Pass in the shader source.
			GLES20.glShaderSource(vertexShaderHandle, vertexScript);

			// Compile the shader.
			GLES20.glCompileShader(vertexShaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(vertexShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0) 
			{				
				GLES20.glDeleteShader(vertexShaderHandle);
				vertexShaderHandle = 0;
			}
		}

		if (vertexShaderHandle == 0)
		{
			throw new RuntimeException("Error creating vertex shader.");
		}
		
		return vertexShaderHandle;
    }
    
    public int createFragmentShader(String fragmentScript) {
		// Load in the fragment shader shader.
		int fragmentShaderHandle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);

		if (fragmentShaderHandle != 0) 
		{
			// Pass in the shader source.
			GLES20.glShaderSource(fragmentShaderHandle, fragmentScript);

			// Compile the shader.
			GLES20.glCompileShader(fragmentShaderHandle);

			// Get the compilation status.
			final int[] compileStatus = new int[1];
			GLES20.glGetShaderiv(fragmentShaderHandle, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

			// If the compilation failed, delete the shader.
			if (compileStatus[0] == 0) 
			{				
				GLES20.glDeleteShader(fragmentShaderHandle);
				fragmentShaderHandle = 0;
			}
		}

		if (fragmentShaderHandle == 0)
		{
			throw new RuntimeException("Error creating fragment shader.");
		}
		
		return fragmentShaderHandle;
    }
    
    public int startGlProgram(int vertexShader, int fragmentShader) {
		// Create a program object and store the handle to it.
		int programHandle = GLES20.glCreateProgram();
		
		if (programHandle != 0) 
		{
			// Bind the vertex shader to the program.
			GLES20.glAttachShader(programHandle, vertexShader);			

			// Bind the fragment shader to the program.
			GLES20.glAttachShader(programHandle, fragmentShader);
			
			// Bind attributes
			GLES20.glBindAttribLocation(programHandle, 0, "a_Position");
			GLES20.glBindAttribLocation(programHandle, 1, "a_Color");
			
			// Link the two shaders together into a program.
			GLES20.glLinkProgram(programHandle);

			// Get the link status.
			final int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(programHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

			// If the link failed, delete the program.
			if (linkStatus[0] == 0) 
			{				
				GLES20.glDeleteProgram(programHandle);
				programHandle = 0;
			}
		}
		
		if (programHandle == 0)
		{
			throw new RuntimeException("Error creating program.");
		}
        
        // Set program handles. These will later be used to pass in values to the program.
        mVPMatrixHandle = GLES20.glGetUniformLocation(programHandle, "u_MVPMatrix");        
        positionHandle = GLES20.glGetAttribLocation(programHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(programHandle, "a_Color");        
        
        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(programHandle);      
        
        return programHandle;
    }
    
    public void setClearColor(float[] newColor){
    	if(newColor.length != 4)
    	{
    		throw new RuntimeException("invalid clear color specified");
    	}
    	
    	this.clearColor = newColor;
    	GLES20.glClearColor(this.clearColor[0], this.clearColor[1], this.clearColor[2], this.clearColor[3]);
    }
    
    public float[] getClearColor(){
    	return this.clearColor;
    }
    
    
}
