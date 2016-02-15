package com.yalinwang.birdhunter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class GameBaseView extends View {
    private List<Sprite> sprites;
    private boolean isRunningAnimation;

    public GameBaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        sprites = new ArrayList<>();
        isRunningAnimation = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw all the sprites
        for (Sprite sp : sprites)  {
            canvas.drawBitmap(sp.getCurrentBitmap(), null, sp.getRect(), null);
        }
    }

    protected void add(Sprite sprite) {
        sprites.add(sprite);
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
     * @param fps - frames per second
     */
    private void animate(final int fps) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunningAnimation) {
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
        thread.start();
    }

    protected void startAnimation(int fps) {
        isRunningAnimation = true;
        animate(fps);
    }

    protected void stopAnimation() {
        isRunningAnimation = false;
    }
}
