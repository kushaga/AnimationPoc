package com.example.akosha.sample1.animationpoc;

import android.annotation.TargetApi;
import android.os.Build;
import android.transition.Transition;
import android.transition.TransitionValues;

/**
 * Created by kushagarlall on 8/10/15.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyCustomTransition extends Transition {

    @Override
    public void captureStartValues(TransitionValues transitionValues) {

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {

    }
}
