package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/13/15.
 */
import android.os.Build.VERSION;
import android.view.View;

import com.example.akosha.sample1.animationpoc.tablayout.ValueAnimatorCompat.Creator;
import com.example.akosha.sample1.animationpoc.tablayout.ValueAnimatorCompat.Impl;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR = new Creator() {
        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat((Impl)(VERSION.SDK_INT >= 12?new ValueAnimatorCompatImplHoneycombMr1():new ValueAnimatorCompatImplEclairMr1()));
        }
    };
    private static final ViewUtils.ViewUtilsImpl IMPL;

    ViewUtils() {
    }

    static void setBoundsViewOutlineProvider(View view) {
        IMPL.setBoundsViewOutlineProvider(view);
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static {
        int version = VERSION.SDK_INT;
        if(version >= 21) {
            IMPL = new ViewUtils.ViewUtilsImplLollipop(null);
        } else {
            IMPL = new ViewUtils.ViewUtilsImplBase(null);
        }

    }

    private static class ViewUtilsImplLollipop implements ViewUtils.ViewUtilsImpl {
        private ViewUtilsImplLollipop(Object o) {
        }

        public void setBoundsViewOutlineProvider(View view) {
            ViewUtilsLollipop.setBoundsViewOutlineProvider(view);
        }
    }

    private static class ViewUtilsImplBase implements ViewUtils.ViewUtilsImpl {
        private ViewUtilsImplBase(Object o) {
        }

        public void setBoundsViewOutlineProvider(View view) {
        }
    }

    private interface ViewUtilsImpl {
        void setBoundsViewOutlineProvider(View var1);
    }
}

