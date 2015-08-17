package com.example.akosha.sample1.animationpoc;

/**
 * Created by kushagarlall on 8/11/15.
 */
public interface TabClickListener {

    /**
     * Handle click event when tab is clicked.
     * @param position ViewPager item position.
     * @return Call setCurrentItem if return true.
     */
    public boolean onTabClick(int position);

}
