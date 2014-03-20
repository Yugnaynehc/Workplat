package com.donnfelker.android.bootstrap.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_ITEM;

/**
 * Created by Feather on 14-3-12.
 */

public class WorkActivity extends BootstrapActivity {

    @InjectView(R.id.tv_name) protected TextView name;

    private Work work;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.work_view);
        Views.inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK_ITEM);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name.setText(String.format("%s", work.getType()));

    }


}

