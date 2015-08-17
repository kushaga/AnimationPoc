package com.example.akosha.sample1.animationpoc;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.akosha.sample1.animationpoc.smarttablayout.FragmentPagerItemAdapter;
import com.example.akosha.sample1.animationpoc.smarttablayout.FragmentPagerItems;
import com.example.akosha.sample1.animationpoc.smarttablayout.SmartTabLayout;

public class Activitysix extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitysix);

        //LandingTabAdapter adapter = new LandingTabAdapter(getSupportFragmentManager());

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("title1", Screenthree.Card2.class)
                .add("title2", CardOne.class)
                .add("title1", CardOne.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout smartTabLayout = (SmartTabLayout) findViewById(R.id.viewpagertab);
        //smartTabLayout.setDistributeEvenly(true);

        smartTabLayout.setTabTextColors(getResources().getColor(R.color
                .theme_accent));
        smartTabLayout.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activitysix, menu);
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
