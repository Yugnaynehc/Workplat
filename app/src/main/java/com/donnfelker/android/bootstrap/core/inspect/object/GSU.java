package com.donnfelker.android.bootstrap.core.inspect.object;

import com.donnfelker.android.bootstrap.core.inspect.object.subdevice.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feather on 14-3-28.
 * 主变的定义
 */
public class GSU extends Device {

    public GSU() {
        setName("主变");
        subDevice = new ArrayList<SubDevice>();
        subDevice.add(new Block());
        subDevice.add(new TappingSwitch());
        subDevice.add(new JacketedPipe());
        subDevice.add(new StreamGuidancePart());
        subDevice.add(new AirCoolingSystem());
        subDevice.add(new NeutralEquipment());
        subDevice.add(new TerminalBox());
    }

}
