package com.donnfelker.android.bootstrap.core.inspect.security;

import android.util.SparseBooleanArray;

import java.io.Serializable;

/**
 * Created by Feather on 14-3-25.
 */
public abstract class Security implements Serializable {

    private SparseBooleanArray select;

    public abstract String[] getPoint();
    public abstract String[] getMeasure();
    public abstract int getCount();

    public void setSelect(SparseBooleanArray a) {
        // TODO instead of reference by clone?
        select = a;
    }

    public SparseBooleanArray getSelect() {
        return select;
    }
}
