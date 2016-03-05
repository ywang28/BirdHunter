package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
import android.graphics.RectF;

/**
 * Created by ywang28 on 2/28/16.
 */
public class Arrow  {
    private int power;

    public Sprite getArrowSprite() {
        return arrowSprite;
    }

    private Sprite arrowSprite;

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Arrow(RectF rect, Bitmap bitmap, float velocity, int power) {
        arrowSprite = new Sprite(rect, bitmap);
        arrowSprite.setyVelocity(velocity);
        this.power = power;
    }

    public RectF getRectF() {
        return arrowSprite.getRectF();
    }

    public boolean isCollidingWith(Sprite other) {
        return arrowSprite.isCollidingWith(other);
    }
}
