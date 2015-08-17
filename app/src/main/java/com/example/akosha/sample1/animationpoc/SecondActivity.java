package com.example.akosha.sample1.animationpoc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView mLabelText;
    private Button button1;
    private Fade mFade;
    private ViewGroup mRootView;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mLabelText = new TextView(this);
        mLabelText.setText("Label Text");
        mRootView = (ViewGroup) findViewById(R.id.scndLayout);
        mFade = new Fade(Fade.OUT);

        button1 = new Button(this);
        button1.setText("On fly button");

        //View myView = findViewById(R.id.screenthree);

        final View animView = (View) findViewById(R.id.screenthree);

        Button button = (Button) findViewById(R.id.clickAddtext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransitionManager.beginDelayedTransition(mRootView, mFade);
                mRootView.addView(button1);
                //change the layout accordingly , the added button comes on the top
                //// TODO: add another activity with a view Pager
                Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left);
                anim.setDuration(5000);
                // v.startAnimation(anim);
                //mRootView.startAnimation(anim);
//                animView.startAnimation(anim);
                startActivity(new Intent(getApplicationContext(), Screenthree.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_second, menu);
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
