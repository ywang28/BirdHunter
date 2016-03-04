package com.yalinwang.birdhunter;

import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by ywang28 on 2/28/16.
 */
public class ArrowShooter implements DrawableArt {
    private float xPos;

    public float getxPos() {
        return xPos;
    }

    // reloading time is 10 frames
    private static final int FRAMES_PER_SHOOT = 10;
    private int frameCount;

    public ArrowShooter(float xPos) {
        this.xPos = xPos;
        frameCount = 0;
    }

    /**
     * Only ready to shoot after reloading time
     * @return
     */
    public boolean readyToShoot() {
        if (frameCount > FRAMES_PER_SHOOT) {
            frameCount = 0;
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        float height = canvas.getHeight();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255, 0, 255, 0);
        canvas.drawRect(xPos - 30, height - 30, xPos + 30, height, paint);
    }

    @Override
    public void update() {
        frameCount++;
    }
}
