package com.donnfelker.android.bootstrap.ui.step.device;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;
import android.util.Xml;
import android.view.Window;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;
import com.donnfelker.android.bootstrap.util.Ln;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_DATE;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_RESULT;
/**
 * Created by Feather on 14-7-17.
 */

public class DeviceInspectActivity extends BootstrapFragmentActivity {

    protected FragmentManager fragmentManager;

    private String deviceID;
    private String deviceName;
    private String deviceDate;
    private int deviceNo;
    private ArrayList<String> inspectContent;
    private ArrayList<String> inspectStandard;
    private SparseArray<String> inspectResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.device_inspect_activity);
        Views.inject(this);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent scanDevice = getIntent();
        deviceID = scanDevice.getStringExtra(DEVICE_ID);
        deviceNo = scanDevice.getIntExtra(DEVICE_NO, -1);
        deviceDate = scanDevice.getStringExtra(DEVICE_DATE);
        inspectContent = new ArrayList<String>();
        inspectStandard = new ArrayList<String>();
        initInspectData();
        if (deviceNo != -1) {
            initInspectResult(scanDevice.getStringArrayListExtra(DEVICE_RESULT));
        } else {
            inspectResult = new SparseArray<String>();
            for(int i=0; i<inspectContent.size(); i++) {
                inspectResult.put(i,"正常");
            }
        }

        fragmentManager = this.getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.container, new DeviceCarouseFragment())
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
    }

    @Override
    public void onRestart() {
        super.onRestart();
    }

    @Override
    public void onPause() {
        super.onPause();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String re = data.getStringExtra("result");
            int pos = Integer.parseInt(data.getStringExtra("pos"));
            inspectResult.put(pos, re);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initInspectResult(ArrayList<String> list) {
        inspectResult = new SparseArray<String>();
        int index = 0;
        for (String s : list) {
            if (!s.equals("")) {
                inspectResult.put(index, s);
            }
            index++;
        }
    }

    private void initInspectData() {
        XmlPullParser parser = Xml.newPullParser();
        InputStream inStream = null;
        int eventType;

        try {
            /**
             * TODO 实际使用时应该是从数据存取区获得数据。
             * 整体流程应该如下：
             * 用户登录->检查服务器是否发出XML文件更新指令->检查本地XML文件是否完整->
             * 读取某一个巡检任务(设备)对应的XML文件->将XML文件的内容呈现至屏幕
             */
            inStream = getAssets().open("devices/"
                    + deviceID + ".xml");
            parser.setInput(inStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if (name.equalsIgnoreCase("item")) {
                            inspectContent.add(parser.getAttributeValue(0));
                            Ln.d("inspect content %s", parser.getAttributeValue(0));
                        } else if (name.equalsIgnoreCase("standard")) {
                            if (parser.next() == XmlPullParser.TEXT) {
                                inspectStandard.add(parser.getText().trim());
                                Ln.d("inspect standard %s", parser.getText());
                            }
                        } else if (name.equalsIgnoreCase("device")) {
                            deviceName = parser.getAttributeValue(0);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (inStream != null)
                inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getInspectContent() { return this.inspectContent; }
    public ArrayList<String> getInspectStandard() { return this.inspectStandard; }
    public SparseArray<String> getInspectResult() { return this.inspectResult; }
    public String getDeviceID() { return this.deviceID; }
    public String getDeviceName() { return this.deviceName; }
    public String getDeviceDate() { return this.deviceDate; }
    public int getDeviceNo() { return this.deviceNo; }
}