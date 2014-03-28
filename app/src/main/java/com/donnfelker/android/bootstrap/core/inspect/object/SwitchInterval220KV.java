package com.donnfelker.android.bootstrap.core.inspect.object;

import com.donnfelker.android.bootstrap.core.inspect.object.subdevice.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feather on 14-3-28.
 * 220KV间隔的定义
 */
public class SwitchInterval220KV extends Device {

    private List<SubDevice> subDevice;

    public SwitchInterval220KV() {
        setName("220KV间隔");
        subDevice = new ArrayList<SubDevice>();
        subDevice.add(new Switch220KV("型号"));
        subDevice.add(new Mechanism("型号"));
        subDevice.add(new CurrentTransformer("型号"));
        subDevice.add(new DisconnectingSwitch("型号"));
        subDevice.add(new CouplingCapacitor());
        subDevice.add(new CombinedFilter());
        subDevice.add(new LineTrap());
        subDevice.add(new LinePT());
    }
}
