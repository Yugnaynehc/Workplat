package com.donnfelker.android.bootstrap.ui.step;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.donnfelker.android.bootstrap.R;

/**
 * Created by Feather on 14-4-2.
 * 巡检流程的页面适配器
 */
public class InspectPagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    public InspectPagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            //case 0:
                //result = new DefectListFragment();
                //break;
            case 0:
                result = new WeatherFragment();
                break;
            case 1:
                result = new ToolsPrepareFragment();
                break;
            case 2:
                result = new SecurityFragment();
                break;
            case 3:
                result = new RfidScanFragment();
                break;
            case 4:
                result = new ResultFragment();
                break;
            default:
                result = null;
                break;
        }
        if (result != null) {
            result.setArguments(new Bundle()); //TODO do we need this?
        }
        return result;
    }

    @Override
    public CharSequence getPageTitle(final int position) {
        switch (position) {
            //case 0:
                //return resources.getString(R.string.page_defect);
            case 0:
                return resources.getString(R.string.page_weather);
            case 1:
                return resources.getString(R.string.page_tools);
            case 2:
                return resources.getString(R.string.page_security);
            case 3:
                return resources.getString(R.string.page_scan);
            case 4:
                return resources.getString(R.string.page_result);
            default:
                return null;
        }
    }
}
