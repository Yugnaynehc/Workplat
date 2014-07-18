package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.widget.Toast;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.step.device.DeviceActivity;
import com.donnfelker.android.bootstrap.util.Ln;

/**
 * Created by Feather on 14-6-27.
 */
public class ScanActivity extends Activity{

    private static final int DEVICEACTIVITY = 1;

    private NfcAdapter mNfcAdapter = null;
    private PendingIntent pendingIntent = null;
    private IntentFilter ndef = null;
    private IntentFilter[] intentFiltersArray = null;
    private String[][] techListArray = null;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_activity);
        if (!nfcCheck()) {
            Toast.makeText(this, "NFC error", Toast.LENGTH_SHORT).show();
        } else {
            pendingIntent = PendingIntent.getActivity(this,
                    0,
                    new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                    0);
            ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            try {
                ndef.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("fail", e);
            }
            intentFiltersArray = new IntentFilter[] {ndef, };
            techListArray = new String[][] {new String[] {MifareClassic.class.getName()} };
            Ln.d("NFC: success");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListArray);
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onNewIntent(Intent intent) {
        Tag tagFormatIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        try {
            Ln.d("NFC: %s", tagFormatIntent.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        final Intent scanDevice = new Intent(this, DeviceActivity.class);
        int random = (int)(Math.random()*5);
        String deviceNameList[] = {"0dian4", "10jiange", "10pt", "35jiange", "jiankong"};
        scanDevice.putExtra("device_name", deviceNameList[random]);
        startActivityForResult(scanDevice, DEVICEACTIVITY);
    }

    private boolean nfcCheck() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Ln.d("NFC: No device");
        } else if (!mNfcAdapter.isEnabled()) {
            Ln.d("NFC: No open");
        } else {
            Ln.d("NFC: OK!");
            return true;
        }
        return false;
    }

}
