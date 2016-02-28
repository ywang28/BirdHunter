package com.yalinwang.birdhunter;

import android.graphics.Canvas;

/**
 * Created by ywang28 on 2/27/16.
 */
public interface DrawableArt {

    /**
     * draw current art on the canvas
     * @param canvas
     */
    public void draw(Canvas canvas);

    /**
     * update current art for each animation tick
     */
    public void update();
}