package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;
import com.cetc7.UHFReader.UHFReaderClass;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-4-11.
 */
public class RfidScanFragment extends Fragment implements ValidationFragment {

    private UHFReaderClass UHFRFID;
    @InjectView(R.id.button_test)protected BootstrapButton test;
    @InjectView(R.id.button_scan)protected BootstrapButton scan;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_scan, container, false);
        Views.inject(this, view);
        UHFRFID = new UHFReaderClass();

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ln.d("scan test click");
                int result = UHFRFID.SetPower(true);
                Ln.d("scan rfid setpower = %d", result);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ln.d("scan scan click");
                int result = UHFRFID.Connect();
                Ln.d("scan rfid connect result = %d", result);
                if (result == 0) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("测试")
                            .setMessage("")
                            .setPositiveButton("确定", null)
                            .show();
                }

            }
        });
        /*
        int result;
        result = UHFRFID.SetPower(true);
        Ln.d("scan set power result = %d", result);
        result = UHFRFID.Connect();
        Ln.d("scan connect result = %d", result);
        result = UHFRFID.SetReaderMode(0);
        Ln.d("scan set read mode result = %d", result);
        result = UHFRFID.GetReaderMode();
        Ln.d("scan get read mode result = %d", result);
        result = UHFRFID.GetRFAttenuation();
        Ln.d("scan get rf attenuation result = %d", result);
        result = UHFRFID.GetFrequency();
        Ln.d("scan get frequency result = %d", result);
        */

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
        UHFRFID.DisConnect();
        UHFRFID.SetPower(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public boolean validation() {
        Ln.d("Rfid Scan fragment validation");
        return true;
    }

    public void saveResult() {
        Ln.d("Rfid Scan fragment save result");
    }
}
