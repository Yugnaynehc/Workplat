package com.donnfelker.android.bootstrap.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.OperationCanceledException;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.Injector;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.Work;
import com.donnfelker.android.bootstrap.core.inspect.result.Result;
import com.donnfelker.android.bootstrap.util.Ln;
import com.donnfelker.android.bootstrap.util.ResultXmlBuilder;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;
import com.github.kevinsawicki.wishlist.Toaster;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.WORK_ITEM;
import static com.donnfelker.android.bootstrap.core.Constants.Extra.RESULT_ITEM;
import static com.donnfelker.android.bootstrap.core.Constants.UPreference.*;

/**
 * Created by Feather on 14-3-17.
 */
public class WorkListFragment extends ItemListFragment<Work> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setEmptyText(R.string.no_works);
    }

    @Override
    protected void configureList(Activity activity, ListView listView) {
        super.configureList(activity, listView);
        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);
        getListAdapter().
            addHeader(activity.getLayoutInflater()
                      .inflate(R.layout.work_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }


    @Override
    public void onDestroyView() {
        setListAdapter(null);
        super.onDestroyView();
    }

    @Override
    public Loader<List<Work>> onCreateLoader(int id, Bundle args) {
        final List<Work> initialItems = items;
        return new ThrowableLoader<List<Work>>(getActivity(), items) {

            @Override
            public List<Work> loadData() throws Exception {
                try {
                    if (getActivity() != null) {

                        SharedPreferences sharedPreferences;
                        sharedPreferences = getActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE);
                        String substationID = sharedPreferences.getString(USER_INFO_SUBSTATION_ID, "");
                        return serviceProvider.getService(getActivity()).getWorks(substationID);
                    }
                    else {
                        return Collections.emptyList();
                    }
                } catch (Exception e) {
                    Toaster.showLong(getActivity(), getActivity().getResources().getString(R.string.get_work_error));
                    return initialItems;
                }
            }
        };
    }

    @Override
    protected SingleTypeAdapter<Work> createAdapter(List<Work> items) {
        return new WorkListAdapter(getActivity(), items);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        final Work work = ((Work) l.getItemAtPosition(position));
        Intent intent = new Intent(getActivity(), WorkActivity.class);
        intent.putExtra(WORK_ITEM, work);
        Ln.d("get result %d", work.getStatus());
        if (work.getStatus() == 1) {
            try {
                Result result = ResultXmlBuilder.GET(getActivity(), work.getPlanid());
                intent.putExtra(RESULT_ITEM, result);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
        startActivity(intent);
    }

    @Override
    protected int getErrorMessage(Exception exception) {
        return R.string.error_loading_works;
    }

    @Override
    public void onResume() {
        super.onResume();
        forceRefresh();
    }
}
