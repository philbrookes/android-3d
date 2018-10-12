package pbrookes.philthi.android3d;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class HUDItem implements HUDRenderable {
    protected Vertex2D position;
    protected FloatBuffer vertexBuffer, texCoordBuffer;
    protected int[] texture;
    protected float aspectRatio = 1.0f;
    protected float rotation = 100.0f;
    protected Bitmap bitmap;
    protected Vertex2D scale = new Vertex2D(1, 1);
    protected int width, height;
    private int direction = 1;

    public HUDItem(Vertex2D pos, Bitmap bmap){
        this(pos);
        this.setBitmap(bmap);
        this.generateVertexBuffer();
    }

    protected void generateVertexBuffer(){
        // Generate buffers for vertices and texture coordinates
        float mVertices[]  = {
                -(height/2),-(width/2),0, //top-left
                (height/2),-(width/2),0, //top-right
                -(height/2),(width/2),0, //bottom-left
                (height/2),(width/2),0 //bottom-right
        };
        vertexBuffer = ByteBuffer.allocateDirect(mVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(mVertices).position(0);
    }

    public HUDItem(Vertex2D pos){
        this.position = pos;
        texture = new int[1];
        float mTexCoords[] = {0,1, 1,1, 0,0, 1,0};
        texCoordBuffer = ByteBuffer.allocateDirect(mTexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        texCoordBuffer.put(mTexCoords).position(0);

        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
    }

    public void onTouch(){
        direction = -direction;
    }

    public boolean isTouched(Vertex2D point){
        Vertex2D topLeft = new Vertex2D(
            position.getX() - ((width/2) * scale.getX()),
            position.getY() - ((height/2) * scale.getY())
        );

        Vertex2D bottomRight = new Vertex2D(
                position.getX() + ((width/2) * scale.getX()),
                position.getY() + ((height/2) * scale.getY())
        );

        //TODO: deal with rotating rectangles
        if(topLeft.getY() <= point.getY() && point.getY() <= bottomRight.getY() &&
            topLeft.getX() <= point.getX() && point.getX() <= bottomRight.getX()) {
            return true;
        }
        return false;
    }

    public void processLogic() {
        setRotation((getRotation() + direction) % 360);
    }

    public void render(Renderer renderer, int maPositionHandle, int maTextureHandle, float[] modelMatrix){
        if(bitmap == null){
            Log.d("android-3d", "bitmap is null");
            return;
        }
        //apply matrix multiplications
        Matrix.translateM(modelMatrix, 0, position.getX(), position.getY(), 0);
        Matrix.scaleM(modelMatrix, 0, this.scale.getX(), this.scale.getY(), 0);
        Matrix.rotateM(modelMatrix, 0, this.rotation, 0.0f, 0.0f, 1.0f);

        //feed data to opengl
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer);

        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maTextureHandle);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);

        int err = GLES20.glGetError();
        if(err > 0) {
            Log.e("android-3d", "opengl hud render error: " + err);
        }
    }

    public void setScale(Vertex2D scale) {
        this.scale = scale;
    }

    @Override
    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    @Override
    public Vertex2D getScale() {
        return scale;
    }

    public void setAspectRatio(float ar) {
        if(ar == aspectRatio) {
            return;
        }
        aspectRatio = ar;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public void setPosition(Vertex2D pos) {
        if (position.equals(pos)) {
            return;
        }
        position = pos;
    }

    public void setBitmap(Bitmap bmap){
        bitmap = bmap;
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        this.height = bmap.getHeight();
        this.width = bmap.getWidth();
        this.generateVertexBuffer();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
