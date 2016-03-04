package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywang28 on 2/19/16.
 */
public class BirdSprite extends Sprite {
    private List<Rect> forwardRectRegions;
    private List<Rect> backwardRectRegions;
    private boolean isFlyingForward;
    private float hpRatio;
    private static final int HP_BAR_WIDTH = 100;
    private static final int HP_BAR_HEIGHT = 20;
    private static final int HP_BAR_OFFSET = 10;

    public void setHpRatio(float hpRatio) {
        this.hpRatio = hpRatio;
    }

    public BirdSprite(float left, float top, int width, int height,
                      int offset, Bitmap bitmap) {
        super(new RectF(left, top, left + width, top + height), bitmap);

        hpRatio = 1;

        forwardRectRegions = new ArrayList<>();
        backwardRectRegions = new ArrayList<>();

        int widthOffset = width + offset;
        int heightOffset = height + offset;

        forwardRectRegions.add(new Rect(0, 0, width, height));
        forwardRectRegions.add(new Rect(0, heightOffset, width, heightOffset + height));
        forwardRectRegions.add(new Rect(0, heightOffset * 2, width, heightOffset * 2 + height));

        backwardRectRegions.add(new Rect(widthOffset, 0, widthOffset + width, height));
        backwardRectRegions.add(new Rect(widthOffset, heightOffset, widthOffset + width,
                heightOffset + height));
        backwardRectRegions.add(new Rect(widthOffset, heightOffset * 2, widthOffset + width,
                heightOffset * 2 + height));

        setFlyingForward();
    }

    private void setFlyingForward() {
        isFlyingForward = true;
        setRectRegions(forwardRectRegions);
    }

    private void setFlyingBackward() {
        isFlyingForward = false;
        setRectRegions(backwardRectRegions);
    }

    /**
     * Make bird fly in opposite direction
     */
    public void reverseFlyingDirection() {
        if (isFlyingForward) {
            setFlyingBackward();
        }
        else {
            setFlyingForward();
        }
        setxVelocity(- getxVelocity());
    }

    /**
     * draw the hp bar for the bird
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        Paint redPaint = new Paint();
        redPaint.setStyle(Paint.Style.FILL);
        redPaint.setARGB(255, 255, 0, 0);
        Paint blackPaint = new Paint();
        blackPaint.setStyle(Paint.Style.STROKE);
        blackPaint.setARGB(255, 0, 0, 0);
        float remainingHPWidth = HP_BAR_WIDTH * hpRatio;
        RectF rectF = getRectF();
        float mid = (rectF.left + rectF.right) / 2;
        float left = mid - HP_BAR_WIDTH / 2;
        float top = rectF.top - HP_BAR_HEIGHT - HP_BAR_OFFSET;
        canvas.drawRect(left, top, left + remainingHPWidth, top + HP_BAR_HEIGHT, redPaint);
        canvas.drawRect(left, top, mid + HP_BAR_WIDTH / 2, top + HP_BAR_HEIGHT, blackPaint);
        super.draw(canvas);
    }
}
