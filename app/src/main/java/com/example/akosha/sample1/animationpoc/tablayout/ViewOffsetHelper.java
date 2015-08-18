package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/18/15.
 */
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.view.View;

class ViewOffsetHelper {
    private final View mView;
    private int mLayoutTop;
    private int mLayoutLeft;
    private int mOffsetTop;
    private int mOffsetLeft;

    public ViewOffsetHelper(View view) {
        this.mView = view;
    }

    public void onViewLayout() {
        this.mLayoutTop = this.mView.getTop();
        this.mLayoutLeft = this.mView.getLeft();
        this.updateOffsets();
    }

    private void updateOffsets() {
        if(VERSION.SDK_INT == 22) {
            ViewCompat.setTranslationY(this.mView, (float)this.mOffsetTop);
            ViewCompat.setTranslationX(this.mView, (float)this.mOffsetLeft);
        } else {
            ViewCompat.offsetTopAndBottom(this.mView, this.mOffsetTop - this.mView.getTop() - this.mLayoutTop);
            ViewCompat.offsetLeftAndRight(this.mView, this.mOffsetLeft - this.mView.getLeft() - this.mLayoutLeft);
        }

    }

    public boolean setTopAndBottomOffset(int offset) {
        if(this.mOffsetTop != offset) {
            this.mOffsetTop = offset;
            this.updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public boolean setLeftAndRightOffset(int offset) {
        if(this.mOffsetLeft != offset) {
            this.mOffsetLeft = offset;
            this.updateOffsets();
            return true;
        } else {
            return false;
        }
    }

    public int getTopAndBottomOffset() {
        return this.mOffsetTop;
    }

    public int getLeftAndRightOffset() {
        return this.mOffsetLeft;
    }
}
