package com.donnfelker.android.bootstrap.util;

import android.content.Context;
import android.content.res.Configuration;

public class UIUtils {

    /**
     * Helps determine if the app is running in a Tablet context.
     *
     * @param context
     * @return
     */
    public static boolean isTablet(Context context) {
        /** warning:
         * if use this feature to detect whether the device is a tablet,
         * some abnormal views appear. So I decide block this feature,
         * and all device will use the same layout, that is mobile phone layout.
         *
         * 如果使用了isTable()这个函数来判断是否是在平板设备上使用该应用程序，
         * 则会在平板设备上显示出专为平板设备设计的界面布局。
         * 但是这个函数的判断方式太简陋了，使得现在使用的小平板(T705)设备会显示“平板布局”
         * 从而出现内容的错乱。
         * 所以我令这个函数强制返回false，使得所有设备上都使用同一种布局（手机布局）。
        */
        /*
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
                */
        return false;
    }
}
