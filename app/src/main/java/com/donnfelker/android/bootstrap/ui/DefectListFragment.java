package com.donnfelker.android.bootstrap.ui;

import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.ListView;

import com.donnfelker.android.bootstrap.BootstrapServiceProvider;
import com.donnfelker.android.bootstrap.Injector;
import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.authenticator.LogoutService;
import com.donnfelker.android.bootstrap.core.Defect;
import com.github.kevinsawicki.wishlist.SingleTypeAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;


/**
 * Created by Feather on 14-4-1.
 */
public class DefectListFragment extends  ItemListFragment<Defect> {

    @Inject protected BootstrapServiceProvider serviceProvider;
    @Inject protected LogoutService logoutService;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.inject(this);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setEmptyText(R.string.no_defects);
    }

    @Override
    protected void configureList(final Activity activity, final ListView listView) {
        super.configureList(activity, listView);

        listView.setFastScrollEnabled(true);
        listView.setDividerHeight(0);

        getListAdapter().addHeader(activity.getLayoutInflater()
                .inflate(R.layout.defect_list_item_labels, null));
    }

    @Override
    protected LogoutService getLogoutService() {
        return logoutService;
    }

    @Override
    public Loader<List<Defect>> onCreateLoader(final int id, final Bundle args) {
        final List<Defect> initialItems = items;
        return new ThrowableLoader<List<Defect>>(getActivity(), items) {
            @Override
            public List<Defect> loadData() throws Exception {
                try {
                    if (getActivity() != null) {
                        List<Defect> t = new ArrayList<Defect>();
                        for (int i=0; i<5; ++i) {
                            t.add(new Defect());
                            t.get(i).setDeviceid("主变");
                            t.get(i).setDescription("设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损设备劳损");
                        }
                      return t;
                        //return serviceProvider.getService(getActivity()).getDefect();
                    } else {
                        return Collections.emptyList();
                    }

                //} catch (OperationCanceledException e) {
                } catch (Exception e) {
                    Activity activity = getActivity();
                    if (activity != null)
                        activity.finish();
                    return initialItems;
                }
            }
        };

    }

    public void onListItemClick(final ListView l, final View v, final int position, final long id) {

    }

    @Override
    public void onLoadFinished(final Loader<List<Defect>> loader, final List<Defect> items) {
        super.onLoadFinished(loader, items);

    }

    @Override
    protected int getErrorMessage(final Exception exception) {
        return R.string.error_loading_defects;
    }

    @Override
    protected SingleTypeAdapter<Defect> createAdapter(final List<Defect> items) {
        return new DefectListAdapter(getActivity().getLayoutInflater(), items);
    }
}
