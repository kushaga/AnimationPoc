package com.example.akosha.sample1.animationpoc.tablayout;

/**
 * Created by kushagarlall on 8/18/15.
 */
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

class ViewGroupUtils {
    private static final ViewGroupUtils.ViewGroupUtilsImpl IMPL;

    ViewGroupUtils() {
    }

    static void offsetDescendantRect(ViewGroup parent, View descendant, Rect rect) {
        IMPL.offsetDescendantRect(parent, descendant, rect);
    }

    static void getDescendantRect(ViewGroup parent, View descendant, Rect out) {
        out.set(0, 0, descendant.getWidth(), descendant.getHeight());
        offsetDescendantRect(parent, descendant, out);
    }

    static {
        int version = VERSION.SDK_INT;
        if(version >= 11) {
            IMPL = new ViewGroupUtils.ViewGroupUtilsImplHoneycomb();
        } else {
            IMPL = new ViewGroupUtils.ViewGroupUtilsImplBase();
        }

    }

    private static class ViewGroupUtilsImplHoneycomb implements ViewGroupUtils.ViewGroupUtilsImpl {
        private ViewGroupUtilsImplHoneycomb() {
        }

        public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
            ViewGroupUtilsHoneycomb.offsetDescendantRect(parent, child, rect);
        }
    }

    private static class ViewGroupUtilsImplBase implements ViewGroupUtils.ViewGroupUtilsImpl {
        private ViewGroupUtilsImplBase() {
        }

        public void offsetDescendantRect(ViewGroup parent, View child, Rect rect) {
            parent.offsetDescendantRectToMyCoords(child, rect);
        }
    }

    private interface ViewGroupUtilsImpl {
        void offsetDescendantRect(ViewGroup var1, View var2, Rect var3);
    }
}
