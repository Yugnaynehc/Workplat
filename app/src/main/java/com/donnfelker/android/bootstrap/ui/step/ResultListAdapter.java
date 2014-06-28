package com.donnfelker.android.bootstrap.ui.step;

import android.text.TextUtils;
import android.view.LayoutInflater;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.ui.AlternatingColorListAdapter;

import java.util.List;

/**
 * Created by Feather on 14-6-28.
 */
public class ResultListAdapter extends AlternatingColorListAdapter<DeviceResult> {

    /**
     * @param inflater
     * @param items
     * @param selectable
     */
    public ResultListAdapter(final LayoutInflater inflater, final List<DeviceResult> items,
                             final boolean selectable) {
        super(R.layout.device_result_list_item, inflater, items, selectable);
    }

    /**
     * @param inflater
     * @param items
     */
    public ResultListAdapter(final LayoutInflater inflater, final List<DeviceResult> items) {
        super(R.layout.device_result_list_item, inflater, items);
    }

    @Override
    public long getItemId(final int position) {
        final String id = getItem(position).getObjectId();
        return !TextUtils.isEmpty(id) ? id.hashCode() : super
                .getItemId(position);
    }

    @Override
    protected int[] getChildViewIds() {
        return new int[]{R.id.tv_deviceName};
    }

    @Override
    protected void update(final int position, final DeviceResult deviceResult) {
        super.update(position, deviceResult);
        setText(0, deviceResult.getDeviceName());
    }
}
