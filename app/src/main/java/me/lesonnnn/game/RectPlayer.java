package me.lesonnnn.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    Rect getRectangle() {
        return rectangle;
    }

    RectPlayer(Rect rectangle, int color) {
        this.rectangle = rectangle;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {
    }

    void update(Point point, Ball ball) {
        rectangle.set(point.x - rectangle.width() / 2, point.y - rectangle.height() / 2,
                point.x + rectangle.width() / 2, point.y + rectangle.height() / 2);
        if (rectangle.contains(ball.getRectangle().left, ball.getRectangle().top)
                || rectangle.contains(ball.getRectangle().left, ball.getRectangle().bottom)
                || rectangle.contains(ball.getRectangle().right, ball.getRectangle().top)
                || rectangle.contains(ball.getRectangle().right, ball.getRectangle().bottom)) {
            ball.setDirectionY(-1);
        }
    }
}
