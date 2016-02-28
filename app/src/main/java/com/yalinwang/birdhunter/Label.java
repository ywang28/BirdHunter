package com.yalinwang.birdhunter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;

/**
 * Created by ywang28 on 2/27/16.
 */
public class Label implements DrawableArt {
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    private float xPos;
    private float yPos;
    private Paint paint;

    public Label(String text, float xPos, float yPos, Paint paint) {
        this.text = text;
        this.xPos = xPos;
        this.yPos = yPos;

        if (paint == null) {
            setDefaulPaint();
        }
        else {
            this.paint = paint;
        }
    }

    /**
     * set default paint styles
     */
    private void setDefaulPaint() {
        paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        paint.setTypeface(Typeface.SANS_SERIF);
        paint.setTextSize(20);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(text, xPos, yPos, paint);
    }

    @Override
    public void update() {

    }
}
