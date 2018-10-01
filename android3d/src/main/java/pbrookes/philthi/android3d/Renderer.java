package pbrookes.philthi.android3d;

import android.opengl.GLES20;

import android.opengl.Matrix;

public class Renderer {
    private Color clearColor = new Color(1f, 1f, 1f, 1f);
    private int glHandle;
    private Shader fragment;
    private Shader vertex;

    public int MVPMatrixHandle = -1, positionHandle = -1, colorHandle = -1;

	private float[] projectionMatrix = new float[16];
	private float[] modelMatrix = new float[16];
	private float[] viewMatrix = new float[16];
    private float[] mVPMatrix = new float[16];

    public Renderer() {
        initGL();
    }

    public void reset() {
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
    }

    public void render(Scene scene) {
        this.setVertexShader(scene.getVertexShader());
        this.setFragmentShader(scene.getFragmentShader());
        this.updateProgram();

        this.applyCamera(scene.getCamera());

        for(Light light : scene.getLights()) {
            this.applyLight(light);
        }

        for(RenderItem item : scene.getItems()) {
            this.applyItem(item);
        }
    }

    public void renderOrthof(HUD hud) {
        this.setVertexShader(hud.getVertexShader());
        this.setFragmentShader(hud.getFragmentShader());
        this.updateProgram();

        int maPositionHandle = GLES20.glGetAttribLocation(glHandle, "aPosition");
        int maTextureHandle = GLES20.glGetAttribLocation(glHandle, "aTextureCoord");
        int muMVPMatrixHandle = GLES20.glGetUniformLocation(glHandle, "uMVPMatrix");

        // Setup orthogonal mode
        float[] mProjMatrix = new float[16];
        Matrix.orthoM(mProjMatrix, 0, 0, 1, 0, 1, -64.0f, 64.0f);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mProjMatrix, 0);

        // Change depth func so our HUD is always rendered atop
        GLES20.glDepthFunc(GLES20.GL_ALWAYS);
        // Disable depth writes
        GLES20.glDepthMask(false);

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glEnable(GLES20.GL_BLEND);

        // Render fullscreen quad
        for(HUDTextItem item: hud.getItems()){
            item.render(this, maPositionHandle, maTextureHandle);
        }
        // Restore depth func an depth write
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
        GLES20.glDepthMask(true);
    }

    private void applyCamera(Camera cam) {
        float[] pos = cam.getPos().getXYZ();
        float[] look = cam.getLookAt().getXYZ();
        float[] up = cam.getUp().getXYZ();

        //set the camera position, focus and rotation
        Matrix.setLookAtM(
                viewMatrix, 0,
                pos[0], pos[1], pos[2],
                look[0], look[1], look[2],
                up[0], up[1], up[2]
        );
    }

    private void applyLight(Light light) {

    }

    private void applyItem(RenderItem item) {
        //let the item run any code it needs to
        item.processLogic();

        //reset the model matrix
        Matrix.setIdentityM(modelMatrix, 0);

        //let the item draw push it's data into GLES20, and apply matrix modification
        item.render(this, modelMatrix);

        //apply the matrix modifications to the MVP matrix
        Matrix.multiplyMM(mVPMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(mVPMatrix, 0, projectionMatrix, 0, mVPMatrix, 0);

        //give the MVP matrix to GLES20
        GLES20.glUniformMatrix4fv(MVPMatrixHandle, 1, false, mVPMatrix, 0);
        GLES20.glDrawArrays(item.getRenderMode(), 0, item.getNumVertices());
    }

    public void initGL() {
        GLES20.glClearColor(clearColor.red(), clearColor.green(), clearColor.blue(), clearColor.alpha());
    }

    public void setClearColor(Color newClear) {
        clearColor = newClear;
        initGL();
    }

    public void setBounds(int x, int y, int width, int height) {
        GLES20.glViewport(x, y, width, height);

        final float ratio = (float) width / height;
        final float left = -ratio;
        final float right = ratio;
        final float bottom = -1.0f;
        final float top = 1.0f;
        final float near = 1.0f;
        final float far = 10.0f;

        Matrix.frustumM(projectionMatrix, 0, left, right, bottom, top, near, far);
    }

    public void setFragmentShader(Shader in) throws RuntimeException {
        fragment = in;
    }

    public void setVertexShader(Shader in) throws RuntimeException {
        vertex = in;
    }

    public void updateProgram() throws RuntimeException {
        glHandle = GLES20.glCreateProgram();
        if (glHandle != 0){
            GLES20.glAttachShader(glHandle, vertex.getHandle());
            GLES20.glAttachShader(glHandle, fragment.getHandle());
            // Still hard-coded names for now

            GLES20.glBindAttribLocation(glHandle, 0, "a_Position");
            GLES20.glBindAttribLocation(glHandle, 1, "a_Color");
            //GLES20.glBindAttribLocation(glHandle, 2, "a_Normal");
            //GLES20.glBindAttribLocation(glHandle, 3, "a_TexCoord");

            // Link the shaders together into a program.
            GLES20.glLinkProgram(glHandle);

            // Get the link status.
            final int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(glHandle, GLES20.GL_LINK_STATUS, linkStatus, 0);

            // If the link failed, delete the program.
            if (linkStatus[0] == 0) {
                GLES20.glDeleteProgram(glHandle);
                glHandle = 0;
            }
        }

        if (glHandle == 0) {
            throw new RuntimeException("Error updating program.");
        }

        // Set program handles. These will later be used to pass in values to the program.
        // Still hard-coded names for now
        MVPMatrixHandle = GLES20.glGetUniformLocation(glHandle, "u_MVPMatrix");
        positionHandle = GLES20.glGetAttribLocation(glHandle, "a_Position");
        colorHandle = GLES20.glGetAttribLocation(glHandle, "a_Color");
        //normalHandle = GLES20.glGetAttribLocation(glHandle, "a_Normal");
        //TextureCoordinateHandle = GLES20.glGetAttribLocation(glHandle, "a_TexCoord");

        // Tell OpenGL to use this program when rendering.
        GLES20.glUseProgram(glHandle);
    }

}
