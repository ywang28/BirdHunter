package com.yalinwang.birdhunter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ywang28 on 2/14/16.
 */
public class BirdHunterView extends GameBaseView {
    private Sprite bird, bird2;

    public BirdHunterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        List<Bitmap> bird2Bitmaps = new ArrayList<>();
        bird2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird2));
        bird2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird2_1));
        bird2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird2_2));
        bird2Bitmaps.add(BitmapFactory.decodeResource(getResources(), R.drawable.bird2_1));

        bird = new Sprite(new RectF(10, 10, 210, 60),
                BitmapFactory.decodeResource(getResources(), R.drawable.bird1));
        bird.setxVelocity(10);
        add(bird);

        bird2 = new Sprite(new RectF(50, 80, 350, 230), bird2Bitmaps);
        bird2.setxVelocity(15);
        add(bird2);
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
        RectF bird2Rect = bird2.getRect();
        // if bird hits edge of the screen, fly backwards
        if (bird2Rect.right > getWidth() || bird2Rect.left < 0) {
            bird2.setxVelocity( - bird2.getxVelocity());

        }

        super.onAnimationTick();
    }
}
