package me.lesonnnn.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private static final int MAX_FPS = 30;
    private final SurfaceHolder mSurfaceHolder;
    private GamePanel mGamePanel;
    private boolean running;

    void setRunning(boolean running) {
        this.running = running;
    }

    MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        mSurfaceHolder = surfaceHolder;
        mGamePanel = gamePanel;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000 / MAX_FPS;

        while (running) {
            startTime = System.nanoTime();
            Canvas canvas = null;

            try {
                canvas = this.mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    mGamePanel.update();
                    mGamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000;
            waitTime = targetTime - timeMillis;

            try {
                if (waitTime > 0) {
                    sleep(waitTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;

            if (frameCount == MAX_FPS) {
                double averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
