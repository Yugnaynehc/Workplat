package com.donnfelker.android.bootstrap.ui.step;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.donnfelker.android.bootstrap.R;


/**
 * Created by Feather on 14-7-17.
 */
public class DevicePagerAdapter extends FragmentPagerAdapter {

    private final Resources resources;

    public DevicePagerAdapter(final Resources resources, final FragmentManager fragmentManager) {
        super(fragmentManager);
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(final int position) {
        final Fragment result;
        switch (position) {
            case 0:
                result = new WeatherFragment();
                break;
            case 1:
                result = new ToolsPrepareFragment();
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
                return resources.getString(R.string.page_device_info);
            case 1:
                return resources.getString(R.string.page_device_form);
            default:
                return null;
        }
    }
}