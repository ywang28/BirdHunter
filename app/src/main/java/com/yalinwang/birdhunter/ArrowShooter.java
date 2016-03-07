package com.yalinwang.birdhunter;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

/**
 * Created by ywang28 on 2/28/16.
 */
public class ArrowShooter implements DrawableArt {
    private float xPos;
    private float yPos;
    private ArrowType arrowType;
    private static final double UPGRADE_FACTOR_BASE = 1.5;
    private double upgradeFactor;


    public int getPower() {
        // TODO int overflow problem
        return (int)(arrowType.getPower() * upgradeFactor);
    }

    /**
     * multiply power factor by upgrade base factor each time
     */
    public void upgrade() {
        upgradeFactor *= UPGRADE_FACTOR_BASE;
    }

    // reloading time is 10 frames
    private static final int FRAMES_PER_SHOOT = 10;
    private int frameCount;

    public ArrowShooter(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        arrowType = ArrowType.BASIC_ARROW;
        frameCount = 0;
        upgradeFactor = 1.0;
    }

    public enum ArrowType {
        BASIC_ARROW(20, 100, -10, 50, R.drawable.arrow);

        ArrowType(int width, int height, int velocity, int power, int id) {
            this.width = width;
            this.height = height;
            this.velocity = velocity;
            this.power = power;
            this.id = id;
        }

        private int width, height, velocity, power, id;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getVelocity() {
            return velocity;
        }

        public int getPower() {
            return power;
        }

        public int getId() {
            return id;
        }
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

    public Arrow createArrow(Resources res) {
        int halfWidth = arrowType.getWidth() / 2;
        int height = arrowType.getHeight();
        int id = arrowType.getId();
        return new Arrow(new RectF(xPos - halfWidth, yPos - height, xPos + halfWidth, yPos),
                BitmapFactory.decodeResource(res, id),
                arrowType.getVelocity(),
                getPower());
    }
}
