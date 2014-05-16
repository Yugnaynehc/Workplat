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

import com.donnfelker.android.bootstrap.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ArrayAdapter<String>(this.getActivity(),
                android.R.layout.simple_spinner_item);

        for (int i=0; i< Substation.INSPECT_TYPE_LIST.length; ++i) {
            String str = "";
            switch (Substation.indexOf(Substation.INSPECT_TYPE_LIST[i])) {
                case 1:
                    str = "全面巡检"; break;
                case 2:
                    str = "日常巡检"; break;
                case 3:
                    str = "雷雨特殊巡检"; break;
                case 4:
                    str = "雪天特殊巡检"; break;
                case 5:
                    str = "大雾特殊巡检"; break;
                case 6:
                    str = "大风特殊巡检"; break;
                case 7:
                    str = "夜间熄灯特殊巡检"; break;
                case 8:
                    str = "设备异常缺陷跟踪特殊巡检"; break;
                case 9:
                    str = "红外线测试作业"; break;
                case 10:
                    str = "主变冷却器切换试验作业"; break;
                case 11:
                    str = "事故照明切换作业"; break;
                case 12:
                    str = "蓄电池定期测试作业"; break;
                case 13:
                    str = "轮换作业"; break;
                case 14:
                    str = "设备定期维护作业"; break;
                case 15:
                    str = "道闸操作作业"; break;
                default:
                    break;
            }
            adapter.add(str);
        }
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
        type.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()
        {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
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

}
