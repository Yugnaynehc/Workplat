package com.donnfelker.android.bootstrap.ui.step;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;

import butterknife.Views;

/**
 * Created by Feather on 14-7-17.
 */

public class DeviceInspectActivity extends BootstrapFragmentActivity {

    protected FragmentManager fragmentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_inspect_activity);
        Views.inject(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = this.getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new DeviceCarouseFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
