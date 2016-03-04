package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class Sprite implements DrawableArt {
    private RectF rectF;
    private RectF collisionRect;
    private List<Rect> rectRegions;

    public float getxVelocity() {
        return xVelocity;
    }

    public float getyVelocity() {
        return yVelocity;
    }

    private float xVelocity;
    private float yVelocity;
    private int frame;
    private AnimationSpeed animationSpeed;

    private Bitmap bitmap;
    private int bitmapIndex;

    public Sprite(RectF rect, Bitmap bitmap) {
        initParams(rect);
        this.bitmap = bitmap;
    }

    private void initParams(RectF rect) {
        this.rectF = rect;
        xVelocity = 0;
        yVelocity = 0;
        frame = 0;
        animationSpeed = AnimationSpeed.MEDIUM;
        bitmapIndex = 0;
        rectRegions = null;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setxVelocity(float xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(float yVelocity) {
        this.yVelocity = yVelocity;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setBitmapIndex(int bitmapIndex) {
        this.bitmapIndex = bitmapIndex;
    }

    /**
     * set display region on screen
     * @param rectF
     */
    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, getRectRegion(), rectF, null);
    }

    @Override
    public void update() {
        rectF.set(rectF.left + xVelocity, rectF.top + yVelocity,
                rectF.right + xVelocity, rectF.bottom + yVelocity);
    }

    public enum AnimationSpeed {
        SLOW(10),
        MEDIUM(8),
        FAST(6);

        private int speed;

        AnimationSpeed(int speed) {
            this.speed = speed;
        }

        public int getSpeed() {
            return speed;
        }
    }


    public boolean isCollidingWith(Sprite other) {
        return RectF.intersects(rectF, other.getRectF());
    }

    public void setCollisionMargin(float margin) {

    }

    public void setAnimationSpeed(AnimationSpeed animationSpeed) {
        this.animationSpeed = animationSpeed;
    }

    /**
     * Get the region on the sprite to show for current frame
     * @return
     */
    protected Rect getRectRegion() {


        // no animation regions defined
        if (rectRegions == null) {
            return null;
        }

        int regionSize = rectRegions.size();

        Rect rectRegion = rectRegions.get(bitmapIndex);

        // time to switch to next bitmap
        if (frame % animationSpeed.getSpeed() == 0) {
            bitmapIndex = (bitmapIndex + 1) % regionSize;
        }

        frame++;
        return rectRegion;
    }

    public void setRectRegions(List<Rect> rectRegions) {
        this.rectRegions = rectRegions;
    }
}
