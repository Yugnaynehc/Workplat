package com.donnfelker.android.bootstrap.core.inspect.object;

import com.donnfelker.android.bootstrap.core.inspect.object.subdevice.SubDevice;

import java.util.List;

/**
 * Created by Feather on 14-3-26.
 */
public abstract class Device extends InspectedObject {

    protected List<SubDevice> subDevice;

    public List<SubDevice> getSubDevice() { return this.subDevice; }
}
