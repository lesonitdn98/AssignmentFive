package me.lesonnnn.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Obstacle implements GameObject {
    private Rect mRect;
    private int mColor;

    Rect getRectangle() {
        return mRect;
    }

    void incrementY(float y){
        mRect.top += y;
        mRect.bottom += y;
    }

    Obstacle(int rectHeight, int color,int startX, int startY, int playerGap) {
        mColor = color;
        int x = (Constants.SCREEN_WIDTH - 5*playerGap) / 4;
        mRect = new Rect(startX - x, startY, startX, startY + rectHeight);
    }

    boolean playerCollide(RectPlayer player) {
        if (mRect.bottom >= player.getRectangle().top) {
            return true;
        }
        // Cham day => true
        return false;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(mColor);
        canvas.drawRect(mRect, paint);
    }

    @Override
    public void update() {

    }
}
