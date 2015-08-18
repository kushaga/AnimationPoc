package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/18/15.
 */
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.view.View;

class CoordinatorLayoutInsetsHelperLollipop implements CoordinatorLayoutInsetsHelper {
    CoordinatorLayoutInsetsHelperLollipop() {
    }

    public void setupForWindowInsets(View view, OnApplyWindowInsetsListener insetsListener) {
        if(ViewCompat.getFitsSystemWindows(view)) {
            ViewCompat.setOnApplyWindowInsetsListener(view, insetsListener);
            view.setSystemUiVisibility(1280);
        }

    }
}
