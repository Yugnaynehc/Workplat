package com.donnfelker.android.bootstrap.ui.step;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.inspect.object.Device;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-4-17.
 */
public class DeviceActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.inspect_detail)protected ListView list;
    private Device device;
    private DeviceAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_activity);
        Views.inject(this);
        adapter = new DeviceAdapter();
        list.setAdapter(adapter);

    }

    private class DeviceAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            return convertView;
        }
    }
}
