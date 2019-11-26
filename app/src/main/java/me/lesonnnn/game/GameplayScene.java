package me.lesonnnn.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

public class GameplayScene implements Scene {

    private Rect r = new Rect();
    private RectPlayer mPlayer;
    private Point mPlayerPoint;
    private ObstacleManager mObstacleManager;
    private BallManager mBallManager;
    private Ball mBall;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;
    private String game_log;

    GameplayScene() {
        mBall = new Ball(new Rect(100, 100, 150, 150));
        mPlayer = new RectPlayer(new Rect(0, 0, 200, 30), Color.rgb(44, 62, 80));
        mPlayerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 100);

        mPlayer.update(mPlayerPoint, mBall);

        mObstacleManager = new ObstacleManager(10, 10, 75, Color.rgb(39, 174, 96));

        // Ball
        mBallManager = new BallManager(mBall);
    }

    private void reset() {
        mBall = new Ball(new Rect(100, 100, 150, 150));
        mBallManager = new BallManager(mBall);

        mPlayerPoint = new Point(Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT - 100);
        mPlayer.update(mPlayerPoint, mBall);
        mObstacleManager = new ObstacleManager(10, 10, 75, Color.rgb(39, 174, 96));
        movingPlayer = false;
    }

    @Override
    public void update() {
        if (!gameOver) {
            mPlayer.update(mPlayerPoint, mBall);
            mObstacleManager.update(mBall);
            mBallManager.update(mPlayer, mObstacleManager);
            if (mObstacleManager.playerCollide(mPlayer) || mBallManager.playerCollide()) {
                gameOver = true;
                game_log = "Game Over!!!";
                gameOverTime = System.currentTimeMillis();
            }
            if (mObstacleManager.checkGame()){
                gameOver = true;
                game_log = "You Win!!!";
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        mBallManager.draw(canvas);
        mPlayer.draw(canvas);
        mObstacleManager.draw(canvas);
        if (gameOver) {
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.rgb(192, 57, 43));
            drawCenterText(canvas, paint);
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(game_log, 0, game_log.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(game_log, x, y, paint);
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && mPlayer.getRectangle()
                        .contains((int) event.getX(), (int) event.getY())) {
                    movingPlayer = false;
                }
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (!(gameOver && movingPlayer)) {

                    int x = (int) event.getX();
                    if (x < 100){
                        x = 100;
                    } else if (x > Constants.SCREEN_WIDTH - 100){
                        x = Constants.SCREEN_WIDTH - 100;
                    }
                    //Position
                    mPlayerPoint.set(x, Constants.SCREEN_HEIGHT - 100);
                }
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }
}
