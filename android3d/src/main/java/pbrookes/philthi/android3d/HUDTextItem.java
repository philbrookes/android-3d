package pbrookes.philthi.android3d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class HUDTextItem {
    protected Vertex2D position;
    protected boolean dirty;
    protected Bitmap bitmap;
    protected Canvas canvas;
    protected FloatBuffer vertexBuffer, texCoordBuffer;
    protected int[] texture;
    protected String text = "HUD ITEM";
    protected Float aspectRatio = 1.0f;
    protected Paint paint;
    protected Float rotation = 0.0f;
    protected int width = 512, height = 512;
    public HUDTextItem(Vertex2D pos) {
        this.position = pos;
        // Create a bitmap for our android canvas that'll get passed to OpenGL ES as a texture later on
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        // Create canvas that's used to draw on the bitmap
        canvas = new Canvas(bitmap);
        texture = new int[16];

        // Generate buffers for vertices and texture coordinates
        float mVertices[]  = {0,0,0, 1,0,0, 0,1,0, 1,1,0};
        float mTexCoords[] = {0,1, 1,1, 0,0, 1,0};
        vertexBuffer = ByteBuffer.allocateDirect(mVertices.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexBuffer.put(mVertices).position(0);
        texCoordBuffer = ByteBuffer.allocateDirect(mTexCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        texCoordBuffer.put(mTexCoords).position(0);

        // Generate OpenGL ES texture
        GLES20.glGenTextures(1, texture, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        paint = new Paint();
        paint.setTextSize(10);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setAntiAlias(false);
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.LEFT);

        dirty = true;
    }

    public void setAspectRatio(Float ar) {
        if(ar == aspectRatio) {
            return;
        }
        dirty = true;
        aspectRatio = ar;
    }

    public void setHeight(int height) {
        this.height = height;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        dirty = true;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        dirty = true;
    }

    public int getWidth() {
        return width;
    }

    public boolean isDirty() {
        return dirty;
    }

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint newPaint) {
        dirty = true;
        paint = newPaint;
    }

    public Float getRotation() {
        return rotation;
    }

    public void setRotation(Float rotation) {
        dirty = true;
        this.rotation = rotation;
    }

    public Vertex2D getPosition() {
        return position;
    }

    public void setPosition(Vertex2D pos) {
        if (position.equals(pos)) {
            return;
        }
        dirty = true;
        position = pos;
    }

    public void setText(String txt){
        if(txt == text) {
            return;
        }
        dirty = true;
        text = txt;
    }

    public void onPress() {
    }

    public void render(Renderer renderer, int maPositionHandle, int maTextureHandle) {
        if(dirty) {
            this.update();
        }

        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glVertexAttribPointer(maTextureHandle, 2, GLES20.GL_FLOAT, false, 0, texCoordBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glEnableVertexAttribArray(maTextureHandle);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, vertexBuffer.capacity() / 3);

        int err = GLES20.glGetError();
        if(err > 0) {
            Log.e("android-3d", "opengl hud render error: " + err);
        }
    }

    protected void update() {
        bitmap.eraseColor(Color.TRANSPARENT);

        // Scale depending on viewport ratio
        canvas.save();
        canvas.scale(1.0f, 1.0f);

        canvas.drawText(text, position.getX(), position.getY(), paint);

        // Upload our bitmap to OpenGL. We use texSubImage this time as it's faster
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture[0]);
        GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);

        dirty = false;
    }

}