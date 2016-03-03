package com.yalinwang.birdhunter;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by ywang28 on 3/2/16.
 */
public class Bird {

    private BirdSprite sprite;
    private int totalHP, currHP;

    public Bird(Resources res, BirdType type, float left, float top) {
        sprite = new BirdSprite(left, top, type.getWidth(), type.getHeight(), type.getOffset(),
                BitmapFactory.decodeResource(res, type.getId()));
        totalHP = type.getHp();
        currHP = totalHP;
    }

    public enum BirdType {
        BLUE_BIRD(150, 75, 10, 100, R.drawable.bird2_sp);

        BirdType(int width, int height, int offset, int hp, int id) {
            this.width = width;
            this.height = height;
            this.offset = offset;
            this.hp = hp;
            this.id = id;
        }

        private int width, height, offset, hp, id;

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getOffset() {
            return offset;
        }

        public int getHp() {
            return hp;
        }

        public int getId() {
            return id;
        }
    }

    public void setxVelocity(int v) {
        sprite.setxVelocity(v);
    }

    public void setAnimationSpeed(Sprite.AnimationSpeed sp) {
        sprite.setAnimationSpeed(sp);
    }

    public RectF getRect() {
        return sprite.getRect();
    }

    public BirdSprite getSprite() {
        return sprite;
    }

    public boolean isCollidingWith(Sprite other) {
        return sprite.isCollidingWith(other);
    }

    public void reverseFlyingDirection() {
        sprite.reverseFlyingDirection();
    }

    public void loseHP(int p) {
        currHP = Math.max(0, currHP - p);
        sprite.setHpRatio((float)currHP / totalHP);
    }

    public boolean isDead() {
        return currHP == 0;
    }
}
