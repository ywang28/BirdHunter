package com.yalinwang.birdhunter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ywang28 on 2/14/16.
 */
public class BirdHunterView extends GameBaseView {
    private List<BirdSprite> birds;
    private List<BirdSprite> birdsToRemove;
    private List<Sprite> arrows;
    private List<Sprite> arrowsToRemove;
    private Random random;
    private Label remainingBirdsLabel, scoreLabel;
    private static final int BIRDS_COUNT = 20;
    private static final int BIRD_SCORE = 10;
    private int remainingBirds;
    private int score;

    public BirdHunterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame();
    }

    private void initGame() {
        birds = new ArrayList<>();
        birdsToRemove = new ArrayList<>();
        random = new Random();

        remainingBirds = BIRDS_COUNT;
        score = 0;
        addLabels();
    }

    private void addLabels() {
        remainingBirdsLabel = new Label("Remaining birds: " + remainingBirds, 10, 30, null);
        scoreLabel = new Label("Score: " + score, 300, 30, null);

        add(remainingBirdsLabel);
        add(scoreLabel);
    }

    private void addBird(float left, float top) {
        BirdSprite bird = new BirdSprite(left, top, 150, 75, 10,
                BitmapFactory.decodeResource(getResources(), R.drawable.bird2_sp));
        bird.setxVelocity(15);
        bird.setAnimationSpeed(Sprite.AnimationSpeed.FAST);
        birds.add(bird);
        add(bird);
    }

    public void startGame() {
        // start new game
        if (remainingBirds == 0) {
            remainingBirds = BIRDS_COUNT;
        }
        startAnimation(20);
    }

    @Override
    protected void onAnimationTick() {
        super.onAnimationTick();

        addBirds();

        updateBirds();

        boolean gameComplete = detectCollision();

        updateLabels();

        if (gameComplete) {
            stopAnimation();
        }

    }

    private void updateLabels() {
        remainingBirdsLabel.setText("Remaining Birds: " + remainingBirds);
        scoreLabel.setText("Score: " + score);
    }

    /**
     * add up to 3 birds
     */
    private void addBirds() {

        while (birds.size() < 3 && remainingBirds > 2) {
            addBird(random.nextFloat() * 200, random.nextFloat() * 200 + 40);
        }
    }

    /**
     * Reverse flying direction if birds hit wall
     */
    private void updateBirds() {
        for (BirdSprite bird : birds) {
            RectF birdRect = bird.getRect();
            // if bird hits edge of the screen, fly backwards
            if (birdRect.right > getWidth() || birdRect.left < 0) {
                bird.reverseFlyingDirection();
            }
        }
    }

    private boolean detectCollision() {
        if (arrows != null) {
            for (Sprite arrow : arrows) {
                // remove off screen arrows
                if (arrow.getRect().bottom < 0) {
                    arrowsToRemove.add(arrow);
                }
                else {
                    for (BirdSprite bird : birds) {
                        if (bird.isCollidingWith(arrow)) {
                            remainingBirds--;
                            score += BIRD_SCORE;
                            remove(bird);
                            remove(arrow);
                            birdsToRemove.add(bird);
                            arrowsToRemove.add(arrow);
                        }
                    }
                }
                // game complete
                if (remainingBirds == 0) {
                    return true;
                }
            }
            birds.removeAll(birdsToRemove);
            arrows.removeAll(arrowsToRemove);
            birdsToRemove.clear();
            arrowsToRemove.clear();
        }
        return false;
    }

    /**
     * shoot arrow from bottom at x position of touch
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isAnimating) {
            createArrow(event.getRawX());
        }
        return super.onTouchEvent(event);
    }

    /**
     * create an arrow to shoot birds
     * @param x
     */
    private void createArrow(float x) {
        if (arrows == null) {
            arrows = new ArrayList<>();
            arrowsToRemove = new ArrayList<>();
        }
        Sprite arrow = new Sprite(new RectF(x - 10, getHeight() - 100, x + 10, getHeight()),
                BitmapFactory.decodeResource(getResources(), R.drawable.arrow));
        arrow.setyVelocity(-10);
        add(arrow);
        arrows.add(arrow);
    }
}
