

package com.donnfelker.android.bootstrap.ui;

import android.accounts.OperationCanceledException;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.BootstrapService;
import com.donnfelker.android.bootstrap.core.Forecast;
import com.donnfelker.android.bootstrap.events.NavItemSelectedEvent;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import com.donnfelker.android.bootstrap.util.UIUtils;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.Views;


/**
 * Initial activity for the application.
 *
 * If you need to remove the authentication from the application please see
 * {@link com.donnfelker.android.bootstrap.authenticator.ApiKeyProvider#getAuthKey(android.app.Activity)}
 */
public class MainActivity extends BootstrapFragmentActivity {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LocationManager locationManager;
    protected MyLocationListener myLocationListener;
    // @Inject protected WifiManager wifiManager;
    // private WifiInfo wifiInfo;
    private BluetoothAdapter bluetoothAdapter;
    protected Forecast weather;

    private boolean userHasAuthenticated = false;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private CharSequence drawerTitle;
    private CharSequence title;
    private NavigationDrawerFragment navigationDrawerFragment;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        if(isTablet()) {
            setContentView(R.layout.main_activity_tablet);
        } else {
            setContentView(R.layout.main_activity);
        }

        // View injection with Butterknife
        Views.inject(this);

        // Set up navigation drawer
        title = drawerTitle = getTitle();

        if(!isTablet()) {
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerToggle = new ActionBarDrawerToggle(
                    this,                    /* Host activity */
                    drawerLayout,           /* DrawerLayout object */
                    R.drawable.ic_drawer,    /* nav drawer icon to replace 'Up' caret */
                    R.string.navigation_drawer_open,    /* "open drawer" description */
                    R.string.navigation_drawer_close) { /* "close drawer" description */

                /** Called when a drawer has settled in a completely closed state. */
                public void onDrawerClosed(View view) {
                    getSupportActionBar().setTitle(title);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }

                /** Called when a drawer has settled in a completely open state. */
                public void onDrawerOpened(View drawerView) {
                    getSupportActionBar().setTitle(drawerTitle);
                    supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                }
            };

            // Set the drawer toggle as the DrawerListener
            drawerLayout.setDrawerListener(drawerToggle);

            navigationDrawerFragment = (NavigationDrawerFragment)
                    getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

            // Set up the drawer.
            navigationDrawerFragment.setUp(
                    R.id.navigation_drawer,
                    (DrawerLayout) findViewById(R.id.drawer_layout));
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        myLocationListener = new MyLocationListener();

        checkAuth();

    }

    private boolean isTablet() {
        return UIUtils.isTablet(this);
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if(!isTablet()) {
            // Sync the toggle state after onRestoreInstanceState has occurred.
            drawerToggle.syncState();
        }
    }


    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(!isTablet()) {
            drawerToggle.onConfigurationChanged(newConfig);
        }
    }


    private void initScreen() {
        if (userHasAuthenticated) {

            Ln.d("Foo");
            final FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, new CarouselFragment())
                    .commitAllowingStateLoss();

            String locationProvider = LocationManager.NETWORK_PROVIDER;
            int interval = (int)(0.2 * 60 * 1000);
            // wifiInfo = wifiManager.getConnectionInfo();
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            locationManager.requestLocationUpdates(locationProvider, interval, 0, myLocationListener);

        }
    }

    private void uploadLocation(final Location location) {
        if (location != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("实时的位置信息：\n经度：");
            sb.append(location.getLongitude());
            sb.append("\n纬度：");
            sb.append(location.getLatitude());
            sb.append("\n高度：");
            sb.append(location.getAltitude());
            sb.append("\n速度：");
            sb.append(location.getSpeed());
            sb.append("\n方向：");
            sb.append(location.getBearing());
            sb.append("\n精度：");
            sb.append(location.getAccuracy());
            Ln.d("LBS: %s", sb.toString());
            try {
                SafeAsyncTask<Boolean> forecastTask;
                forecastTask = new SafeAsyncTask<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {

                        weather = serviceProvider.getService(MainActivity.this).
                                getWeather(String.valueOf(location.getLongitude()),
                                        String.valueOf(location.getLatitude()),
                                        bluetoothAdapter.getAddress());
                        return weather != null;
                    }

                    @Override
                    protected void onException(final Exception e) throws RuntimeException {
                        super.onException(e);
                    }
                };
                forecastTask.execute();
                Ln.d("Weather: get weather %s %s ", weather.getStatus(), weather.getDate());
            } catch (Exception e) {
                e. printStackTrace();
            }
        } else {
            Ln.d("LBS: %s", "无法获得数据");
        }
    }

    private void checkAuth() {
        new SafeAsyncTask<Boolean>() {

            @Override
            public Boolean call() throws Exception {
                final BootstrapService svc = serviceProvider.getService(MainActivity.this);
                return svc != null;
            }

            @Override
            protected void onException(final Exception e) throws RuntimeException {
                super.onException(e);
                if (e instanceof OperationCanceledException) {
                    // User cancelled the authentication process (back button, etc).
                    // Since auth could not take place, lets finish this activity.
                    finish();
                }
            }

            @Override
            protected void onSuccess(final Boolean hasAuthenticated) throws Exception {
                super.onSuccess(hasAuthenticated);
                userHasAuthenticated = true;
                initScreen();
            }
        }.execute();
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        if (!isTablet() && drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case android.R.id.home:
                // menuDrawer.toggleMenu();
                return true;
            /*
            case R.id.timer:
                navigateToTimer();
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToTimer() {
        final Intent i = new Intent(this, BootstrapTimerActivity.class);
        startActivity(i);
    }

    private void navigateToUserInfo() {
        final Intent i = new Intent(this, UserInfoActivity.class);
        startActivity(i);
    }

    private void navigateToSubstationInfo() {
        final Intent i = new Intent(this, SubstationInfoActivity.class);
        startActivity(i);
    }


    @Subscribe
    public void onNavigationItemSelected(NavItemSelectedEvent event) {

        Ln.d("Selected: %1$s", event.getItemPosition());

        switch(event.getItemPosition()) {
            case 0:
                // Home
                // do nothing as we're already on the home screen.
                break;
            /*
            case 1:
                // Timer
                navigateToTimer();
                break;
                */
            case 1:
                navigateToUserInfo();
                break;
            case 2:
                navigateToSubstationInfo();
                break;
        }
    }

    public Forecast getWeather() {
        return this.weather;
    }

    public BootstrapServiceProvider getServiceProvider() {
        return this.serviceProvider;
    }


    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            uploadLocation(location);
            Ln.d("LBS: location changed");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //uploadLocation(null);
            Ln.d("LBS: status changed");
        }

        @Override
        public void onProviderEnabled(String provider) {
            //uploadLocation(locationManager.getLastKnownLocation(provider));
            Ln.d("LBS: provider enabled");
        }

        @Override
        public void onProviderDisabled(String provider) {
            //uploadLocation(null);
            Ln.d("LBS: provider disabled");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        locationManager.removeUpdates(myLocationListener);
    }
}
