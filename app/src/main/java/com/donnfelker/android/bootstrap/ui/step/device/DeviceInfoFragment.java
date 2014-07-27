package com.donnfelker.android.bootstrap.ui.step.device;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.donnfelker.android.bootstrap.R;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-7-17.
 */
public class DeviceInfoFragment extends Fragment {

    @InjectView(R.id.device_id) EditText deviceID;
    @InjectView(R.id.device_type_id) EditText deviceTypeID;
    @InjectView(R.id.device_name) EditText deviceName;
    @InjectView(R.id.device_date) EditText deviceDate;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_device_info, container, false);
        Views.inject(this, view);
        deviceID.setText(((DeviceInspectActivity) getActivity()).getDeviceID());
        deviceTypeID.setText(((DeviceInspectActivity) getActivity()).getDeviceTypeID());
        deviceName.setText(((DeviceInspectActivity)getActivity()).getDeviceName());
        deviceDate.setText(((DeviceInspectActivity)getActivity()).getDeviceDate());
        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
}
