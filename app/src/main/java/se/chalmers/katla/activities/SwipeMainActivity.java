package se.chalmers.katla.activities;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import se.chalmers.katla.R;
import se.chalmers.katla.model.CompositesXmlParser;
import se.chalmers.katla.model.ICategory;
import se.chalmers.katla.model.IComposite;
import se.chalmers.katla.model.Katla;
import se.chalmers.katla.views.CompositeFragment;

public class SwipeMainActivity extends FragmentActivity implements ActionBar.TabListener,
                                                                   CompositeFragment.CompositeFragmentInteractionListener,
                                                                   InputDialogListener {

    private MyViewPager viewpager;
    private PagerAdapter tabAdapter;
    private ActionBar actionBar;
    private Katla katlaInstance = null;
    private List<CompositeFragment> fragments = new ArrayList<CompositeFragment>();

    private String textString = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_main);
        tabAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewpager = (MyViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(tabAdapter);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

         viewpager.setOnPageChangeListener(
                new ViewPager.SimpleOnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {
                        actionBar.setSelectedNavigationItem(position);
                    }
                });

        katlaInstance = Katla.getInstance();
        try {
            katlaInstance.loadComposites();
        } catch (CompositesXmlParser.ParseException e) {
            System.out.println(e.getMessage());
        }

        for(Iterator<ICategory> iterator = katlaInstance.getCategories(); iterator.hasNext();) {
            ICategory category = iterator.next();
            ActionBar.Tab tab = actionBar.newTab();

            tab.setText(category.getName());
            tab.setTabListener(this);
            actionBar.addTab(tab);

            Bundle args = new Bundle();
            List<IComposite> compositeList = new ArrayList<IComposite>();

            for(Iterator<IComposite> composites = category.getComposites(); composites.hasNext();) {
                compositeList.add(composites.next());
            }
            args.putSerializable("composites", (Serializable)compositeList);

            CompositeFragment compositeFragment = new CompositeFragment();
            compositeFragment.setArguments(args);
            fragments.add(compositeFragment);
        }

        tabAdapter.notifyDataSetChanged();
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

    @Override
    public void onCompositeUsed(String text, boolean containsUserInput) {
       if(containsUserInput) {
           textString = text;
       } else {
           katlaInstance.addStringToMessage(" " + text.replace("\n", "").trim());
           Toast.makeText(getApplicationContext(), "Added text to message", Toast.LENGTH_SHORT).show();
       }
    }

    @Override
    public void receiveInput(String input) {
         if(textString != null) {
             textString = textString.replace("%s", input);
             katlaInstance.addStringToMessage(" " + textString.replace("\n", "").trim());
             Toast.makeText(getApplicationContext(), "Added text to message", Toast.LENGTH_SHORT).show();
             textString = null;
         }
    }



    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if(position < fragments.size()) {
                return fragments.get(position);
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return fragments.size();
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
