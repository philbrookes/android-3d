package pbrookes.philthi.android3d;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class HUDTextItem extends HUDItem{
    protected boolean dirty;
    protected Canvas canvas;
    protected int width = 512, height = 512;
    protected String text = "HUD ITEM";
    protected Paint paint;

    public HUDTextItem(Vertex2D pos) {
        super(pos);
        // Create a bitmap for our android canvas that'll get passed to OpenGL ES as a texture later on
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        super.setBitmap(bitmap);

        // Create canvas that's used to draw on the bitmap
        canvas = new Canvas(bitmap);

        paint = new Paint();
        paint.setTextSize(30);
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
        super.setAspectRatio(ar);
    }

    public void setHeight(int height) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        dirty = true;
    }

    public void setWidth(int width) {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        dirty = true;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isDirty() {
        return dirty;
    }

    public Paint getPaint() {
        //paint might be modified, so assume dirty
        dirty = true;
        return paint;
    }

    public void setPaint(Paint newPaint) {
        dirty = true;
        paint = newPaint;
    }

    public void setRotation(Float rotation) {
        dirty = true;
        super.setRotation(rotation);
    }


    public void setPosition(Vertex2D pos) {
        if (position.equals(pos)) {
            return;
        }
        dirty = true;
        super.setPosition(pos);
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

    public void render(Renderer renderer, int maPositionHandle, int maTextureHandle, float[] modelMatrix) {
        if(dirty) {
            this.update();
        }

        super.render(renderer, maPositionHandle, maTextureHandle, modelMatrix);

    }

    protected void update() {
        bitmap.eraseColor(Color.TRANSPARENT);
        canvas.save();
        canvas.scale(1.0f, 1.0f);
        canvas.drawText(text, 0, 0, paint);
        this.setBitmap(bitmap);
        dirty = false;
    }

}