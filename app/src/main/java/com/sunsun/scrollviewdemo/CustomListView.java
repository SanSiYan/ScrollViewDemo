package com.sunsun.scrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by sun on 2016/12/17.
 */
public class CustomListView extends ListView {

    public static final String TAG = "CustomListView";

    public static final int SIZE = 0;
    private int height;

    public CustomListView(Context context) {
        super(context);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        height = (int) CommonUtil.getHeightPixels(getContext()) - PxToDip.dip2px(getContext(), SIZE);
        Log.d(TAG, "CustomListView-- height = " + height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_DOWN");

                break;

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_MOVE");

                break;

            case MotionEvent.ACTION_UP:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_UP");

                break;

            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_CANCEL");

                break;

        }
        boolean b = super.dispatchTouchEvent(ev);
        //boolean b = true;
        Log.v(TAG, "CustomListView dispatchTouchEvent [" + action + "] test.................return:" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_DOWN");

                break;

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_MOVE");

                break;

            case MotionEvent.ACTION_UP:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_UP");

                break;

            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_CANCEL");

                break;

        }
        boolean b = super.onInterceptTouchEvent(ev);
        //boolean b = true;
        Log.v(TAG, "CustomListView dispatchTouchEvent [" + action + "] test.................return:" + b);
        return b;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_DOWN");

                break;

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_MOVE");

                break;

            case MotionEvent.ACTION_UP:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_UP");

                break;

            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "CustomListView-- dispatchTouchEvent action:ACTION_CANCEL");

                break;

        }
        boolean b = super.onTouchEvent(ev);
        //boolean b = true;
        Log.v(TAG, "CustomListView dispatchTouchEvent [" + action + "] test.................return:" + b);
        return b;
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (height > 0) {
//            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
//                    MeasureSpec.EXACTLY);
//        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
