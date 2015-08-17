package com.example.akosha.sample1.animationpoc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Scene firstScene;
    private Scene secondScene;
    private Transition fadeTransition;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: animate view pager

        ViewGroup mScreenRoot = (ViewGroup) findViewById(R.id.scene_root);

        firstScene = Scene.getSceneForLayout(mScreenRoot, R.layout.firstscreen, this);
        secondScene = Scene.getSceneForLayout(mScreenRoot, R.layout.secondscreen, this);

        Button firstButton = (Button) findViewById(R.id.firstScreenButton);
        Button secondButton = (Button) findViewById(R.id.secondScreenButton);

        fadeTransition = TransitionInflater.from(this).
                inflateTransition(R.transition.fade_transition);


        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(secondScene, fadeTransition);
            }
        });

        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(firstScene, fadeTransition);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void onButClick(View view) {
        TransitionManager.go(firstScene, fadeTransition);
        //open another activity
        startActivity(new Intent(this, SecondActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
