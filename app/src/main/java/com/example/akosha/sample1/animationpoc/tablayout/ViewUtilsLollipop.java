package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/13/15.
 */
import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.ViewOutlineProvider;

class ViewUtilsLollipop {
    ViewUtilsLollipop() {
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    static void setBoundsViewOutlineProvider(View view) {
        view.setOutlineProvider(ViewOutlineProvider.BOUNDS);
    }
}