package com.example.akosha.sample1.animationpoc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class Screenthree extends AppCompatActivity {

    public static Screenthree ins = new Screenthree();
    private Animation anim;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myView = findViewById(R.id.screenthree);
        anim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        //myView.startAnimation(anim);
        

        setContentView(R.layout.activity_screenthree);

        if (savedInstanceState == null) {
            showfragment(0);
        }

        // make the view visible and start the animation
        //myView.setVisibility(View.VISIBLE);

    }

    /*@Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

        myView.startAnimation(anim);
        return myView;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_screenthree, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void showfragment(int i) {
        switch (i) {
            case 0:
                myView.startAnimation(anim);
                getSupportFragmentManager().beginTransaction().add(R.id.container, new CardOne()).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new Card2()).commit();
                break;
        }
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


    public static class Card2 extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.frgament2, container, false);
        }
    }

}

