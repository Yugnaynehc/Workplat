package com.donnfelker.android.bootstrap;

import android.accounts.AccountManager;
import android.content.Context;

import com.donnfelker.android.bootstrap.authenticator.BootstrapAuthenticatorActivity;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.TimerService;
import com.donnfelker.android.bootstrap.ui.ApplyFragment;
import com.donnfelker.android.bootstrap.ui.BootstrapTimerActivity;
import com.donnfelker.android.bootstrap.ui.CheckInsListFragment;
import com.donnfelker.android.bootstrap.ui.ForecastFragment;
import com.donnfelker.android.bootstrap.ui.MainActivity;
import com.donnfelker.android.bootstrap.ui.NavigationDrawerFragment;
import com.donnfelker.android.bootstrap.ui.NewsActivity;
import com.donnfelker.android.bootstrap.ui.NewsListFragment;
import com.donnfelker.android.bootstrap.ui.SubstationInfoActivity;
import com.donnfelker.android.bootstrap.ui.UserActivity;
import com.donnfelker.android.bootstrap.ui.UserInfoActivity;
import com.donnfelker.android.bootstrap.ui.UserListFragment;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.ui.WorkListFragment;
import com.donnfelker.android.bootstrap.ui.step.device.DefectListFragment;
import com.donnfelker.android.bootstrap.ui.step.device.DeviceActivity;
import com.donnfelker.android.bootstrap.ui.step.device.DeviceInspectActivity;
import com.donnfelker.android.bootstrap.ui.step.ResultListFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for setting up provides statements.
 * Register all of your entry points below.
 */
@Module(
        complete = false,

        injects = {
                BootstrapApplication.class,
                BootstrapAuthenticatorActivity.class,
                MainActivity.class,
                BootstrapTimerActivity.class,
                CheckInsListFragment.class,
                NavigationDrawerFragment.class,
                NewsActivity.class,
                NewsListFragment.class,
                UserActivity.class,
                UserListFragment.class,
                WorkActivity.class,
                WorkListFragment.class,
                DefectListFragment.class,
                TimerService.class,
                DeviceActivity.class,
                ResultListFragment.class,
                DeviceInspectActivity.class,
                ForecastFragment.class,
                ApplyFragment.class,
                UserInfoActivity.class,
                SubstationInfoActivity.class
        }
)
public class BootstrapModule {

    @Singleton
    @Provides
    Bus provideOttoBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    LogoutService provideLogoutService(final Context context, final AccountManager accountManager) {
        return new LogoutService(context, accountManager);
    }

}
