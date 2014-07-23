package com.donnfelker.android.bootstrap.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.widget.Toast;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.DeviceResult;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.ui.step.device.DeviceInspectActivity;
import com.donnfelker.android.bootstrap.ui.step.ProcessCarouselFragment;
import com.donnfelker.android.bootstrap.util.Ln;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.*;
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
    private IntentFilter nfcFliter = null;
    private IntentFilter[] intentFiltersArray = null;
    private String[][] techListArray = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        initNFCModule();

        setContentView(R.layout.work_activity);
        Views.inject(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            work = (Work) getIntent().getExtras().getSerializable(WORK_ITEM);
        }
        result = (Result)getIntent().getSerializableExtra(RESULT_ITEM);
        if (result == null)
            result = new Result();

        result.setType(work.getType());
        result.setResultid(work.getPlanid());

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

    private void initNFCModule() {
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
            nfcFliter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
            try {
                nfcFliter.addDataType("*/*");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                throw new RuntimeException("fail", e);
            }
            intentFiltersArray = new IntentFilter[] {nfcFliter, };
            techListArray = new String[][] {new String[] {MifareClassic.class.getName()} };
            Ln.d("NFC: success");
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = null;
        Tag tagFormatIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Ndef ndef = Ndef.get(tagFormatIntent);
        try {

            // read the tag
            ndef.connect();
            //Ln.d("NFC test tag: %s", tagFormatIntent.toString());
            NdefMessage message = ndef.getNdefMessage();
            //Ln.d("NFC test message: %s", message.toString());
            NdefRecord[] records = message.getRecords();
            for (NdefRecord record : records) {
                jsonObject = jsonParser.parse(parseRecord(record)).getAsJsonObject();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Ln.d("NFC test exception: %s", e.toString());
        }
        final Intent scanDevice = new Intent(this, DeviceInspectActivity.class);
        try {
            scanDevice.putExtra(DEVICE_ID, jsonObject.get("id").getAsString());
            scanDevice.putExtra(DEVICE_DATE, jsonObject.get("date").getAsString());
            startActivityForResult(scanDevice, ADD_NEW_RESULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String parseRecord(NdefRecord record) {
        if (record.getTnf() != NdefRecord.TNF_WELL_KNOWN)
            return null;
        //验证可变长度类型是否为RTD_TEXT
        if (!Arrays.equals(record.getType(), NdefRecord.RTD_TEXT))
            return null;
        try {
            //获取payload
            byte[] payload = record.getPayload();
            //下面代码分析payload：状态字节+ISO语言编码（ASCLL）+文本数据（UTF_8/UTF_16）
            //其中payload[0]放置状态字节：如果bit7为0，文本数据以UTF_8格式编码，如果为1则以UTF_16编码
            //bit6是保留位，默认为0
            String textEncoding = ((payload[0] & 0x80) == 0) ? "UTF-8"
                    : "UTF-16";
            //处理bit5-0。bit5-0表示语言编码长度（字节数）
            int languageCodeLength = payload[0] & 0x3f;
            //获取语言编码（从payload的第2个字节读取languageCodeLength个字节作为语言编码）
            String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
            //解析出实际的文本数据
            String text = new String(payload, languageCodeLength + 1,
                    payload.length - languageCodeLength - 1, textEncoding);
            //创建一个TextRecord对象，并返回该对象
            return text;
        } catch (UnsupportedEncodingException e) {
            // should never happen unless we get a malformed tag.
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_NEW_RESULT:
                if (resultCode == RESULT_OK) {
                    Ln.d("Activity Result: ADD");
                    String deviceName = data.getStringExtra(DEVICE_NAME);
                    String deviceID = data.getStringExtra(DEVICE_ID);
                    String deviceDate = data.getStringExtra(DEVICE_DATE);
                    ArrayList<String> inspectContent = data.getStringArrayListExtra(DEVICE_CONTENT);
                    ArrayList<String> inspectStandard = data.getStringArrayListExtra(DEVICE_STANDARD);
                    ArrayList<String> inspectResult = data.getStringArrayListExtra(DEVICE_RESULT);
                    DeviceResult deviceResult = new DeviceResult(
                            deviceID,
                            deviceName,
                            deviceDate,
                            inspectContent,
                            inspectStandard,
                            inspectResult
                    );
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

