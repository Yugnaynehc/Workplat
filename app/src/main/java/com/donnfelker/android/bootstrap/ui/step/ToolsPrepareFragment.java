package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.InspectTool;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.Views;


/**
 * Created by feather on 14-4-11.
 */
public class ToolsPrepareFragment extends Fragment implements ValidationFragment {

    protected ListView toolsList;
    private ToolsAdapter adapter;
    private List<String> toolsName;
    private List<String> toolsUnit;
    private List<String> toolsRemark;
    private SparseArray<String> numMap;
    private SparseArray<String> typeMap;
    private SparseBooleanArray selectMap;
    private Result result;
    private Work work;
    private List<InspectTool> toolList;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
        result = ((WorkActivity)getActivity()).getResult();
        work = ((WorkActivity)getActivity()).getWork();
        toolList = result.getTools();
        toolsList = (ListView)view.findViewById(R.id.tools_list);
        numMap = new SparseArray<String>();
        typeMap = new SparseArray<String>();
        if (work.getStatus() == 0 || toolList == null)
            adapter = new ToolsAdapter(getActivity());
        else
            adapter = new hasToolsAdapter(getActivity());

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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean validation() {
        Ln.d("tools prepare fragment validation");
        for (int i=0;  i<toolsName.size(); ++i) {
            if (selectMap.get(i)) {
                if (typeMap.get(i) == null
                        || typeMap.get(i).equals("")
                        || numMap.get(i) == null
                        || numMap.get(i).equals(""))
                    return false;
            }
        }
        return true;
    }

    @Override
    public void saveResult() {
        toolList = new ArrayList<InspectTool>();
        InspectTool tool;
        Ln.d("tools prepare fragment save result");
        for (int i=0;  i<toolsName.size(); ++i) {
            if (selectMap.get(i)) {
                tool = new InspectTool(toolsName.get(i), typeMap.get(i), numMap.get(i));
                toolList.add(tool);
            }
        }
        result.setTools(toolList);
    }

    private class ToolsAdapter extends BaseAdapter {

        // TODO change the organization of tools list 使用一种更好的工具信息的组织存储方式
        // 工具信息的字符串列表，包括工具的名称，规格，单位，备注
        private Context context;
        private LayoutInflater inflater;
        // the following tow variables are used to fix EditText focus bug
        // 以下两个变量用来解决ListView中EditText无法正确获得焦点的问题
        int touchIndex = -1; // used to save touch position of touch event
        // 保存touch事件的所在行
        int touchItem = 0;  // used to save select item of touch event,
        // -1 is holder.type and 1 is holder.num

        public ToolsAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            toolsName = initToolsName();
            toolsUnit = initToolsUnit();
            toolsRemark = initToolsRemark();
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

        protected SparseBooleanArray initSelect() {
            SparseBooleanArray select = new SparseBooleanArray();
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ToolsViewHolder holder;

            String toolNo = String.valueOf(position + 1);
            String toolName = toolsName.get(position);
            String toolUnit = toolsUnit.get(position);
            String toolRemark = toolsRemark.get(position);

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tools_list_item, null);
                holder = new ToolsViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ToolsViewHolder)convertView.getTag();
            }

            holder.no.setText(toolNo);
            holder.name.setText(toolName);
            holder.type.setText(typeMap.get(position));
            holder.type.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    typeMap.put(position, holder.type.getText().toString());
                    Ln.d("typeMap %d %s", position, typeMap.get(position));
                }
            });
            holder.type.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        touchIndex = position;
                        touchItem = -1;
                    }
                    return false;
                }
            });
            holder.unit.setText(toolUnit);
            holder.num.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable editable) {
                    numMap.put(position, holder.num.getText().toString());
                }
            });
            holder.num.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        touchIndex = position;
                        touchItem = 1;
                    }
                    return false;
                }
            });
            holder.num.setText(numMap.get(position));
            holder.remark.setText(toolRemark);
            holder.select.setOnCheckedChangeListener(null);
            holder.select.setChecked(selectMap.get(position));
            holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    selectMap.put(position, isChecked);
                }
            });

            holder.type.clearFocus();
            holder.num.clearFocus();

            if (touchIndex != -1 && touchIndex == position) {
                if (touchItem == -1)
                    holder.type.requestFocus();
                else if(touchItem == 1)
                    holder.num.requestFocus();
            }

            return convertView;
        }
    }

    private class hasToolsAdapter extends ToolsAdapter {

        public hasToolsAdapter(Context context) {
            super(context);
        }

        @Override
        protected SparseBooleanArray initSelect() {
            SparseBooleanArray select = new SparseBooleanArray();
            for (int i=0; i<toolsName.size(); ++i) {
                for (InspectTool tool : toolList) {
                    if (tool.getName().equals(toolsName.get(i))) {
                        select.put(i, true);
                        numMap.put(i, tool.getNum());
                        typeMap.put(i, tool.getType());
                    }
                }

            }
            return select;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ToolsViewHolder holder;

            String toolNo = String.valueOf(position + 1);
            String toolName = toolsName.get(position);
            String toolUnit = toolsUnit.get(position);
            String toolRemark = toolsRemark.get(position);

            if (convertView == null) {
                convertView = super.inflater.inflate(R.layout.tools_list_item, null);
                holder = new ToolsViewHolder(convertView);
                convertView.setTag(holder);
            }
            else {
                holder = (ToolsViewHolder)convertView.getTag();
            }

            holder.no.setText(toolNo);
            holder.name.setText(toolName);
            holder.type.setText(typeMap.get(position));
            holder.type.setFocusable(false);
            holder.unit.setText(toolUnit);
            holder.num.setText(numMap.get(position));
            holder.num.setFocusable(false);
            holder.remark.setText(toolRemark);
            holder.select.setChecked(selectMap.get(position));
            holder.select.setClickable(false);
            return convertView;

        }


    }

    static class ToolsViewHolder {
        @InjectView(R.id.tv_tool_no)TextView no;
        @InjectView(R.id.tv_tool_name)TextView name;
        @InjectView(R.id.et_tool_type)EditText type;
        @InjectView(R.id.tv_tool_unit)TextView unit;
        @InjectView(R.id.et_tool_num)EditText num;
        @InjectView(R.id.tv_tool_remark)TextView remark;
        @InjectView(R.id.cb_tool_select)CheckBox select;

        public ToolsViewHolder(View view) {
            Views.inject(this, view);
        }
    }

}
