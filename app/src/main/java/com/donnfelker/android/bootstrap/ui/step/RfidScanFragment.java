package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.nfc.NfcAdapter;
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

    private static final int DEVICEACTIVITY = 1;
    private NfcAdapter mNfcAdapter = null;

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

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent scanDevice = new Intent(getActivity(), DeviceActivity.class);
                scanDevice.putExtra("device_name", "benti");
                startActivityForResult(scanDevice, DEVICEACTIVITY);
            }
        });

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case DEVICEACTIVITY:
                if (resultCode == Activity.RESULT_OK) {
                    nfcCheck();
                }
                break;
            default:
                return;
        }
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

    private void nfcCheck() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(getActivity());
        if (mNfcAdapter == null) {
            Ln.d("NFC: No device");
        } else if (!mNfcAdapter.isEnabled()) {
            Ln.d("NFC: No open");
        }
    }

    public boolean validation() {
        Ln.d("Rfid Scan fragment validation");
        return true;
    }

    public void saveResult() {
        Ln.d("Rfid Scan fragment save result");
    }
}
