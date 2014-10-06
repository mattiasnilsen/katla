package se.chalmers.katla.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import se.chalmers.katla.R;
import se.chalmers.katla.views.BlankFragment;
import se.chalmers.katla.views.BlankFragment2;
import se.chalmers.katla.views.BlankFragment3;

public class SwipeMainActivity extends FragmentActivity implements ActionBar.TabListener{
    MyViewPager viewpager;
    PagerAdapter TabAdapter;
    ActionBar actionBar;
    Fragment bf= new BlankFragment(), bf2 = new BlankFragment2(), bf3 = new BlankFragment3();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_main);
        TabAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewpager = (MyViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(TabAdapter);
        viewpager.setGestureDetector(new GestureDetector(this, new MyGestureListener(this)));

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

         viewpager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

       actionBar.addTab(actionBar.newTab().setText("sida ett").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("sida två").setTabListener(this));
       actionBar.addTab(actionBar.newTab().setText("sida tre").setTabListener(this));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        /*if (viewpager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewpager.setCurrentItem(viewpager.getCurrentItem() - 1);
        }*/
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
            viewpager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

       public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0: return bf;
                case 1: return bf2;
                case 2: return bf3;
                default: return new Fragment();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.swipe_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
