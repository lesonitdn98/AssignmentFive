package me.lesonnnn.game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class Ball implements GameObject {
    private final float speedX = 0.4f;
    private final float speedY = 0.4f;

    private int directionX = 1;
    private int directionY = 1;
    private Rect mRect;
    private boolean mCheckGame;

    Ball(Rect rect) {
        mRect = rect;
        mCheckGame = false;
        mCheckGame = false;
    }

    Rect getRectangle() {
        return mRect;
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable = Constants.CURRENT_CONTEXT.getDrawable(R.drawable.ball);
        drawable.setBounds(mRect);
        drawable.draw(canvas);
        //        Paint paint = new Paint();
        //        paint.setColor(Color.MAGENTA);
        //        canvas.drawRect(mRect, paint);
    }

    public void setDirectionY(int directionY) {
        this.directionY = directionY;
    }

    @Override
    public void update() {
    }

    void incrementXY(RectPlayer player, float v) {

        if (mRect.left < 0) {
            directionX = 1;
        } else if (mRect.right > Constants.SCREEN_WIDTH) {
            directionX = -1;
        }
        if (mRect.contains(player.getRectangle().left, player.getRectangle().top) || mRect.contains(
                player.getRectangle().left, player.getRectangle().bottom) || mRect.contains(
                player.getRectangle().right, player.getRectangle().top) || mRect.contains(
                player.getRectangle().right, player.getRectangle().bottom)) {
            directionY = -1;
        }
        if (mRect.top < 0) {
            directionY = 1;
        } else if (mRect.bottom > Constants.SCREEN_HEIGHT) {
            mCheckGame = true;
        }

        mRect.top += directionY * speedY * v;
        mRect.bottom += directionY * speedY * v;
        mRect.left += directionX * speedX * v;
        mRect.right += directionX * speedX * v;
    }

    boolean playerCollide() {
        return mCheckGame;
    }
}
