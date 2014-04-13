package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-4-11.
 */
public class ToolsPrepareFragment extends Fragment implements ValidationFragment {

    @InjectView(R.id.tools_list) protected ListView toolsList;

    private ToolsAdapter adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        Views.inject(this, view);
        adapter = new ToolsAdapter(getActivity());
        toolsList.setAdapter(adapter);
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
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public boolean validation() {
        return true;
    }

    public void saveResult() {

    }

    private class ToolsAdapter extends BaseAdapter {

        // TODO change the organization of tools list 使用一种更好的工具信息的组织存储方式
        // 工具信息的字符串列表，包括工具的名称，规格，单位，备注
        private List<String> toolsName;
        private List<String> toolsUnit;
        private List<String> toolsRemark;
        private HashMap<Integer, String> numMap;
        private HashMap<Integer, String> typeMap;
        private HashMap<Integer, Boolean> selectMap;
        private Context context;
        private LayoutInflater inflater;

        public ToolsAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
            toolsName = initToolsName();
            toolsUnit = initToolsUnit();
            toolsRemark = initToolsRemark();
            numMap = new HashMap<Integer, String>();
            typeMap = new HashMap<Integer, String>();
            selectMap = initSelect();
        }

        private List<String> initToolsName() {
            List<String> name = new ArrayList<String>();
            name.add(getActivity().getResources().getString(R.string.tool_name_helmet));
            name.add(getActivity().getResources().getString(R.string.tool_name_gloves));
            name.add(getActivity().getResources().getString(R.string.tool_name_boots));
            name.add(getActivity().getResources().getString(R.string.tool_name_telescope));
            name.add(getActivity().getResources().getString(R.string.tool_name_thermodetector));
            name.add(getActivity().getResources().getString(R.string.tool_name_lamp));
            name.add(getActivity().getResources().getString(R.string.tool_name_key));
            name.add(getActivity().getResources().getString(R.string.tool_name_goggle));
            name.add(getActivity().getResources().getString(R.string.tool_name_interphone));
            return name;
        }

        private List<String> initToolsUnit() {
            List<String> unit = new ArrayList<String>();
            unit.add(getActivity().getResources().getString(R.string.tool_unit_ding));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_shuang));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_shuang));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_fu));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_tai));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_zhan));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_tao));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_ge));
            unit.add(getActivity().getResources().getString(R.string.tool_unit_ge));
            return unit;
        }

        private List<String> initToolsRemark() {
            List<String> remark = new ArrayList<String>();
            remark.add(getActivity().getResources().getString(R.string.tool_remark_helmet));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_gloves));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_boots));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_telescope));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_thermodetector));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_lamp));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_key));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_goggle));
            remark.add(getActivity().getResources().getString(R.string.tool_remark_interphone));
            return remark;
        }

        private HashMap<Integer, Boolean> initSelect() {
            HashMap<Integer, Boolean> select = new HashMap<Integer, Boolean>();
            for (int i=0; i<toolsName.size(); ++i)
                select.put(i, false);
            return select;
        }

        @Override
        public int getCount() {
            return toolsName.size();
        }

        @Override
        public Object getItem(int position) {
            return toolsName.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View converView, ViewGroup parent) {
            String toolNo = String.valueOf(position + 1);
            String toolName = toolsName.get(position);
            String toolUnit = toolsUnit.get(position);
            String toolRemark = toolsRemark.get(position);
            converView = inflater.inflate(R.layout.tools_list_item, null);
            final TextView no = (TextView)converView.findViewById(R.id.tv_tool_no);
            final TextView name = (TextView)converView.findViewById(R.id.tv_tool_name);
            final EditText type = (EditText)converView.findViewById(R.id.et_tool_type);
            final TextView unit = (TextView)converView.findViewById(R.id.tv_tool_unit);
            final EditText num = (EditText)converView.findViewById(R.id.et_tool_num);
            final TextView remark = (TextView)converView.findViewById(R.id.tv_tool_remark);
            final CheckBox select = (CheckBox)converView.findViewById(R.id.cb_tool_select);

            no.setText(toolNo);
            name.setText(toolName);
            type.setText(typeMap.get(position));
            unit.setText(toolUnit);
            num.setText(numMap.get(position));
            remark.setText(toolRemark);
            select.setOnCheckedChangeListener(new MyOnCheckedChangeListener(position));

            return converView;
        }

        private class MyOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {

            private int position;

            public MyOnCheckedChangeListener(int position) {
                this.position = position;
            }

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    selectMap.put(position, true);
                }
                else {
                    selectMap.put(position, false);
                }

            }
        }
    }

}
