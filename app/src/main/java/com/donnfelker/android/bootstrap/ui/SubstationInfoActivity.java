package com.donnfelker.android.bootstrap.ui;

import android.os.Bundle;

import com.donnfelker.android.bootstrap.R;

/**
 * Created by Feather on 14-7-27.
 */
public class SubstationInfoActivity extends BootstrapActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.substation_info_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

}
