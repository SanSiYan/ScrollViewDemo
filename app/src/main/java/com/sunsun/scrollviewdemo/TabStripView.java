package com.sunsun.scrollviewdemo;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class TabStripView extends FrameLayout implements OnClickListener {

    private float density;

    private Context context;
    private int selectedIndex;
    private OnTabStripSwitchListener onSwitchListener;
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout tabLayout;
    private FrameLayout tabStripLayout;
    private ImageView stripView;
    private List<TextView> tabTextViews = new ArrayList<TextView>();
    private String[] tabs = {getResources().getString(R.string.app_name), // tab
            getResources().getString(R.string.classify)};
    private int tabStripViewColor = getResources().getColor(R.color.white);
    private int[] tabTextColors = {
            getResources().getColor(R.color.tab_text_normal_color), getResources().getColor(R.color.tab_text_color)}; // 文字颜色数组:
    // [选中颜色，非选中颜色]

    private int stripColor = R.color.tab_text_normal_color;
    private int stripHeight = 2;

    private Runnable mTabSelector;

    public TabStripView(Context context) {
        this(context, null);
    }

    public TabStripView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getViewTreeObserver()
                .addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        initView();
    }

    public TabStripView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getViewTreeObserver()
                .addOnGlobalLayoutListener(mOnGlobalLayoutListener);
        initView();
    }

    public void initView() {
        initTabView();
        initGapLine();
    }

    private void initTabView() {

        density = context.getResources().getDisplayMetrics().density;
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                (int) (50 * density), Gravity.TOP);

        horizontalScrollView = new HorizontalScrollView(context);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        LayoutParams childParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);

        tabStripLayout = new FrameLayout(context);

        tabLayout = new LinearLayout(context);
        tabLayout.setGravity(Gravity.CENTER_VERTICAL);
        tabLayout
                .setLayoutParams(new android.widget.RelativeLayout.LayoutParams(
                        android.widget.RelativeLayout.LayoutParams.MATCH_PARENT,
                        android.widget.RelativeLayout.LayoutParams.MATCH_PARENT));
        tabLayout.setBackgroundColor(tabStripViewColor);
        tabStripLayout.addView(tabLayout, childParams);
        horizontalScrollView.addView(tabStripLayout, childParams);
        this.addView(horizontalScrollView, params);
    }

    /**
     * 添加tab
     */
    public void addTabView() {
        if (tabs == null || tabs.length <= 0) {
            setVisibility(View.GONE);
            return;
        }
        tabLayout.removeAllViews();
        tabTextViews.clear();
        ColorStateList colorStateList = createColorStateList(tabTextColors);
        for (int i = 0; i < tabs.length; i++) {

            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            TextView tabTextView = new TextView(context);
            tabTextView.setText(tabs[i]);
            tabTextView.setTextColor(colorStateList);
            tabTextView.setTextSize(15);
            tabTextView.setPadding((int) (15 * density), 0,
                    (int) (15 * density), 0);
            tabTextView.setGravity(Gravity.CENTER);
            tabTextView.setIncludeFontPadding(false);
            tabTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            tabTextView.setCompoundDrawablePadding((int) (5 * density));
            tabTextView.setClickable(true);
            tabTextView.setOnClickListener(this);
            tabTextView.setTag(i);
            tabTextViews.add(tabTextView);
            tabLayout.addView(tabTextView, params1);
        }
        addStripView();
    }

    /**
     * 生成TabStripView tab 文本的颜色
     *
     * @param tabTextColors
     * @return ColorStateList
     */
    private ColorStateList createColorStateList(int[] tabTextColors) {
        int[] colors = new int[]{tabTextColors[0], tabTextColors[1]};
        int[][] states = new int[2][];
        states[0] = new int[]{android.R.attr.state_selected};
        states[1] = new int[]{};
        ColorStateList colorList = new ColorStateList(states, colors);
        return colorList;
    }

    /**
     * 分页指示器 view
     */
    private void addStripView() {
        LayoutParams params = new LayoutParams(tabLayout.getChildAt(
                selectedIndex).getWidth(), (int) (stripHeight * density),
                Gravity.BOTTOM);
        stripView = new ImageView(context);
        stripView.setImageResource(stripColor);

        float offset = tabLayout.getChildAt(selectedIndex).getWidth();// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        stripView.setImageMatrix(matrix);// 设置动画初始位置
        tabStripLayout.addView(stripView, params);
    }

    /**
     * 底部的分割线
     */
    private void initGapLine() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1,
                Gravity.BOTTOM);

        View line = new View(context);
        line.setBackgroundColor(getResources().getColor(R.color.gap_line_bg));
        tabStripLayout.addView(line, params);
    }

    /**
     * 移动分页指示器
     *
     * @param selectedIndex
     */
    private void animateStrip(int selectedIndex) {

        Animation animation = new TranslateAnimation(tabLayout.getChildAt(
                this.selectedIndex).getLeft(), tabLayout.getChildAt(
                selectedIndex).getLeft(), 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(250);
        updateStripViewWidth(selectedIndex);
        stripView.startAnimation(animation);
    }

    private void updateStripViewWidth(int selectedIndex) {
        if (stripView == null) {
            return;
        }
        LayoutParams params = (LayoutParams) stripView.getLayoutParams();
        params.width = tabLayout.getChildAt(selectedIndex).getWidth();
        stripView.setLayoutParams(params);
    }

    /**
     * 移动tab居中
     *
     * @param position
     */
    private void animateToTab(final int position) {
        final View tabView = tabLayout.getChildAt(position);
        if (mTabSelector != null) {
            removeCallbacks(mTabSelector);
        }
        mTabSelector = new Runnable() {
            public void run() {
                final int scrollPos = tabView.getLeft()
                        - (getWidth() - tabView.getWidth()) / 2;
                horizontalScrollView.smoothScrollTo(scrollPos, 0);
                mTabSelector = null;
            }
        };
        post(mTabSelector);
    }

    /**
     * 设置选中item
     *
     * @param selectedIndex
     */
    public void setSelectedIndex(int selectedIndex) {
        if (selectedIndex >= tabs.length) {
            return;
        }
        if (selectedIndex != TabStripView.this.selectedIndex) {
            View preItem = tabLayout.getChildAt(this.selectedIndex);
            preItem.setSelected(false);
            animateStrip(selectedIndex);
            animateToTab(selectedIndex);
            this.selectedIndex = selectedIndex;
        }

        View selectedItem = tabLayout.getChildAt(this.selectedIndex);
        selectedItem.setSelected(true);
    }

    @Override
    public void onClick(View view) {
        int selectedIndex = (Integer) view.getTag();
        if (selectedIndex != TabStripView.this.selectedIndex) {
            setSelectedIndex(selectedIndex);
            if (onSwitchListener != null) {
                onSwitchListener.doSwitch(selectedIndex);
            }
        }
    }

    private boolean equalWeight = true;

    public boolean getEqualWeight() {
        return equalWeight;
    }

    public void setEqualWeight(boolean equalWeight) {
        this.equalWeight = equalWeight;
    }

    private OnGlobalLayoutListener mOnGlobalLayoutListener = new OnGlobalLayoutListener() {

        @Override
        public void onGlobalLayout() {
            if (equalWeight) {
                checkEqualWeight();
            }
        }
    };

    /**
     * 如果child views 的总宽度比parent view 的宽度小，则把child views总宽度设成parent view
     * 的宽度，然后平分每个child view的宽度
     */
    private void checkEqualWeight() {
        int width = tabLayout.getWidth();
        View parent = (View) tabLayout.getParent().getParent();
        int parentWidth = parent.getWidth();
        if (width < parentWidth) {
            tabLayout.getLayoutParams().width = parentWidth;
            int childCount = tabLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = tabLayout.getChildAt(i);

                LinearLayout.LayoutParams childParams = (LinearLayout.LayoutParams) child
                        .getLayoutParams();
                childParams.weight = 1;
            }
        }
        updateStripViewWidth(selectedIndex);
    }

    public interface OnTabStripSwitchListener {
        public void doSwitch(int selectedIndex);
    }

    public OnTabStripSwitchListener getOnSwitchListener() {
        return onSwitchListener;
    }

    public void setOnSwitchListener(OnTabStripSwitchListener onSwitchListener) {
        this.onSwitchListener = onSwitchListener;
    }

    public String[] getTabs() {
        return tabs;
    }

    public void setTabs(String[] tabs) {
        this.tabs = tabs;
        addTabView();
    }

    public void changeTabs(String[] tabs) {
        this.tabs = tabs;
        for (int i = 0; i < tabs.length; i++) {
            tabTextViews.get(i).setText(tabs[i]);
        }
    }

    public void setTabText(int index, String text) {
        tabTextViews.get(index).setText(text);
    }

    public int[] getTabTextColors() {
        return tabTextColors;
    }

    public void setTabTextColors(int[] tabTextColors) {
        this.tabTextColors = tabTextColors;
        addTabView();
    }

    public int getTabStripViewColor() {
        return tabStripViewColor;
    }

    public void setTabStripViewColor(int tabStripViewColor) {
        this.tabStripViewColor = tabStripViewColor;
        tabLayout.setBackgroundColor(tabStripViewColor);
    }

    public int getStripColor() {
        return stripColor;
    }

    public void setStripColor(int stripColor) {
        this.stripColor = stripColor;
        stripView.setImageResource(stripColor);
    }

    public int getStripHeight() {
        return stripHeight;
    }

    public void setStripHeight(int stripHeight) {
        this.stripHeight = stripHeight;
        LayoutParams params = new LayoutParams((int) CommonUtil.getWidthPixels(getContext()) / tabs.length,
                (int) (stripHeight * density), Gravity.BOTTOM);
        stripView.setLayoutParams(params);
    }

}
