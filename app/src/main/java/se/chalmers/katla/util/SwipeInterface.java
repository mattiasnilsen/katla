package se.chalmers.katla.util;

import android.view.View;

/**
 * Created by neon on 2014-10-19.
 * http://stackoverflow.com/questions/937313/android-basic-gesture-detection
 */
public interface SwipeInterface {

    public void bottom2top(View v);

    public void left2right(View v);

    public void right2left(View v);

    public void top2bottom(View v);

}