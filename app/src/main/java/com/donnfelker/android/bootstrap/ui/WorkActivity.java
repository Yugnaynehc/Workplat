package com.donnfelker.android.bootstrap.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK;

/**
 * Created by Feather on 14-3-12.
 */

public class WorkActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    private Work work;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.work_view);

        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        Picasso.with(this).load(work.getAvatarUrl())
                .placeholder(R.drawable.gravatar_icon)
                .into(avatar);
                */

        name.setText(String.format("%s %s", work.getType(), work.getDate()));

    }


}

