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
    private Thread animationThread;
    private List<Sprite> sprites;
    protected boolean isAnimating;

    public GameBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sprites = new ArrayList<>();
        isAnimating = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw all the sprites
        for (Sprite sp : sprites) {
            canvas.drawBitmap(sp.getBitmap(), sp.getRectRegion(), sp.getRect(), null);
        }
    }

    protected void add(Sprite sprite) {
        sprites.add(sprite);
    }

    protected void remove(Sprite sprite) {
        sprites.remove(sprite);
    }

    /**
     * Move all sprites in the container
     */
    protected void onAnimationTick() {
        for (Sprite sp : sprites) {
            sp.move();
        }
    }

    /**
     * Main animation loop
     *
     * @param fps - frames per second
     */
    private void animate(final int fps) {
        animationThread = new Thread(new Runnable() {

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
