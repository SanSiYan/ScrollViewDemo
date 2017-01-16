package com.sunsun.scrollviewdemo;

import android.content.Context;

/**
 * Created by dexian on 2016/5/5.
 */
public class CommonUtil {


    /**
     * get density
     *
     * @param context
     * @return
     */
    public static float getDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * get scale density
     *
     * @param context
     * @return
     */
    public static float getScaleDensity(Context context) {
        return context.getResources().getDisplayMetrics().scaledDensity;
    }

    /**
     * get width pixels
     *
     * @param context
     * @return
     */
    public static float getWidthPixels(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * get height pixels
     *
     * @param context
     * @return
     */
    public static float getHeightPixels(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

}
