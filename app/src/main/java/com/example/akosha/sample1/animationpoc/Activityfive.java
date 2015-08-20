package com.example.akosha.sample1.animationpoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class Activityfive extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activityfive);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

        LandingTabAdapter adapter = new LandingTabAdapter(getSupportFragmentManager());
        //viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
        springIndicator.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activityfive, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

class LandingTabAdapter extends FragmentPagerAdapter {


    private static final int COUNT = 6;

    private String[] title = {"Companies", "Explore", "Messages", "Companies1", "Explore1", "Messages1", "Explore1", "Messages1"};


    public LandingTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new CardOne();
                break;
            case 1:
                fragment = new Fragment1();
                break;
            case 2:
                fragment = new Screenthree.Card2();
                break;
            case 3:
                fragment = new CardOne();
                break;
            case 4:
                fragment = new Fragment1();
                break;
            case 5:
                fragment = new Screenthree.Card2();
                break;
            case 6:
                fragment = new Screenthree.Card2();
                break;
            case 7:
                fragment = new Fragment1();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return COUNT;
    }

}