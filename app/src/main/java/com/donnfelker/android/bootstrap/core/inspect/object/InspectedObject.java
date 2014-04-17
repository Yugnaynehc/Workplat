package com.donnfelker.android.bootstrap.core.inspect.object;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Feather on 14-3-26.
 */
public abstract class InspectedObject implements Serializable {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
