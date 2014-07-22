package com.donnfelker.android.bootstrap.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.donnfelker.android.bootstrap.R;

/**
 * Created by Feather on 14-7-22.
 */
public class MyViewPager extends ViewPager {

        private boolean scroll;

        public MyViewPager(Context context) {
            super(context);
        }

        public MyViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager);
            scroll = a.getBoolean(R.styleable.MyViewPager_scroll, false);
        }

        public void setScroll(boolean scroll) {
            this.scroll = scroll;
        }

        @Override
        public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return scroll && super.onTouchEvent(motionEvent);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return scroll && super.onInterceptTouchEvent(motionEvent);
        }

        @Override
        public void setCurrentItem(int item, boolean smoothScroll) {
            super.setCurrentItem(item, smoothScroll);
        }

        @Override
        public void setCurrentItem(int item) {
            super.setCurrentItem(item);
        }
}
