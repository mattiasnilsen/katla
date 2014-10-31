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

    private final Activity activity;
    private final Intent intent;

    public MyGestureListener(Activity activity, Intent intent) {
        super();
        this.activity = activity;
        this.intent=intent;
    }


    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1 == null || e2 == null){
            return false;
        }
        float sensitvity = 50;
        if((e2.getY() - e1.getY()) > sensitvity){
            System.out.println("Nu drar du neråt! :D");
            activity.startActivity(intent);
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }
}
