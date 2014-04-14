package com.donnfelker.android.bootstrap.ui.step;

import android.support.v4.app.Fragment;

import com.donnfelker.android.bootstrap.util.Ln;

/**
 * Created by feather on 14-4-11.
 */
public class RfidScanFragment extends Fragment implements ValidationFragment {

    public boolean validation() {
        Ln.d("Rfid Scan fragment validation");
        return true;
    }

    public void saveResult() {
        Ln.d("Rfid Scan fragment save result");
    }
}
