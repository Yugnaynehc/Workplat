package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.object.GSU;
import com.donnfelker.android.bootstrap.ui.WorkActivity;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.SafeAsyncTask;
import com.donnfelker.android.bootstrap.util.XMLBuilder;
import com.github.kevinsawicki.http.HttpRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.InjectView;
import butterknife.Views;

import static com.donnfelker.android.bootstrap.core.Constants.Http.URL_UPLOAD;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.USER_INFO;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.USER_INFO_NAME;

/**
 * Created by feather on 14-4-11.
 */
public class ToolsPrepareFragment extends Fragment {

    @InjectView(R.id.tools_list) protected ListView toolsList;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_tools, container, false);
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

}
