package me.lesonnnn.game;

import android.graphics.Canvas;

class BallManager {
    private long startTime;
    private Ball mBall;

    BallManager(Ball ball) {
        startTime = System.currentTimeMillis();
        mBall = ball;
    }

    boolean playerCollide() {
        if (mBall.playerCollide()) {
            return true;
        }
        return false;
    }

    void update(RectPlayer player, ObstacleManager obstacleManager) {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        mBall.incrementXY(player, elapsedTime);
    }

    void draw(Canvas canvas) {
        mBall.draw(canvas);
    }
}
