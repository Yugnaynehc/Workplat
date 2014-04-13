package com.donnfelker.android.bootstrap.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.ui.step.ProcessCarouselFragment;
import com.donnfelker.android.bootstrap.util.UIUtils;

import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_ITEM;

/**
 * Created by Feather on 14-3-12.
 */

public class WorkActivity extends BootstrapFragmentActivity {

    protected FragmentManager fragmentManager;

    private Work work;
    private Result result;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.work_activity);
        Views.inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK_ITEM);
        }
        result = new Result();
        result.setType(work.getType());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = this.getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new ProcessCarouselFragment())
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

    public Work getWork() { return this.work; }

    public Result getResult() { return this.result; }
}

