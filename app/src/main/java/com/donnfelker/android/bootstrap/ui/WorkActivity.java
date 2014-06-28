package com.donnfelker.android.bootstrap.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.widget.Toast;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.ui.step.DeviceActivity;
import com.donnfelker.android.bootstrap.ui.step.ProcessCarouselFragment;
import com.donnfelker.android.bootstrap.util.Ln;

import java.util.ArrayList;

import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NAME;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_RESULT;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_ITEM;
import static com.donnfelker.android.bootstrap.core.Constants.Intent.*;

/**
 * Created by Feather on 14-3-12.
 */

public class WorkActivity extends BootstrapFragmentActivity {



    protected FragmentManager fragmentManager;

    private Work work;
    private Result result;

    private NfcAdapter mNfcAdapter = null;
    private PendingIntent pendingIntent = null;
    private IntentFilter ndef = null;
    private IntentFilter[] intentFiltersArray = null;
    private String[][] techListArray = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);


        // initial nfc module
        // 初始化nfc模块
        if (!nfcCheck()) {
            Toast.makeText(this, "NFC error", Toast.LENGTH_SHORT).show();
        } else {
            // TODO 规范需要拦截的NFC卡片的类型（现在是所有的Mifare Classic卡都被拦截）
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

        setContentView(R.layout.work_activity);
        Views.inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK_ITEM);
        }
        result = new Result();
        result.setType(work.getType());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragmentManager = this.getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new ProcessCarouselFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
        int random = 100000 + (int)(Math.random()*20);
        scanDevice.putExtra(DEVICE_ID, String.valueOf(random));
        startActivityForResult(scanDevice, ADD_NEW_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_NEW_RESULT:
                Ln.d("Activity Result: ADD");
                if (resultCode == RESULT_OK) {
                    String deviceName = data.getStringExtra(DEVICE_NAME);
                    String deviceID = data.getStringExtra(DEVICE_ID);
                    ArrayList<String> inspectResult = data.getStringArrayListExtra(DEVICE_RESULT);
                    DeviceResult deviceResult = new DeviceResult(deviceName, deviceID, inspectResult);
                    result.addDeviceResult(deviceResult);
                }
                break;
            case EDIT_EXISTENT_RESULT:
                if (resultCode == RESULT_OK) {
                    int deviceNo = data.getIntExtra(DEVICE_NO, -1);
                    Ln.d("Activity Result: deviceNo = %s", deviceNo);
                    ArrayList<String> inspectResult = data.getStringArrayListExtra(DEVICE_RESULT);
                    if (deviceNo != -1) {
                        result.getDeviceResults().get(deviceNo).setInspectResult(inspectResult);
                    }
                }
                break;
            default:
                break;
        }
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

    public Work getWork() { return this.work; }

    public Result getResult() { return this.result; }

    public NfcAdapter getNfcAdapter() { return this.mNfcAdapter; }
}

