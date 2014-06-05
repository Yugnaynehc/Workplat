package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.inspect.security.Security;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.SecurityFactory;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-4-11.
 */
public class SecurityFragment extends Fragment implements ValidationFragment {

    protected ListView securityList;
    private Security security;
    private SparseBooleanArray selectMap;
    private SecurityAdapter securityAdapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_security, container, false);
        securityList = (ListView)view.findViewById(R.id.security_list);
        String securityType = ((WorkActivity)getActivity()).getWork().getType();
        Ln.d("securityType: %s", securityType);
        security = SecurityFactory.get(securityType);
        securityAdapter = new SecurityAdapter(getActivity());
        securityList.setAdapter(securityAdapter);
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
        Ln.d("security fragment validation");
        return true;
    }

    public void saveResult() {
        security.setSelect(selectMap);
        ((WorkActivity)getActivity()).getResult().setSecurity(security);
        Ln.d("security fragment save result");
    }

    private class SecurityAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;

        public SecurityAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(this.context);
            selectMap = initSelect();
        }

        private SparseBooleanArray initSelect() {
            SparseBooleanArray select = new SparseBooleanArray();
            for (int i=0; i<security.getCount(); ++i)
                select.put(i, false);
            return select;
        }


        @Override
        public int getCount() {
            return security.getCount();
        }

        @Override
        public Object getItem(int position) {
            return security.getPoint()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final SecurityViewHolder holder;
            String securityNo = String.valueOf(position + 1);
            String securityPoint = security.getPoint()[position];
            String securityMeasure = security.getMeasure()[position];

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.security_list_item, null);
                holder = new SecurityViewHolder();
                holder.no = (TextView)convertView.findViewById(R.id.tv_security_no);
                holder.point = (TextView)convertView.findViewById(R.id.tv_security_point);
                holder.measure = (TextView)convertView.findViewById(R.id.tv_security_measure);
                holder.select = (CheckBox)convertView.findViewById(R.id.cb_security_select);
                convertView.setTag(holder);
            }
            else {
                holder = (SecurityViewHolder)convertView.getTag();
            }

            holder.no.setText(securityNo);
            holder.point.setText(securityPoint);
            holder.measure.setText(securityMeasure);
            holder.select.setOnCheckedChangeListener(null);
            holder.select.setChecked(selectMap.get(position));
            holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    selectMap.put(position, isChecked);
                }
            });
            return convertView;
        }
    }

    private class SecurityViewHolder {
        TextView no;
        TextView point;
        TextView measure;
        CheckBox select;
    }
}
