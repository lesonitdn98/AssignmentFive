package me.lesonnnn.game;

import android.graphics.Canvas;
import java.util.ArrayList;

class ObstacleManager {
    private ArrayList<Obstacle> mObstacles;
    private int mPlayerGap;
    private int mObstacleGap;
    private int mObstacleHeight;
    private int mColor;

    private long startTime;

    ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color) {
        mPlayerGap = playerGap;
        mObstacleGap = obstacleGap;
        mObstacleHeight = obstacleHeight;
        mColor = color;

        startTime = System.currentTimeMillis();
        mObstacles = new ArrayList<>();

        populateObstacle();
    }

    public ArrayList<Obstacle> getObstacles() {
        return mObstacles;
    }

    boolean playerCollide(RectPlayer player) {
        for (Obstacle ob : mObstacles) {
            if (ob.playerCollide(player)) {
                return true;
            }
        }
        return false;
    }

    boolean checkGame() {
        if (mObstacles.size() == 0){
            return true;
        }
        return false;
    }

    private void populateObstacle() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 5;
        int x = (Constants.SCREEN_WIDTH - 5*mPlayerGap) / 4;
        while (currY < 0) {
            mObstacles.add(new Obstacle(mObstacleHeight, mColor, x + mPlayerGap, currY, mPlayerGap));
            mObstacles.add(new Obstacle(mObstacleHeight, mColor, 2*(x + mPlayerGap), currY, mPlayerGap));
            mObstacles.add(new Obstacle(mObstacleHeight, mColor, 3*(x + mPlayerGap), currY, mPlayerGap));
            mObstacles.add(new Obstacle(mObstacleHeight, mColor, 4*(x + mPlayerGap), currY, mPlayerGap));
            currY += mObstacleHeight + mObstacleGap;
        }
    }

    void update(Ball ball) {
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = Constants.SCREEN_HEIGHT / 40000.0f;
        for (Obstacle ob : mObstacles) {
            ob.incrementY(speed * elapsedTime);
        }
        // remove obstacle
        for (Obstacle ob : mObstacles) {
            if (ob.getRectangle().contains(ball.getRectangle().left, ball.getRectangle().bottom)
                    || ob.getRectangle().contains(ball.getRectangle().left, ball.getRectangle().top)
                    || ob.getRectangle()
                    .contains(ball.getRectangle().right, ball.getRectangle().bottom)
                    || ob.getRectangle()
                    .contains(ball.getRectangle().right, ball.getRectangle().top)) {
                mObstacles.remove(ob);
                ball.setDirectionY(1);
                break;
            }
        }
    }

    void draw(Canvas canvas) {
        for (Obstacle ob : mObstacles) {
            ob.draw(canvas);
        }
    }
}
