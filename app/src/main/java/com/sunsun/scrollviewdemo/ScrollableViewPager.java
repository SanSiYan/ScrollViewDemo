package com.sunsun.scrollviewdemo;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class ScrollableViewPager extends ViewPager {

    public static final String TAG = "ScrollableViewPager";
    public static final int SIZE = 50;
    private int height;


    public ScrollableViewPager(Context context) {
        super(context);
        init();
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        // height = getRootView().findViewById(android.R.id.content).getHeight();
        // height = (int) CommonUtil.getHeightPixels(getContext()) - PxToDip.dip2px(getContext(), SIZE);
        // Log.d(TAG, "CustomListView-- height = " + height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (height <= 0) {
            height = getRootView().findViewById(android.R.id.content).getHeight() - PxToDip.dip2px(getContext(), SIZE);
            Log.d(TAG, "CustomListView-- height = " + height);
        }
        if (height > 0) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
