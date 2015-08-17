package com.example.akosha.sample1.animationpoc.smarttablayout;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kushagarlall on 8/14/15.
 */
public class TabStripView extends LinearLayout {

    public TextView textView ;

    public TabStripView(Context context) {
        super(context);
    }

    public TabStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabStripView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public TabStripView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void attachTextView(TextView textView){

        this.textView = textView;
        addView(this.textView);
    }

}
