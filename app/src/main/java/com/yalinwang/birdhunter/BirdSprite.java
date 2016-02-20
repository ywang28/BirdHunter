package com.yalinwang.birdhunter;

import android.graphics.Bitmap;
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

    public BirdSprite(float left, float top, int width, int height,
                      int offset, Bitmap bitmap) {
        super(new RectF(left, top, left + width, top + height), bitmap);

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
}
