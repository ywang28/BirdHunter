package com.yalinwang.birdhunter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created by ywang28 on 2/14/16.
 */
public class BirdHunterView extends GameBaseView {
    private Sprite bird;

    public BirdHunterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bird = new Sprite(new RectF(10, 10, 210, 60),
                BitmapFactory.decodeResource(getResources(), R.drawable.bird1));
        bird.setxVelocity(10);
        add(bird);
    }

    public void startGame() {
        startAnimation(20);
    }

    @Override
    protected void onAnimationTick() {
        RectF birdRect = bird.getRect();
        // if bird hits edge of the screen, fly backwards
        if (birdRect.right > getWidth() || birdRect.left < 0) {
            bird.setxVelocity( - bird.getxVelocity());

        }
        super.onAnimationTick();
    }
}
