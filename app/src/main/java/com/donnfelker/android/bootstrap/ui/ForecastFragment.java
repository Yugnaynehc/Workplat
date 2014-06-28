package com.donnfelker.android.bootstrap.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Forecast;
import com.donnfelker.android.bootstrap.util.Ln;

import javax.inject.Inject;

import butterknife.Views;

/**
 * Created by feather on 14-6-28.
 * 天气预报信息页面，在进入应用之后的滑动页面中的第一页显示。
 */
public class ForecastFragment extends Fragment {

    private Forecast weather;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
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
        weather = ((MainActivity)getActivity()).getWeather();
        if (weather != null)
            Ln.d("Weather: %s", weather.getStatus());
        else
            Ln.d("Weather: null");
    }

    @Override
    public void onResume() {
        super.onResume();
        weather = ((MainActivity)getActivity()).getWeather();
        if (weather != null)
            Ln.d("Weather: %s", weather.getStatus());
        else
            Ln.d("Weather: null");
    }
}
