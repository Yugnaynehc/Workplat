package com.donnfelker.android.bootstrap.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.donnfelker.android.bootstrap.Injector;
import com.donnfelker.android.bootstrap.R;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.*;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 * Created by Feather on 14-7-27.
 */
public class UserInfoActivity extends BootstrapActivity {

    @Inject protected SharedPreferences sharedPreferences;
    @InjectView(R.id.user_name) EditText name;
    @InjectView(R.id.user_post) EditText post;
    @InjectView(R.id.user_department) EditText department;
    @InjectView(R.id.user_substation) EditText substation;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Injector.inject(this);
        setContentView(R.layout.user_info_activity);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        name.setText(sharedPreferences.getString(USER_INFO_NAME, ""));
        post.setText(sharedPreferences.getString(USER_INFO_POSITION, ""));
        department.setText(sharedPreferences.getString(USER_INFO_DEPARTMENT, ""));
        substation.setText(sharedPreferences.getString(USER_INFO_SUBSTATION, ""));

    }

}
