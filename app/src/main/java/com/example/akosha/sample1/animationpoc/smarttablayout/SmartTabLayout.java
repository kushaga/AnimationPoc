package com.example.akosha.sample1.animationpoc.smarttablayout;

/**
 * Created by kushagarlall on 8/13/15.
 */


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.akosha.sample1.animationpoc.R;

/**
 * To be used with ViewPager to provide a tab indicator component which give constant feedback as
 * to
 * the user's scroll progress.
 * <p>
 * To use the component, simply add it to your view hierarchy. Then in your
 * {@link android.app.Activity} or {@link android.app.Fragment}, {@link
 * android.support.v4.app.Fragment} call
 * {@link #setViewPager(android.support.v4.view.ViewPager)} providing it the ViewPager this layout
 * is being used for.
 * <p>
 * The colors can be customized in two ways. The first and simplest is to provide an array of
 * colors
 * via {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)}. The
 * alternative is via the {@link TabColorizer} interface which provides you complete control over
 * which color is used for any individual position.
 * <p>
 * The views used as tabs can be customized by calling {@link #setCustomTabView(int, int)},
 * providing the layout ID of your custom layout.
 * <p>
 * Forked from Google Samples &gt; SlidingTabsBasic &gt;
 * <a href="https://developer.android.com/samples/SlidingTabsBasic/src/com.example.android.common/view/SlidingTabLayout.html">SlidingTabLayout</a>
 */
public class SmartTabLayout extends HorizontalScrollView {

    private static final boolean DEFAULT_DISTRIBUTE_EVENLY = false;
    private static final int TITLE_OFFSET_DIPS = 24;
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final boolean TAB_VIEW_TEXT_ALL_CAPS = true;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;
    private static final int TAB_VIEW_TEXT_COLOR = 0xFC000000;
    private static final int TAB_VIEW_TEXT_MIN_WIDTH = 0;
    private static final boolean TAB_CLICKABLE = true;

    protected final SmartTabStrip tabStrip;
    private int titleOffset;
    private int tabViewBackgroundResId;
    private boolean tabViewTextAllCaps;
    private ColorStateList tabViewTextColors;
    private float tabViewTextSize;
    private int tabViewTextHorizontalPadding;
    private int tabViewTextMinWidth;
    private ViewPager viewPager;
    private ViewPager.OnPageChangeListener viewPagerPageChangeListener;
    private OnScrollChangeListener onScrollChangeListener;
    private TabProvider tabProvider;
    private TabClickListener tabClickListener;
    private boolean distributeEvenly;
    public int mMode;
    private ColorStateList mTabTextColors;

    //todo check
    private int radiusMin =2, radiusMax=7;
    float headMoveOffset = 0.6f;
    float acceleration = 0.5f;
    float footMoveOffset = 1 - headMoveOffset;

    public void setRadiusMin(int radius) {
        this.radiusMin = radius;
    }

    public int getRadiusMin() {
        return this.radiusMin;
    }

    public SmartTabLayout(Context context) {
        this(context, null);
    }

    public SmartTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);

        final DisplayMetrics dm = getResources().getDisplayMetrics();
        final float density = dm.density;

        int tabBackgroundResId = NO_ID;
        boolean textAllCaps = TAB_VIEW_TEXT_ALL_CAPS;
        ColorStateList textColors;
        float textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP, dm);
        int textHorizontalPadding = (int) (TAB_VIEW_PADDING_DIPS * density);
        int textMinWidth = (int) (TAB_VIEW_TEXT_MIN_WIDTH * density);
        boolean distributeEvenly = DEFAULT_DISTRIBUTE_EVENLY;
        int customTabLayoutId = NO_ID;
        int customTabTextViewId = NO_ID;
        boolean clickable = TAB_CLICKABLE;

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.stl_SmartTabLayout, defStyle, 0);
        tabBackgroundResId = a.getResourceId(
                R.styleable.stl_SmartTabLayout_stl_defaultTabBackground, tabBackgroundResId);
        textAllCaps = a.getBoolean(
                R.styleable.stl_SmartTabLayout_stl_defaultTabTextAllCaps, textAllCaps);
        textColors = a.getColorStateList(
                R.styleable.stl_SmartTabLayout_stl_defaultTabTextColor);
        textSize = a.getDimension(
                R.styleable.stl_SmartTabLayout_stl_defaultTabTextSize, textSize);
        textHorizontalPadding = a.getDimensionPixelSize(
                R.styleable.stl_SmartTabLayout_stl_defaultTabTextHorizontalPadding, textHorizontalPadding);
        textMinWidth = a.getDimensionPixelSize(
                R.styleable.stl_SmartTabLayout_stl_defaultTabTextMinWidth, textMinWidth);
        customTabLayoutId = a.getResourceId(
                R.styleable.stl_SmartTabLayout_stl_customTabTextLayoutId, customTabLayoutId);
        customTabTextViewId = a.getResourceId(
                R.styleable.stl_SmartTabLayout_stl_customTabTextViewId, customTabTextViewId);
        distributeEvenly = a.getBoolean(
                R.styleable.stl_SmartTabLayout_stl_distributeEvenly, distributeEvenly);
        clickable = a.getBoolean(
                R.styleable.stl_SmartTabLayout_stl_clickable, clickable);
        a.recycle();


        this.titleOffset = (int) (TITLE_OFFSET_DIPS * density);
        this.tabViewBackgroundResId = tabBackgroundResId;
        this.tabViewTextAllCaps = textAllCaps;
        this.tabViewTextColors = (textColors != null)
                ? textColors
                : ColorStateList.valueOf(TAB_VIEW_TEXT_COLOR);
        this.tabViewTextSize = textSize;
        this.tabViewTextHorizontalPadding = textHorizontalPadding;
        this.tabViewTextMinWidth = textMinWidth;
        this.tabClickListener = clickable ? new TabClickListener() : null;
        this.distributeEvenly = distributeEvenly;

        if (customTabLayoutId != NO_ID) {
            setCustomTabView(customTabLayoutId, customTabTextViewId);
        }

        //distributeEvenly=true;
        this.tabStrip = new SmartTabStrip(context, attrs);

        if (distributeEvenly && tabStrip.isIndicatorAlwaysInCenter()) {
            throw new UnsupportedOperationException(
                    "'distributeEvenly' and 'indicatorAlwaysInCenter' both use does not support");
        }

        // Make sure that the Tab Strips fills this View
        setFillViewport(!tabStrip.isIndicatorAlwaysInCenter());

        this.applyModeAndGravity();

        //todo add spring view here

        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChanged(l, oldl);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (tabStrip.isIndicatorAlwaysInCenter() && getChildCount() > 0) {
            View firstTab = tabStrip.getChildAt(0);
            View lastTab = tabStrip.getChildAt(getChildCount() - 1);
            int start = (w - Utils.getMeasuredWidth(firstTab)) / 2 - Utils.getMarginStart(firstTab);
            int end = (w - Utils.getMeasuredWidth(lastTab)) / 2 - Utils.getMarginEnd(lastTab);
            tabStrip.setMinimumWidth(tabStrip.getMeasuredWidth());
            ViewCompat.setPaddingRelative(this, start, getPaddingTop(), end, getPaddingBottom());
            setClipToPadding(false);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // Ensure first scroll
        if (changed && viewPager != null) {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
        createPoints();
    }

    /**
     * Set the behavior of the Indicator scrolling feedback.
     *
     * @param interpolator {@link com.example.akosha.sample1.animationpoc.smarttablayout.SmartTabIndicationInterpolator}
     */
    public void setIndicationInterpolator(SmartTabIndicationInterpolator interpolator) {
        tabStrip.setIndicationInterpolator(interpolator);
    }

    /**
     * Set the custom {@link TabColorizer} to be used.
     * <p>
     * If you only require simple customisation then you can use
     * {@link #setSelectedIndicatorColors(int...)} and {@link #setDividerColors(int...)} to achieve
     * similar effects.
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        tabStrip.setCustomTabColorizer(tabColorizer);
    }

    /**
     * Set the color used for styling the tab text. This will need to be called prior to calling
     * {@link #setViewPager(android.support.v4.view.ViewPager)} otherwise it will not get set
     *
     * @param color to use for tab text
     */
    public void setDefaultTabTextColor(int color) {
        tabViewTextColors = ColorStateList.valueOf(color);
    }

    /**
     * Sets the colors used for styling the tab text. This will need to be called prior to calling
     * {@link #setViewPager(android.support.v4.view.ViewPager)} otherwise it will not get set
     *
     * @param colors ColorStateList to use for tab text
     */
    public void setDefaultTabTextColor(ColorStateList colors) {
        tabViewTextColors = colors;
    }

    /**
     * Set the same weight for tab
     */
    public void setDistributeEvenly(boolean distributeEvenly) {
        this.distributeEvenly = distributeEvenly;
    }

    /**
     * Sets the colors to be used for indicating the selected tab. These colors are treated as a
     * circular array. Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setSelectedIndicatorColors(int... colors) {
        tabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
     * Providing one color will mean that all tabs are indicated with the same color.
     */
    public void setDividerColors(int... colors) {
        tabStrip.setDividerColors(colors);
    }

    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SmartTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        viewPagerPageChangeListener = listener;
    }

    /**
     * Set {@link OnScrollChangeListener} for obtaining values of scrolling.
     *
     * @param listener the {@link OnScrollChangeListener} to set
     */
    public void setOnScrollChangeListener(OnScrollChangeListener listener) {
        onScrollChangeListener = listener;
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId  id of the {@link android.widget.TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        tabProvider = new SimpleTabProvider(getContext(), layoutResId, textViewId);
    }

    /**
     * Set the custom layout to be inflated for the tab views.
     *
     * @param provider {@link TabProvider}
     */
    public void setCustomTabView(TabProvider provider) {
        tabProvider = provider;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        tabStrip.removeAllViews();

        this.viewPager = viewPager;
        if (viewPager != null && viewPager.getAdapter() != null) {
            viewPager.setOnPageChangeListener(new InternalViewPagerListener());
            populateTabStrip();
        }
    }

    /**
     * Returns the view at the specified position in the tabs.
     *
     * @param position the position at which to get the view from
     * @return the view at the specified position or null if the position does not exist within the
     * tabs
     */
    public View getTabAt(int position) {
        return tabStrip.getChildAt(position);
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TabStripView createDefaultTabView(CharSequence title) {
        TextView textView = new TextView(getContext());
        textView.setGravity(Gravity.CENTER);
        textView.setText(title);
        textView.setTextColor(tabViewTextColors);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabViewTextSize);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        //textView.setTextColor(getResources().getColor(R.color.theme_accent));

        if (tabViewBackgroundResId != NO_ID) {
            textView.setBackgroundResource(tabViewBackgroundResId);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // If we're running on Honeycomb or newer, then we can use the Theme's
            // selectableItemBackground to ensure that the View has a pressed state
            TypedValue outValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                    outValue, true);
            textView.setBackgroundResource(outValue.resourceId);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // If we're running on ICS or newer, enable all-caps to match the Action Bar tab style
            textView.setAllCaps(tabViewTextAllCaps);
        }

        textView.setPadding(
                tabViewTextHorizontalPadding, 0,
                tabViewTextHorizontalPadding, 0);

        if (tabViewTextMinWidth > 0) {
            textView.setMinWidth(tabViewTextMinWidth);
        }

        textView.setTextColor(getResources().getColor(R.color.theme_default_primary_dark));
        TabStripView view = new TabStripView(getContext());
        view.attachTextView(textView);

        return view;
    }

    public void setTabTextColors(int... colors) {

        this.tabStrip.setSelectedIndicatorColors(colors);

        /*if(this.mTabTextColors != textColor) {
            this.mTabTextColors = textColor;
            this.updateAllTabs();
        }*/

    }

    private void updateAllTabs() {
        int i = 0;

        for (int z = this.tabStrip.getChildCount(); i < z; ++i) {
            this.updateTab(i);
        }

    }

    private void updateTab(int position) {
        View view = (View) this.tabStrip.getChildAt(position);
        if (view != null) {
            //view.update();
        }

    }

    private void populateTabStrip() {
        final PagerAdapter adapter = viewPager.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {

            final TabStripView tabView = (tabProvider == null)
                    ? createDefaultTabView(adapter.getPageTitle(i))
                    : (TabStripView) tabProvider.createTabView(tabStrip, i, adapter);
            if (tabView == null) {
                throw new IllegalStateException("tabView is null.");
            }

            if (distributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }

            if (tabClickListener != null) {
                tabView.setOnClickListener(tabClickListener);
            }

            tabStrip.addView(tabView);

            if (i == viewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }

        }
    }

    private void applyModeAndGravity() {
        int paddingStart = 0;
        if (this.mMode == 0) {
            //paddingStart = Math.max(0, this.mContentInsetStart - this.mTabPaddingStart);
        }

        ViewCompat.setPaddingRelative(this.tabStrip, paddingStart, 0, 0, 0);
        switch (this.mMode) {
            case 0:
                this.tabStrip.setGravity(8388611);
                break;
            case 1:
                this.tabStrip.setGravity(2);
        }

        this.updateTabViewsLayoutParams();
    }

    private void updateTabViewsLayoutParams() {
        for (int i = 0; i < this.tabStrip.getChildCount(); ++i) {
            View child = this.tabStrip.getChildAt(i);
            this.updateTabViewLayoutParams((LayoutParams) child.getLayoutParams());
            child.requestLayout();
        }

    }

    private void updateTabViewLayoutParams(LayoutParams lp) {
        if (this.mMode == 1) {
            lp.width = 0;
        } else {
            lp.width = -2;
        }

    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedTab = tabStrip.getChildAt(tabIndex);
        if (selectedTab == null) {
            return;
        }

        final boolean isLayoutRtl = Utils.isLayoutRtl(this);

        if (tabStrip.isIndicatorAlwaysInCenter()) {
            View firstTab = tabStrip.getChildAt(0);
            int x;
            if (isLayoutRtl) {
                int first = Utils.getWidth(firstTab) + Utils.getMarginEnd(firstTab);
                int selected = Utils.getWidth(selectedTab) + Utils.getMarginEnd(selectedTab);
                x = Utils.getEnd(selectedTab) - Utils.getMarginEnd(selectedTab) - positionOffset;
                x -= (first - selected) / 2;
            } else {
                int first = Utils.getWidth(firstTab) + Utils.getMarginStart(firstTab);
                int selected = Utils.getWidth(selectedTab) + Utils.getMarginStart(selectedTab);
                x = Utils.getStart(selectedTab) - Utils.getMarginStart(selectedTab) + positionOffset;
                x -= (first - selected) / 2;
            }

            scrollTo(x, 0);
            return;

        }

        int start = Utils.getStart(selectedTab);
        int startMargin = Utils.getMarginStart(selectedTab);
        int x = isLayoutRtl
                ? start + startMargin - positionOffset - getWidth() + Utils.getPaddingHorizontally(this)
                : start - startMargin + positionOffset;

        if (tabIndex > 0 || positionOffset > 0) {
            // If we're not at the first child and are mid-scroll, make sure we obey the offset
            if (isLayoutRtl) {
                x += titleOffset;
            } else {
                x -= titleOffset;
            }
        }

        scrollTo(x, 0);

    }

    /**
     * Allows complete control over the colors drawn in the tab layout. Set with
     * {@link #setCustomTabColorizer(TabColorizer)}.
     */
    public interface TabColorizer {

        /**
         * @return return the color of the indicator used when {@code position} is selected.
         */
        int getIndicatorColor(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        int getDividerColor(int position);

    }

    /**
     * Interface definition for a callback to be invoked when the scroll position of a view changes.
     */
    public interface OnScrollChangeListener {

        /**
         * Called when the scroll position of a view changes.
         *
         * @param scrollX    Current horizontal scroll origin.
         * @param oldScrollX Previous horizontal scroll origin.
         */
        void onScrollChanged(int scrollX, int oldScrollX);
    }

    /**
     * Create the custom tabs in the tab layout. Set with
     * {@link #setCustomTabView(com.example.akosha.sample1.animationpoc.smarttablayout.SmartTabLayout.TabProvider)}
     */
    public interface TabProvider {

        /**
         * @return Return the View of {@code position} for the Tabs
         */
        View createTabView(ViewGroup container, int position, PagerAdapter adapter);

    }

    private static class SimpleTabProvider implements TabProvider {

        private final LayoutInflater inflater;
        private final int tabViewLayoutId;
        private final int tabViewTextViewId;

        private SimpleTabProvider(Context context, int layoutResId, int textViewId) {
            inflater = LayoutInflater.from(context);
            tabViewLayoutId = layoutResId;
            tabViewTextViewId = textViewId;
        }

        @Override
        public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
            View tabView = null;
            TextView tabTitleView = null;

            if (tabViewLayoutId != NO_ID) {
                tabView = inflater.inflate(tabViewLayoutId, container, false);
            }

            if (tabViewTextViewId != NO_ID && tabView != null) {
                tabTitleView = (TextView) tabView.findViewById(tabViewTextViewId);
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (tabTitleView != null) {
                tabTitleView.setText(adapter.getPageTitle(position));
            }


            return tabView;
        }

    }

    private void createPoints() {
        View view = tabStrip.getChildAt(viewPager.getCurrentItem());
        tabStrip.getHeadPoint().setX(view.getX() + view.getWidth() / 2);
        tabStrip.getHeadPoint().setY(view.getY() + view.getHeight() / 2);
        tabStrip.getFootPoint().setX(view.getX() + view.getWidth() / 2);
        tabStrip.getFootPoint().setY(view.getY() + view.getHeight() / 2);

    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {

        private int scrollState;

        public void makeScrollPath(int position, float positionOffset, int positionOffsetPixels) {
            //todo set radius max and radius min , and intializa in the tabstrip
            if (position < tabStrip.getChildCount() - 1) {
                int radiusOffset = radiusMax - radiusMin;
                float radiusOffsetHead = 0.5f;
                if (positionOffset < radiusOffsetHead) {
                    tabStrip.getHeadPoint().setRadius(getRadiusMin());
                } else {
                    tabStrip.getHeadPoint().setRadius(((positionOffset - radiusOffsetHead) / (1 - radiusOffsetHead) * radiusOffset + radiusMin));
                }
                float radiusOffsetFoot = 0.5f;
                if (positionOffset < radiusOffsetFoot) {
                    tabStrip.getFootPoint().setRadius((1 - positionOffset / radiusOffsetFoot) * radiusOffset + radiusMin);
                } else {
                    tabStrip.getFootPoint().setRadius(radiusMin);
                }

                // x
                float headX = 1f;

                if (positionOffset < headMoveOffset) {
                    float positionOffsetTemp = positionOffset / headMoveOffset;
                    headX = (float) ((Math.atan(positionOffsetTemp * acceleration * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                }
                tabStrip.getHeadPoint().setX(getTabX(position) - headX * getPositionDistance(position));


                tabStrip.getHeadPoint().setX(position);
                float footX = 0f;
                if (positionOffset > footMoveOffset) {
                    float positionOffsetTemp = (positionOffset - footMoveOffset) / (1 - footMoveOffset);
                    footX = (float) ((Math.atan(positionOffsetTemp * acceleration * 2 - acceleration) + (Math.atan(acceleration))) / (2 * (Math.atan(acceleration))));
                }

                tabStrip.getFootPoint().setX(getTabX(position) - footX * getPositionDistance(position));

                // reset radius
                if (positionOffset == 0) {
                    tabStrip.getHeadPoint().setRadius(radiusMax);
                    tabStrip.getFootPoint().setRadius(radiusMax);
                }
            } else {
                tabStrip.getHeadPoint().setX(getTabX(position));
                tabStrip.getFootPoint().setX(getTabX(position));
                tabStrip.getHeadPoint().setRadius(radiusMax);
                tabStrip.getFootPoint().setRadius(radiusMax);
            }
            tabStrip.animCreate();
        }

        private float getTabX(int position) {
            return tabStrip.getChildAt(position).getX() + tabStrip.getChildAt(position).getWidth() / 2;
        }

        private float getPositionDistance(int position) {
            float tarX = tabStrip.getChildAt(position + 1).getX();
            float oriX = tabStrip.getChildAt(position).getX();
            return oriX - tarX;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = tabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            tabStrip.onViewPagerPageChanged(position, positionOffset);


            View selectedTab = tabStrip.getChildAt(position);
            int widthPlusMargin = Utils.getWidth(selectedTab) + Utils.getMarginHorizontally(selectedTab);
            int extraOffset = (int) (positionOffset * widthPlusMargin);

            if (0f < positionOffset && positionOffset < 1f && tabStrip.isIndicatorAlwaysInCenter()) {
                View currentTab = tabStrip.getChildAt(position);
                View nextTab = tabStrip.getChildAt(position + 1);
                int current = Utils.getWidth(currentTab) / 2 + Utils.getMarginEnd(currentTab);
                int next = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab);
                extraOffset = Math.round(positionOffset * (current + next));
            }

            scrollToTab(position, extraOffset);

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            onStripColorChange(position);
            makeScrollPath(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            scrollState = state;

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            if (scrollState == ViewPager.SCROLL_STATE_IDLE) {
                tabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }

            for (int i = 0, size = tabStrip.getChildCount(); i < size; i++) {
                tabStrip.getChildAt(i).setSelected(position == i);
            }

            if (viewPagerPageChangeListener != null) {
                viewPagerPageChangeListener.onPageSelected(position);
            }

            onStripColorChange(position);
        }

    }

    private void onStripColorChange(int pos) {
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            TabStripView view = (TabStripView) tabStrip.getChildAt(i);
            if (i == pos) {
                view.textView.setTextColor(getResources().getColor(R.color.theme_accent));
                continue;
            }
            view.textView.setTextColor(getResources().getColor(R.color.theme_default_primary_dark));
        }
    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (v == tabStrip.getChildAt(i)) {
                    TabStripView view = (TabStripView) v;
                    view.textView.setTextColor(getResources().getColor(R.color.theme_accent));
                    viewPager.setCurrentItem(i);
                    continue;
                }
                TabStripView view = (TabStripView) tabStrip.getChildAt(i);
                view.textView.setTextColor(getResources().getColor(R.color.theme_default_primary_dark));
            }
        }
    }
}

