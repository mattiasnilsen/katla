package se.chalmers.katla.activities;

import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Anna on 2014-10-05.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapter {
        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

    @Override
    public android.support.v4.app.Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new BlankFragment();
                case 1:
                    return new BlankFragment2();

            }
            return null;
    }


    @Override
    public int getCount() {
        return 3;
    }
}
