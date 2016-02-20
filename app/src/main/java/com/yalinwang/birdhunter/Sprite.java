package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class Sprite {
    private RectF rect;
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
        this.rect = rect;
        xVelocity = 0;
        yVelocity = 0;
        frame = 0;
        animationSpeed = AnimationSpeed.MEDIUM;
        bitmapIndex = 0;
        rectRegions = null;
    }

    public RectF getRect() {
        return rect;
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
     * @param rect
     */
    public void setRect(RectF rect) {
        this.rect = rect;
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
        return rect.intersect(other.rect);
    }

    public void setCollisionMargin(float margin) {

    }

    public void move() {
        rect.set(rect.left + xVelocity, rect.top + yVelocity,
                rect.right + xVelocity, rect.bottom + yVelocity);
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
