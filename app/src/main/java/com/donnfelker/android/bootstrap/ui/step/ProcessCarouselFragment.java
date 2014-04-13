package com.donnfelker.android.bootstrap.ui.step;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by Feather on 14-4-2.
 * 巡检流程的走马灯框架
 */
public class ProcessCarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)
    protected TitlePageIndicator indicator;

    @InjectView(R.id.vp_pages)
    protected ViewPager pager;

    protected InspectPagerAdapter pagerAdapter;

    protected ValidationFragment currentFragment;

    protected MyOnTouchListener myOnTouchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_process, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        myOnTouchListener = new MyOnTouchListener();
        Views.inject(this, getView());
        pagerAdapter = new InspectPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        // pager.setOnPageChangeListener() need to be above to indicator.setViewPager(pager)
        // pager.setOnPageChangeListener() 需要放在 indicator.setViewPager(pager) 的上面
        // 否则会使得title不跟随页面动
        pager.setOnPageChangeListener(new MyOnPageChangeListener());
        pager.setOnTouchListener(myOnTouchListener);
        indicator.setOnTouchListener(myOnTouchListener);
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        // 以下这一行代码解决了在平板电脑上ActinBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);
    }

    // TODO implement this class(interface) 实现SimpleOnPageChangeListener，使得在页面滑动的适合能完成内容的检验
    protected class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            currentFragment = (ValidationFragment)pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
            currentFragment.saveResult();
        }
    }

    // TODO implement this interface 实现OnTouchListener，使得检验失败时禁止滑动
    protected class MyOnTouchListener implements View.OnTouchListener {
        private float x1 = 0;
        private float x2 = 0;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            // TODO judge move direction 判断滑动的方向
            if (!validation()) {
                /*
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    x1 = motionEvent.getX();
                    Ln.d("在第%d页", pager.getCurrentItem() + 1);
                    return true;
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    x2 = motionEvent.getX();
                    if (x1 - x2 > 50) {
                        Toast.makeText(getActivity(), "信息填写不完整", Toast.LENGTH_LONG).show();
                        Ln.d("尝试跳过第%d页", pager.getCurrentItem() + 1);
                        return true;
                    }
                }
                */
                Toast.makeText(getActivity(), "信息填写不完整", Toast.LENGTH_LONG).show();
                return true;
            }
            else
                return false;
        }

        private boolean validation() {
            currentFragment = (ValidationFragment)pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
            return currentFragment.validation();
        }
    }
}
