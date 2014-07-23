package com.donnfelker.android.bootstrap.ui.step.device;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_CONTENT;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_DATE;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_ID;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NAME;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_NO;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_RESULT;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.DEVICE_STANDARD;

/**
 * Created by feather on 14-7-17.
 */
public class DeviceFormFragment extends Fragment {

    @InjectView(R.id.inspect_detail)protected ListView list;
    @InjectView(R.id.submit)protected BootstrapButton submit;
    private DeviceAdapter adapter;
    private String deviceID;
    private String deviceName;
    private String deviceDate;
    private int deviceNo;
    private ArrayList<String> inspectContent;
    private ArrayList<String> inspectStandard;
    private SparseArray<String> inspectResult;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.device_activity, container, false);
        Views.inject(this, view);

        deviceID = ((DeviceInspectActivity)getActivity()).getDeviceID();
        deviceName = ((DeviceInspectActivity)getActivity()).getDeviceName();
        deviceDate = ((DeviceInspectActivity)getActivity()).getDeviceDate();
        deviceNo = ((DeviceInspectActivity)getActivity()).getDeviceNo();
        inspectContent = ((DeviceInspectActivity)getActivity()).getInspectContent();
        inspectStandard = ((DeviceInspectActivity)getActivity()).getInspectStandard();
        inspectResult = ((DeviceInspectActivity)getActivity()).getInspectResult();

        if (deviceNo != -1)
            submit.setText(this.getResources().getString(R.string.button_edit));

        adapter = new DeviceAdapter(getActivity());
        list.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(DEVICE_ID, deviceID);
                intent.putExtra(DEVICE_NAME, deviceName);
                intent.putExtra(DEVICE_DATE, deviceDate);
                intent.putExtra(DEVICE_NO, deviceNo);
                intent.putExtra(DEVICE_CONTENT, inspectContent);
                intent.putExtra(DEVICE_STANDARD, inspectStandard);
                intent.putExtra(DEVICE_RESULT, getResultList());
//                try {
//                    File file = new File(getActivity().getFilesDir(), deviceID + ".xml");
//                    FileOutputStream out  = new FileOutputStream(file);
//                    saveFile(out);
//                } catch(IOException e) {
//                    Ln.d(e.toString());
//                }
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();
            }
        });
        return view;
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

    private void saveFile(OutputStream out)  throws IOException {

        XmlSerializer serializer = Xml.newSerializer();
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        serializer.setOutput(out, "utf-8");
        serializer.startDocument("GB2312", true);
        serializer.startTag(null, "device");
        serializer.attribute(null, "name", deviceName);
        for (int i=0; i<inspectContent.size(); ++i) {
            serializer.startTag(null, "item");
            serializer.attribute(null, "name",inspectContent.get(i));
            serializer.startTag(null, "standard");
            serializer.text(inspectStandard.get(i));
            serializer.endTag(null, "standard");
            if(inspectResult.get(i).equals("正常")) {
                serializer.startTag(null, "result");
                serializer.text(inspectResult.get(i));
                serializer.endTag(null, "result");
            } else {
                serializer.startTag(null, "result");
                serializer.text("异常");
                serializer.endTag(null, "result");
                serializer.startTag(null, "exceptions");
                serializer.text(inspectResult.get(i));
                serializer.endTag(null, "exceptions");
            }
            serializer.endTag(null, "item");
        }
        serializer.endTag(null, "device");
        serializer.endDocument();
        out.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(getActivity(), "返回数据", Toast.LENGTH_LONG).show();
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String re = data.getStringExtra("result");
            int pos = Integer.parseInt(data.getStringExtra("pos"));
            inspectResult.put(pos, re);
        }
    }

    private class DeviceAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

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
            String no = String.valueOf(position + 1);
            String context = inspectContent.get(position);
            String standard = inspectStandard.get(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.device_list_item, null);
                holder = new DeviceViewHolder();
                holder.no = (TextView)convertView.findViewById(R.id.tv_device_no);
                holder.content = (TextView)convertView.findViewById(R.id.tv_device_content);
                holder.standard = (TextView)convertView.findViewById(R.id.tv_device_standard);
                holder.rg = (RadioGroup)convertView.findViewById(R.id.rg_device_result);
                convertView.setTag(holder);
            }
            else {
                holder = (DeviceViewHolder)convertView.getTag();
            }

            holder.no.setText(no);
            holder.content.setText(context);
            holder.standard.setText(standard);
            if (!inspectResult.get(position).equals("正常")) {
                holder.rg.check(R.id.device_problem);
            }
            try {
                holder.rg.getChildAt(1).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent exIntent = new Intent();
                        exIntent.putExtra("pos", String.valueOf(position));
                        exIntent.putExtra("res",inspectResult.get(position));
                        exIntent.putExtra(DEVICE_ID, deviceID);
                        exIntent.setClass(getActivity(), ExceptionActivity.class);
                        getActivity().startActivityForResult(exIntent, 1);
                    }
                });
                holder.rg.getChildAt(0).setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        inspectResult.put(position,"正常");
                    }


                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private class DeviceViewHolder {
        TextView no;
        TextView content;
        TextView standard;
        RadioGroup rg;
    }
}
