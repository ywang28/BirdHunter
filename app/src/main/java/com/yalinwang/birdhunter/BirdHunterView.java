package com.yalinwang.birdhunter;

import android.app.Activity;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ywang28 on 2/14/16.
 */
public class BirdHunterView extends GameBaseView {
    private List<Bird> birds;
    private List<Bird> birdsToRemove;
    private List<Arrow> arrows;
    private List<Arrow> arrowsToRemove;
    private Random random;
    private Label remainingBirdsLabel, coinLabel;
    private static final int BIRDS_COUNT = 10;
    private static final int BIRD_COIN = 10;
    private static final int COINS_TO_UPGRADE_ARROW = 20;
    private int remainingBirds;
    private int coins;
    private ArrowShooter shooter;

    public BirdHunterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGame();
    }

    private void initGame() {
        birds = new ArrayList<>();
        birdsToRemove = new ArrayList<>();
        arrows = new ArrayList<>();
        arrowsToRemove = new ArrayList<>();
        random = new Random();


        remainingBirds = BIRDS_COUNT;
        coins = 0;
        addLabels();
    }

    private void addLabels() {
        remainingBirdsLabel = new Label("Remaining birds: " + remainingBirds, 10, 30, null);
        coinLabel = new Label("Coin: " + coins, 300, 30, null);

        add(remainingBirdsLabel);
        add(coinLabel);
    }

    private void addBird(float left, float top) {
        Bird bird = new Bird(getResources(), Bird.BirdType.BLUE_BIRD, left, top);
        bird.setxVelocity(15);
        bird.setAnimationSpeed(Sprite.AnimationSpeed.FAST);
        birds.add(bird);
        add(bird.getSprite());
    }

    public void startGame() {
        if (remainingBirds == 0) {
            remainingBirds = BIRDS_COUNT;
        }
        // start new game or resume old game
        updateLabels();

        if (shooter == null) {
            // initially add only one shooter in the center
            shooter = new ArrowShooter(getWidth() / 2, getHeight());
            add(shooter);
        }

        startAnimation(20);
    }

    @Override
    protected void onAnimationTick() {
        super.onAnimationTick();

        addBirds();

        updateBirds();

        boolean gameComplete = detectCollision();

        updateUpgradeButtonStatus();

        // reset birds and coins after game is complete
        if (gameComplete) {
            stopAnimation();
            remainingBirds = BIRDS_COUNT;
            coins = 0;
        }

    }

    private void updateLabels() {
        remainingBirdsLabel.setText("Remaining Birds: " + remainingBirds);
        coinLabel.setText("Score: " + coins);
    }

    /**
     * add up to 3 birds
     */
    private void addBirds() {

        while (birds.size() < 3 && remainingBirds > 2) {
            addBird(random.nextFloat() * 200, random.nextFloat() * 200 + 80);
        }
    }

    /**
     * Reverse flying direction if birds hit wall
     */
    private void updateBirds() {
        for (Bird bird : birds) {
            RectF birdRect = bird.getRectF();
            // if bird hits edge of the screen, fly backwards
            if (birdRect.right > getWidth() || birdRect.left < 0) {
                bird.reverseFlyingDirection();
            }
        }
    }

    private boolean detectCollision() {
        if (arrows != null) {
            for (Arrow arrow : arrows) {
                // remove off screen arrows
                if (arrow.getRectF().bottom < 0) {
                    arrowsToRemove.add(arrow);
                }
                else {
                    for (Bird bird : birds) {
                        if (bird.isCollidingWith(arrow.getArrowSprite())) {
                            remove(arrow.getArrowSprite());
                            arrowsToRemove.add(arrow);
                            bird.loseHP(shooter.getPower());
                            // remove bird if it's dead and update coins
                            if (bird.isDead()) {
                                remainingBirds--;
                                coins += BIRD_COIN;
                                updateLabels();
                                remove(bird.getSprite());
                                birdsToRemove.add(bird);
                            }
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
            createArrow();
        }
        return super.onTouchEvent(event);
    }

    /**
     * create an arrow to shoot birds
     */
    private void createArrow() {
        if (shooter.readyToShoot()) {
            Arrow arrow = shooter.createArrow(getResources());
            add(arrow.getArrowSprite());
            arrows.add(arrow);
        }
    }

    /**
     * TODO coins conflicts in multi threading.
     */
    public  void upgradeArrow() {
        shooter.upgrade();
        coins -= COINS_TO_UPGRADE_ARROW;
        updateUpgradeButtonStatus();
    }

    private void updateUpgradeButtonStatus() {
        // disable the button if not enough money for next upgrade
        Activity activity = (Activity) getContext();
        Button upgradeArrowButton = (Button) activity.findViewById(R.id.upgrade_arrow_button);
        upgradeArrowButton.setEnabled(coins >= COINS_TO_UPGRADE_ARROW);
    }

}
