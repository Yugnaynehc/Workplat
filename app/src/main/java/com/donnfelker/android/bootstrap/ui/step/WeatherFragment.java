package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import com.github.kevinsawicki.http.HttpRequest;
import static com.donnfelker.android.bootstrap.core.Constants.Http.*;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import butterknife.Views;

/**
 * Created by Feather on 14-4-3.
 */
public class WeatherFragment extends Fragment {

    @InjectView(R.id.upload) Button bupload;
    @InjectView(R.id.date) EditText date;
    @InjectView(R.id.person) EditText person;
    private SafeAsyncTask<Boolean> authenticationTask;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        Views.inject(this, view);
        bupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ln.d("upload click");
                /*
                try {
                    InputStream in = getActivity().getResources().getAssets().open("fontawesome-webfont.ttf");
                    final String query = String.format("?%s=%s", "filename", "123");
                    int request = HttpRequest.post(URL_UPLOAD + query).send(in).code();
                    Ln.d("upload return code = %s", request);
                } catch (Exception e) {
                    e.printStackTrace();
                    Ln.d("upload %s ", e.toString());
                }
                */
                authenticationTask = new SafeAsyncTask<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        HttpRequest request = HttpRequest.post(URL_UPLOAD);
                        request.part("upload", "testfile.txt", "text/plain", "this is a testfile");
                        return request.ok();
                        //Ln.d("upload return code = %s", request);
                    }
                };
                authenticationTask.execute();
            }
        });
        date.setText(new SimpleDateFormat("yyyy-M-d").format(new Date()));
        sharedPreferences = this.getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
        person.setText(sharedPreferences.getString(USER_INFO_NAME, ""));

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