package com.yuen.baselib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by dada53 on 15/8/14.
 */
public final class AppUtil {
    public static int getWidth(Context context) {
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        return screenWidth;
    }

    public static int getHeigth(Context context) {
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        return screenHeight;
    }

    /**
     * DP转化为PX
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * PX转化为DP
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
