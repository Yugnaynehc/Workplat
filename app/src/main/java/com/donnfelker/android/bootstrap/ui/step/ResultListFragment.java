package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.ui.ItemListFragment;
import com.donnfelker.android.bootstrap.ui.ThrowableLoader;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_RESULT;
import static com.donnfelker.android.bootstrap.core.Constants.Intent.EDIT_EXISTENT_RESULT;

/**
 * Created by feather on 14-5-19.
 *
 * 设备巡检结果列表
 */
public class ResultListFragment extends ItemListFragment<DeviceResult> implements  ValidationFragment {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_device_results);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.device_result_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() { return logoutService; }

    @Override
    public Loader<List<DeviceResult>> onCreateLoader(final int id, final Bundle args) {
        final List<DeviceResult> initialItems = items;
        return new ThrowableLoader<List<DeviceResult>>(getActivity(), items) {
            @Override
            public List<DeviceResult> loadData() throws Exception {
                try {
                    List<DeviceResult> t = ((WorkActivity)getActivity()).getResult().getDeviceResults();
                    if (t != null) {
                        return t;
                    } else {
                        return Collections.emptyList();
                    }
                } catch (Exception e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };
    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {
        DeviceResult deviceResult = ((DeviceResult) l.getItemAtPosition(position));
        Intent intent = new Intent(getActivity(), DeviceActivity.class);
        intent.putExtra(DEVICE_ID, deviceResult.getDeviceID());
        intent.putExtra(DEVICE_RESULT, deviceResult.getInspectResult());
        intent.putExtra(DEVICE_NO, position - 1);
        getActivity().startActivityForResult(intent, EDIT_EXISTENT_RESULT);
    }

    @Override
    public void onLoadFinished(final Loader<List<DeviceResult>> loader, final List<DeviceResult> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        return R.string.error_loading_device_results;
    }

    @Override
    protected SingleTypeAdapter<DeviceResult> createAdapter(final List<DeviceResult> items) {
        return new ResultListAdapter(getActivity().getLayoutInflater(), items);
    }

    @Override
    public void onResume() {
        super.onResume();
        forceRefresh();
    }

    @Override
    public boolean validation() {
        return true;
    }

    @Override
    public void saveResult() {

    }

}
