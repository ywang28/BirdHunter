package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class Sprite {
    private RectF rect;
    private RectF collisionRect;
    private float xVelocity;
    private float yVelocity;
    private int frame;
    private AnimationSpeed speed;

    private List<Bitmap> bitmaps;
    private int bitmapIndex;

    public Sprite(RectF rect, List<Bitmap> bitmaps) {
        initParams(rect);
        this.bitmaps.addAll(bitmaps);
    }

    public Sprite(RectF rect, Bitmap bitmap) {
        initParams(rect);
        this.bitmaps.add(bitmap);
    }

    private void initParams(RectF rect) {
        this.rect = rect;
        this.bitmaps = new ArrayList<>();
        xVelocity = 0;
        yVelocity = 0;
        frame = 0;
        speed = AnimationSpeed.MEDIUM;
        bitmapIndex = 0;
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

    public Bitmap getCurrentBitmap() {
        int bitmapSize = bitmaps.size();

        // only one bitmap to return
        if (bitmapSize == 1) {
            return bitmaps.get(0);
        }

        Bitmap ret = bitmaps.get(bitmapIndex);

        // time to switch to next bitmap
        if (frame % speed.getSpeed() == 0) {
            bitmapIndex = (bitmapIndex + 1) % bitmapSize;
        }

        frame++;
        return ret;
    }
}
