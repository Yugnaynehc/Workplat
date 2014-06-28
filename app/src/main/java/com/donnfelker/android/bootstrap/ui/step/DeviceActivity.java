package com.donnfelker.android.bootstrap.ui.step;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.ui.BootstrapFragmentActivity;
import com.donnfelker.android.bootstrap.util.Ln;

import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NAME;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_RESULT;
/**
 * Created by feather on 14-4-17.
 */
public class DeviceActivity extends BootstrapFragmentActivity {

    @InjectView(R.id.inspect_detail)protected ListView list;
    @InjectView(R.id.submit)protected BootstrapButton submit;
    private DeviceAdapter adapter;
    private String deviceName;
    private int deviceNo;
    private ArrayList<String> inspectContent;
    private ArrayList<String> inspectStandard;
    private SparseArray<String> inspectResult;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity);
        Views.inject(this);

        Intent scanDevice = getIntent();
        deviceName = scanDevice.getStringExtra(DEVICE_NAME);
        deviceNo = scanDevice.getIntExtra(DEVICE_NO, -1);
        setTitle(deviceName);
        inspectContent = new ArrayList<String>();
        inspectStandard = new ArrayList<String>();
        if (deviceNo != -1) {
            initInspectResult(scanDevice.getStringArrayListExtra(DEVICE_RESULT));
            submit.setText(this.getResources().getString(R.string.button_edit));
        } else {
            inspectResult = new SparseArray<String>();
        }
        initInspectData();
        adapter = new DeviceAdapter(this);
        list.setAdapter(adapter);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(DEVICE_NAME, deviceName);
                intent.putExtra(DEVICE_NO, deviceNo);
                intent.putExtra(DEVICE_RESULT, getResultList());
                DeviceActivity.this.setResult(RESULT_OK, intent);
                DeviceActivity.this.finish();
            }
        });
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

    private ArrayList<String> getResultList() {
        ArrayList<String> result = new ArrayList<String>();

        for (int i=0; i<inspectContent.size(); ++i) {
            String item = inspectResult.get(i);
            if (item == null)
                result.add("");
            else
                result.add(item);
        }
        return result;
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
            inStream = getAssets().open("devices/" + deviceName + ".xml");
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
                                inspectStandard.add(parser.getText());
                                Ln.d("inspect standard %s", parser.getText());
                            }
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

    private class DeviceAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        private int touchIndex;

        public DeviceAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
        }

        @Override
        public int getCount() {
            return inspectContent.size();
        }

        @Override
        public Object getItem(int position) {
            return inspectContent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final DeviceViewHolder holder;
            String no = String.valueOf(position + 1 );
            String context = inspectContent.get(position);
            String standard = inspectStandard.get(position);

            Ln.d("getView %d", position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.device_list_item, null);
                holder = new DeviceViewHolder();
                holder.no = (TextView)convertView.findViewById(R.id.tv_device_no);
                holder.content = (TextView)convertView.findViewById(R.id.tv_device_content);
                holder.standard = (TextView)convertView.findViewById(R.id.tv_device_standard);
                holder.result = (EditText)convertView.findViewById(R.id.et_device_result);
                convertView.setTag(holder);
            }
            else {
                holder = (DeviceViewHolder)convertView.getTag();
            }

            holder.no.setText(no);
            holder.content.setText(context);
            holder.standard.setText(standard);
            holder.result.setText(inspectResult.get(position));
            holder.result.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    inspectResult.put(position, holder.result.getText().toString());
                    Ln.d("typeMap %d %s", position, inspectResult.get(position));
                }
            });
            holder.result.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        touchIndex = position;
                    }
                    return false;
                }
            });

            holder.result.clearFocus();
            if (touchIndex == position) {
                holder.result.requestFocus();
            }
            return convertView;
        }
    }

    private class DeviceViewHolder {
        TextView no;
        TextView content;
        TextView standard;
        EditText result;
    }
}
