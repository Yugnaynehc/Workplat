package com.donnfelker.android.bootstrap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Forecast;
import com.donnfelker.android.bootstrap.core.ForecastResult;
import com.donnfelker.android.bootstrap.core.WeatherData;
import com.donnfelker.android.bootstrap.util.Ln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by feather on 14-6-28.
 * 天气预报信息页面，在进入应用之后的滑动页面中的第一页显示。
 */
public class ForecastFragment extends Fragment {

    private Forecast weather = null;
    private ForecastResult result = null;
    private WeatherData weatherData = null;
    private String[] date = null;
    private String[] sun = null;
    private String[] wind = null;
    private String[] temperature = null;
    @InjectView(R.id.weatherlist)ListView list;
    @InjectView(R.id.submit)BootstrapButton button;
    private List<Map<String,Object>> listItems = null;
    private SimpleAdapter simpleAdapter = null;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        Views.inject(this, view);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWeather();
            }
        });

        listItems = new ArrayList<Map<String,Object>>();
        simpleAdapter = new SimpleAdapter(getActivity(),listItems,R.layout.weatherlist,
                new String[]{"date","sun","wind","temperature","images"},
                new int[]{R.id.date,R.id.sun,R.id.wind,R.id.temperature,R.id.imagebaidu});
        list.setAdapter(simpleAdapter);
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
        weather = ((MainActivity)getActivity()).getWeather();
        try {
        Ln.d("Weather: fragment onResume %s", weather.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWeather() {
        weather = ((MainActivity)getActivity()).getWeather();
        if (weather == null) {
            Toast.makeText(getActivity(), "正在获取天气信息，请稍后重试", Toast.LENGTH_LONG).show();
        } else {
            result = weather.getResults();
            weatherData = result.getWeather_data();
            date = weatherData.getDate();
            sun = weatherData.getWeather();
            wind = weatherData.getWind();
            temperature = weatherData.getTemperature();

            listItems.clear();
            for (int i = 0;i<date.length;i++)
            {
                Map<String,Object> listitem =new HashMap<String,Object>();
                listitem.put("date", date[i]);
                listitem.put("sun", sun[i]);
                listitem.put("wind", wind[i]);
                listitem.put("temperature", temperature[i]);
                if(sun[i].equals("晴"))
                {
                    listitem.put("images", R.drawable.qing);
                }else
                {
                    listitem.put("images", R.drawable.yin);
                }
                listItems.add(listitem);
            }
            simpleAdapter.notifyDataSetChanged();
        }
    }
}
