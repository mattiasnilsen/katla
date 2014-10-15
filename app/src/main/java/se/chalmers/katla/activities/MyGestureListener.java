package se.chalmers.katla.activities;

import android.app.Activity;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Anna on 2014-10-06.
 */
public class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

    private final SwipeMainActivity activity;

    public MyGestureListener(SwipeMainActivity activity) {
        super();
        this.activity = activity;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float sensitvity = 50;
        if((e2.getY() - e1.getY()) > sensitvity){
            activity.scrollUp();
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
