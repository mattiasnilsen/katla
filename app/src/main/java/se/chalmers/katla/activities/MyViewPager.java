package se.chalmers.katla.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import se.chalmers.katla.views.BlankFragment;
import se.chalmers.katla.views.BlankFragment2;

/**
 * Created by Anna on 2014-10-06.
 */
public class MyViewPager extends ViewPager {

    private GestureDetector gestureDetector;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGestureDetector (GestureDetector gestureDetector){
        this.gestureDetector=gestureDetector;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

}

