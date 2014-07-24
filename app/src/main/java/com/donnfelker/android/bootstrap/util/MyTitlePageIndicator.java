package com.donnfelker.android.bootstrap.util;

import com.viewpagerindicator.TitlePageIndicator;

import android.content.Context;
import android.util.AttributeSet;


/**
 * Created by Feather on 14-7-24.
 */
public class MyTitlePageIndicator extends TitlePageIndicator {

    public MyTitlePageIndicator(Context context) {
        super(context);
    }

    public MyTitlePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // block touch event 屏蔽触摸事件
    @Override
    public boolean onTouchEvent(android.view.MotionEvent ev) {
        return true;
    }
}
