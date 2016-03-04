package com.yalinwang.birdhunter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class GameBaseView extends View {
    private List<DrawableArt> arts;
    protected boolean isAnimating;

    public GameBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        arts = new ArrayList<>();
        isAnimating = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw all the arts
        for (DrawableArt art : arts) {
            art.draw(canvas);
        }


    }

    protected void add(DrawableArt art) {
        arts.add(art);
    }

    protected void remove(DrawableArt art) {
        arts.remove(art);
    }

    /**
     * Update all arts in the container
     */
    protected void onAnimationTick() {
        for (DrawableArt art : arts) {
            art.update();
        }
    }

    /**
     * Main animation loop
     *
     * @param fps - frames per second
     */
    private void animate(final int fps) {
        Thread animationThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (isAnimating) {
                    try {
                        Thread.sleep(1000 / fps);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                    onAnimationTick();
                    postInvalidate();
                }
            }
        });
        animationThread.start();
    }

    protected void startAnimation(int fps) {
        isAnimating = true;
        animate(fps);
    }

    protected void stopAnimation() {
        isAnimating = false;
    }
}
