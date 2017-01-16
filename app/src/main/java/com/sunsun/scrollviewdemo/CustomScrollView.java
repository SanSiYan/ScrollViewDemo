package com.sunsun.scrollviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by sun on 2016/12/17.
 */
public class CustomScrollView extends ScrollView {

    public static final String TAG1 = "CustomScrollView1";

    public static final String TAG = "CustomScrollView";
    public static final int SIZE = 200;
    private int topHeight;
    private boolean isIntercept = true;
    private ISEXPAND isexpand = ISEXPAND.EXPAND;
    private CustomScrollViewListener mScrollableListener;

    private enum ISEXPAND {
        EXPAND, MIDDLE, THIINK
    }

    private float lastY;
    private float lastX;

    public CustomScrollView(Context context) {
        super(context);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        topHeight = PxToDip.dip2px(getContext(), SIZE);
        Log.d(TAG, "CustomScrollView-- topHeight = " + topHeight);
    }

    public void setScrollableListener(CustomScrollViewListener mScrollableListener) {
        this.mScrollableListener = mScrollableListener;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        float x = ev.getX();
        float y = ev.getY();
        Log.d(TAG, "dispatchTouchEvent x = " + x + "  y = " + y + " getScrollY() = " + getScrollY());
        if (getScrollY() <= 0) {
            isexpand = ISEXPAND.EXPAND;
        } else if (getScrollY() >= topHeight) {
            isexpand = ISEXPAND.THIINK;
        } else {
            isexpand = ISEXPAND.MIDDLE;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_DOWN");
                lastY = y;
                lastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_MOVE");
                float dy = Math.abs(y - lastY);
                float dx = Math.abs(x - lastX);
                if (dy >= dx) {
                    if (y >= lastY) {//向下滑动
                        if (isexpand == ISEXPAND.EXPAND) {
                            //下拉刷新
                            if (!isIntercept) {
                                isIntercept = true;
                            }
                        } else if (isexpand == ISEXPAND.MIDDLE) {
                            //滑动scrollview
                            if (!isIntercept) {
                                ev.setAction(MotionEvent.ACTION_DOWN);
                                isIntercept = true;
                            }
                        } else if (isexpand == ISEXPAND.THIINK) {
                            if (mScrollableListener != null && mScrollableListener.isSlidingTop(ev)) {
                                if (!isIntercept) {
                                    ev.setAction(MotionEvent.ACTION_DOWN);
                                    isIntercept = true;
                                }
                            } else {
                                if (isIntercept) {
                                    ev.setAction(MotionEvent.ACTION_DOWN);
                                    isIntercept = false;
                                }
                            }
                        }
                    } else if (y < lastY) {//向上滑动
                        if (isexpand == ISEXPAND.EXPAND) {
                            //滑动scrollview
                            if (!isIntercept) {
                                ev.setAction(MotionEvent.ACTION_DOWN);
                                isIntercept = true;
                            }
                        } else if (isexpand == ISEXPAND.MIDDLE) {
                            //滑动scrollview
                            if (!isIntercept) {
                                ev.setAction(MotionEvent.ACTION_DOWN);
                                isIntercept = true;
                            }
                        } else if (isexpand == ISEXPAND.THIINK) {
                            //滑动ListView
                            if (isIntercept) {
                                ev.setAction(MotionEvent.ACTION_DOWN);
                                isIntercept = false;
                            }
                        }
                    }
                } else {
                    isIntercept = false;
                }
                lastY = y;
                lastX = x;
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_UP");
                isIntercept = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_CANCEL");
                isIntercept = true;
                break;
        }
        boolean b = super.dispatchTouchEvent(ev);
        //boolean b = true;
        Log.v(TAG, "CustomScrollView dispatchTouchEvent [" + action + "] test.................return:" + b);
        return b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_DOWN");

                return super.onInterceptTouchEvent(ev);

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_MOVE");
                super.onInterceptTouchEvent(ev);
                if (isIntercept) {
                    return true;
                } else {
                    return false;
                }
            case MotionEvent.ACTION_UP:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_UP");

                return super.onInterceptTouchEvent(ev);

            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_CANCEL");

                return super.onInterceptTouchEvent(ev);

        }
        Log.v(TAG, "CustomScrollView dispatchTouchEvent [" + action + "] test.................return:");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_DOWN");

                break;

            case MotionEvent.ACTION_MOVE:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_MOVE");

                break;

            case MotionEvent.ACTION_UP:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_UP");

                break;

            case MotionEvent.ACTION_CANCEL:

                Log.d(TAG, "CustomScrollView-- dispatchTouchEvent action:ACTION_CANCEL");

                break;

        }
        boolean b = super.onTouchEvent(ev);
        //boolean b = true;
        Log.v(TAG, "CustomScrollView dispatchTouchEvent [" + action + "] test.................return:" + b);
        return b;
    }

    public interface CustomScrollViewListener{
        public boolean isSlidingTop(MotionEvent ev);
    }


}
