package com.donnfelker.android.bootstrap.ui.step.device;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.MyTitlePageIndicator;
import com.donnfelker.android.bootstrap.util.MyViewPager;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.*;
/**
 * Created by Feather on 14-7-17.
 */
public class DeviceCarouseFragment extends Fragment {

    @InjectView(R.id.tpi_header)protected MyTitlePageIndicator indicator;

    @InjectView(R.id.vp_pages)protected MyViewPager pager;
    protected DevicePagerAdapter pagerAdapter;

    @InjectView(R.id.prev)BootstrapButton prev;
    @InjectView(R.id.next)BootstrapButton next;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_device, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Views.inject(this, getView());

        pagerAdapter = new DevicePagerAdapter(getResources(), getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        indicator.setViewPager(pager);

        prev.setOnClickListener(new MyOnClickListener());
        next.setOnClickListener(new MyOnClickListener());

        if (getActivity().getIntent().getIntExtra(DEVICE_NO, -1) != -1) {
            pager.setCurrentItem(2);
            next.setText(getResources().getString(R.string.button_submit));
        }

        else {
            pager.setCurrentItem(0);
            prev.setBootstrapButtonEnabled(false);
        }

        // 以下这一行代码解决了在平板电脑上ActionBar的menu显示不正常的问题。
        // 可以在https://code.google.com/p/android/issues/detail?id=29472中找到详细讨论
        // Thanks!
        pager.setOffscreenPageLimit(3);

    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == prev.getId()) {
                int index = pager.getCurrentItem();
                if (index > 0) {
                    pager.setCurrentItem(index - 1);
                    next.setBootstrapButtonEnabled(true);
                    next.setText(getResources().getString(R.string.button_next));
                }
                if (pager.getCurrentItem() == 0)
                    prev.setBootstrapButtonEnabled(false);
                next.setOnClickListener(this);
            }
            else if (view.getId() == next.getId()) {
                    int index = pager.getCurrentItem();
                    if (index < pagerAdapter.getCount() - 1) {
                        pager.setCurrentItem(index + 1);
                        prev.setBootstrapButtonEnabled(true);
                    }
                    if (pager.getCurrentItem()  == pagerAdapter.getCount() - 1) {
                        next.setText(getResources().getString(R.string.button_submit));
                        next.setOnClickListener(new OnSubmitClickListener());
                    }
            }
        }
    }

    private class OnSubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent();
            intent.putExtra(DEVICE_ID, ((DeviceInspectActivity)getActivity()).getDeviceID());
            intent.putExtra(DEVICE_NAME, ((DeviceInspectActivity)getActivity()).getDeviceName());
            intent.putExtra(DEVICE_DATE, ((DeviceInspectActivity)getActivity()).getDeviceDate());
            intent.putExtra(DEVICE_NO, ((DeviceInspectActivity)getActivity()).getDeviceNo());
            intent.putExtra(DEVICE_CONTENT, ((DeviceInspectActivity) getActivity()).getInspectContent());
            intent.putExtra(DEVICE_STANDARD, ((DeviceInspectActivity)getActivity()).getInspectStandard());
            intent.putExtra(DEVICE_RESULT, getResultList());
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    }

    private ArrayList<String> getResultList() {
        ArrayList<String> result = new ArrayList<String>();

        ArrayList<String> inspectContent = ((DeviceInspectActivity) getActivity()).getInspectContent();
        SparseArray<String> inspectResult = ((DeviceInspectActivity)getActivity()).getInspectResult();

        for (int i=0; i<inspectContent.size(); ++i) {
            String item = inspectResult.get(i);
            if (item == null)
                result.add("");
            else
                result.add(item);
        }
        return result;
    }

}
