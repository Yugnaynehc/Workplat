package com.donnfelker.android.bootstrap.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Fragment which houses the View pager.
 */
public class CarouselFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected TitlePageIndicator indicator;

    @InjectView(R.id.vp_pages)protected ViewPager pager;

    protected BootstrapPagerAdapter pagerAdapter;

    protected ForecastFragment forecastFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Views.inject(this, getView());

        pagerAdapter = new BootstrapPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);
        indicator.setOnPageChangeListener(new MyOnPageChangeListener());
        pager.setCurrentItem(1);
        // 以下这一行代码解决了在平板电脑上ActionBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);

    }

    private class MyOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            // if forecastFragment is selected, try to get weather information
            if (position == 0) {
                forecastFragment = (ForecastFragment)pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
                forecastFragment.getWeather();
            }
        }
    }

}