package com.donnfelker.android.bootstrap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Substation;

/**
 * Created by feather on 14-5-16.
 */
public class ApplyFragment extends Fragment {
    @InjectView(R.id.date) EditText date;
    @InjectView(R.id.type) Spinner type;
    @InjectView(R.id.reason) EditText reason;
    @InjectView(R.id.submit) BootstrapButton submit;
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item);
        adapter.add(getResources().getString(R.string.inspect_normal_total));
        adapter.add(getResources().getString(R.string.inspect_normal_daily));
        adapter.add(getResources().getString(R.string.inspect_special_thunderstorm));
        adapter.add(getResources().getString(R.string.inspect_special_snowy));
        adapter.add(getResources().getString(R.string.inspect_special_foggy));
        adapter.add(getResources().getString(R.string.inspect_special_windy));
        adapter.add(getResources().getString(R.string.inspect_special_nightlight));
        adapter.add(getResources().getString(R.string.inspect_special_bugtrace));
        adapter.add(getResources().getString(R.string.inspect_job_infraredtesting));
        adapter.add(getResources().getString(R.string.inspect_job_switchcooler));
        adapter.add(getResources().getString(R.string.inspect_job_emergencylightswitch));
        adapter.add(getResources().getString(R.string.inspect_job_batteryperiodictesting));
        adapter.add(getResources().getString(R.string.inspect_job_deviceperiodictestingrotation));
        adapter.add(getResources().getString(R.string.inspect_job_deviceperiodicmaintance));
        adapter.add(getResources().getString(R.string.inspect_job_barriergateoperate));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_apply, container, false);
        Views.inject(this, view);
        date.setText(new SimpleDateFormat("yyyy-M-d").format(new Date()));
        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将adapter添加到spinner中
        type.setAdapter(adapter);
        //添加Spinner事件监听
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if (submitApplication()) {
                    reason.setText("");
                    type.setSelection(0);
                }
                else
                    showError();
                */

            }
        });
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

    /*
    private boolean submitApplication() {

    }
    */

    private void showError() {

    }

}
