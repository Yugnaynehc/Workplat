package com.donnfelker.android.bootstrap.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_ITEM;

/**
 * Created by Feather on 14-3-12.
 */

public class WorkActivity extends BootstrapFragmentActivity {

    protected FragmentManager fragmentManager;

    private Work work;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        fragmentManager = this.getSupportFragmentManager();

        setContentView(R.layout.work_activity);
        Views.inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK_ITEM);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager.beginTransaction()
                .replace(R.id.container, new ProcessCarouselFragment())
                .commit();

    }


}

