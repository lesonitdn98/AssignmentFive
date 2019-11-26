package me.lesonnnn.game;

import android.graphics.Canvas;
import android.view.MotionEvent;
import java.util.ArrayList;

class SceneManager {
    private ArrayList<Scene> mScenes = new ArrayList<>();
    static int ACTIVE_SCENE;

    SceneManager() {
        ACTIVE_SCENE = 0;
        mScenes.add(new GameplayScene());
    }

    void recieveTouch(MotionEvent event) {
        mScenes.get(ACTIVE_SCENE).recieveTouch(event);
    }

    void update() {
        mScenes.get(ACTIVE_SCENE).update();
    }

    void draw(Canvas canvas) {
        mScenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
