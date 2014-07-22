package com.donnfelker.android.bootstrap.ui;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.Injector;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.Forecast;
import com.donnfelker.android.bootstrap.core.ForecastResult;
import com.donnfelker.android.bootstrap.core.WeatherData;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.R;

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
public class ForecastFragment extends MenuFragment {

    private Forecast weather = null;
    private ForecastResult result = null;
    private WeatherData weatherData = null;
    private String[] date = null;
    private String[] sun = null;
    private String[] wind = null;
    private String[] temperature = null;
    private SimpleAdapter simpleAdapter = null;

    @Inject protected LogoutService logoutService;

    @InjectView(R.id.nowTemp)TextView nowTemp;
    @InjectView(R.id.date1)TextView date1;
    @InjectView(R.id.weather)TextView sun1;
    @InjectView(R.id.wind1)TextView wind1;
    @InjectView(R.id.temp)TextView temp;
    @InjectView(R.id.city)TextView city;
    @InjectView(R.id.weaimage1)ImageView image1;
    @InjectView(R.id.weatherlist)ListView list1;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_forecast,container,false);
        Views.inject(this, view);
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
        /*
        weather = ((MainActivity)getActivity()).getWeather();
        try {
            Ln.d("Weather: fragment onResume %s", weather.getDate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    public void getWeather() {
        weather = ((MainActivity)getActivity()).getWeather();

        if (weather == null) {
            Toast.makeText(getActivity(), "正在获取天气信息，请稍后重试", Toast.LENGTH_LONG).show();
        } else {
            Ln.d("weather: get");
            result = weather.getResults();
            weatherData = result.getWeather_data();
            date = weatherData.getDate();
            sun = weatherData.getWeather();
            wind = weatherData.getWind();
            temperature = weatherData.getTemperature();
            String str = date[0].replace("(","");
            str=str.replace(")","");
            String s[] = str.split(" ");

            nowTemp.setText(s[2]);
            city.setText(result.getCurrentCity());
            date1.setText(s[0]+" "+s[1]);
            sun1.setText(sun[0]);
            wind1.setText(wind[0]);

            temp.setText(temperature[0]);

            if(sun[0].equals("晴"))
            {
                image1.setImageResource(R.drawable.qing);
            }else
            {
                image1.setImageResource(R.drawable.yin);
            }
            List<Map<String,Object>> listItems = new ArrayList<Map<String,Object>>();
            for (int i = 1;i<date.length;i++)
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
                //listitem.put("image", returnBitMap(imgurl[i]));
                listItems.add(listitem);
            }
            simpleAdapter = new SimpleAdapter(getActivity(),listItems,R.layout.weatherlist,
                                              new String[]{"date","sun","wind","temperature","images"},
                                              new int[]{R.id.date,R.id.sun,R.id.wind,R.id.temperature,R.id.imagebaidu});
            list1.setAdapter(simpleAdapter);
        }
    }

    protected void logout() {
        logoutService.logout(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }

    /**
     * Force a refresh of the items displayed ignoring any cached items
     */
    protected void forceRefresh() {

        ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(true);
        getWeather();
        ((ActionBarActivity)getActivity()).setSupportProgressBarIndeterminateVisibility(false);
    }

}
