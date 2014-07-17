package com.donnfelker.android.bootstrap.ui.step;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.BootstrapPagerAdapter;
import com.viewpagerindicator.TitlePageIndicator;

import butterknife.InjectView;
import butterknife.Views;


/**
 * Created by Feather on 14-7-17.
 */
public class DeviceCarouseFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected TitlePageIndicator indicator;

    @InjectView(R.id.vp_pages)protected ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_device, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Views.inject(this, getView());

        pager.setAdapter(new BootstrapPagerAdapter(getResources(), getChildFragmentManager()));
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        // 以下这一行代码解决了在平板电脑上ActinBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);
    }

}
