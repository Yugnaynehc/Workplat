package com.donnfelker.android.bootstrap.ui.step;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
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

    @InjectView(R.id.tpi_header)protected TitlePageIndicator indicator;
    @InjectView(R.id.vp_pages)protected ViewPager pager;
    @InjectView(R.id.prev)BootstrapButton prev;
    @InjectView(R.id.next)BootstrapButton next;

    protected InspectPagerAdapter pagerAdapter;

    protected ValidationFragment currentFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_process, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Views.inject(this, getView());
        pagerAdapter = new InspectPagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);
        pager.setCurrentItem(0);
        // 以下这一行代码解决了在平板电脑上ActinBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);
        prev.setOnClickListener(new MyOnClickListener());
        next.setOnClickListener(new MyOnClickListener());
        prev.setBootstrapButtonEnabled(false);
    }

    private boolean validation() {
        currentFragment = (ValidationFragment)pagerAdapter.instantiateItem(pager, pager.getCurrentItem());
        return currentFragment.validation();
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == prev.getId()) {
                int index = pager.getCurrentItem();
                if (index > 0) {
                    pager.setCurrentItem(index - 1);
                    next.setBootstrapButtonEnabled(true);
                }
                if (pager.getCurrentItem() == 0)
                    prev.setBootstrapButtonEnabled(false);
                //pager.arrowScroll(1);
                Ln.d("prev clicked");
            }
            else if (view.getId() == next.getId()) {
                if (validation()) {
                    int index = pager.getCurrentItem();
                    if (index < pagerAdapter.getCount() - 1) {
                        pager.setCurrentItem(index + 1);
                        prev.setBootstrapButtonEnabled(true);
                    }
                    currentFragment.saveResult();
                    if (pager.getCurrentItem() == pagerAdapter.getCount() - 1)
                        next.setBootstrapButtonEnabled(false);
                    //pager.arrowScroll(2);
                    Ln.d("next clicked");
                }
                else
                    Toast.makeText(getActivity(), "请输入完整信息", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
