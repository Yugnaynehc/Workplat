package com.donnfelker.android.bootstrap.ui.step.device;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;

import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.*;

/**
 * Created by feather on 14-7-17.
 */
public class DeviceFormFragment extends Fragment {

    @InjectView(R.id.inspect_detail)protected ListView list;
    private DeviceAdapter adapter;
    private int workStatus;
    private String resultID;
    private String deviceTypeID;
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

        workStatus = ((DeviceInspectActivity)getActivity()).getWorkStatus();
        resultID = ((DeviceInspectActivity)getActivity()).getResultID();
        deviceTypeID = ((DeviceInspectActivity)getActivity()).getDeviceTypeID();
        inspectContent = ((DeviceInspectActivity)getActivity()).getInspectContent();
        inspectStandard = ((DeviceInspectActivity)getActivity()).getInspectStandard();
        inspectResult = ((DeviceInspectActivity)getActivity()).getInspectResult();

        adapter = new DeviceAdapter(getActivity());
        list.setAdapter(adapter);
        return view;
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
                        exIntent.putExtra(WORK_STATUS, workStatus);
                        exIntent.putExtra(RESULT_ID, resultID);
                        exIntent.putExtra(DEVICE_TYPE_ID, deviceTypeID);
                        exIntent.putExtra(DEVICE_ITEM_NO, position);
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
