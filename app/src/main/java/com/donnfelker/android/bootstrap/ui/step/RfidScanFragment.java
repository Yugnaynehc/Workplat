package com.donnfelker.android.bootstrap.ui.step;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.util.Ln;

/**
 * Created by feather on 14-4-11.
 */
public class RfidScanFragment extends Fragment implements ValidationFragment {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStete) {
        super.onCreateView(inflater, container, savedInstanceStete);
        View view = inflater.inflate(R.layout.fragment_scan, container, false);

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
        Ln.d("Rfid Scan fragment validation");
        return true;
    }

    public void saveResult() {
        Ln.d("Rfid Scan fragment save result");
    }
}
