package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import com.donnfelker.android.bootstrap.core.inspect.object.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feather on 14-3-26.
 */
public abstract class SubDevice extends Device {

    protected List<String> inspectContent;
    protected List<String> inspectStandard;
    protected List<String> inspectResult;

    public List<String> getInspectContent() {
        return inspectContent;
    }

    public List<String> getInspectStandard() {
        return inspectStandard;
    }

    public List<String> getInspectResult() {
        return inspectResult;
    }

    protected abstract void setInspectContent();

    protected abstract void setInspectStandard();

    public void setInspectResult(List<String> result) {
        inspectResult = new ArrayList<String>(result);
    }
    
}
