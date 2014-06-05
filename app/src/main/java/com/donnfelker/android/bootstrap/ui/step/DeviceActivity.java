package com.donnfelker.android.bootstrap.ui.step;

import android.content.Intent;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.inspect.object.Device;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;
import com.donnfelker.android.bootstrap.util.Ln;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-4-17.
 */
public class DeviceActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.inspect_detail)protected ListView list;
    private Device device;
    private DeviceAdapter adapter;
    private String deviceName;
    private List<String> inspectContent;
    private List<String> inspectStandard;
    private List<String> inspectResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        super.onCreate(savedInstanceState);
        Intent scanDevice = getIntent();
        deviceName = scanDevice.getStringExtra("device_name");
        inspectContent = new ArrayList<String>();
        inspectStandard = new ArrayList<String>();
        inspectResult = new ArrayList<String>();
        initInspectData();
        setContentView(R.layout.device_activity);
        Views.inject(this);
        adapter = new DeviceAdapter();
        list.setAdapter(adapter);

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
            //inStream = getAssets().open(deviceName);
            //parser.setInput(inStream, "UTF-8");
            inStream = getAssets().open("benti.xml");
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
                            inspectContent.add(parser.getAttributeName(0));
                        } else if (name.equalsIgnoreCase("standard")) {
                            inspectStandard.add(parser.getText());
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

        for (String i : inspectContent)
            Ln.d("inspectContent %s", i);
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
