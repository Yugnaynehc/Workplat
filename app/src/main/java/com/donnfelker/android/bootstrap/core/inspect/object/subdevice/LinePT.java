package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 线路PT的定义
 */
public class LinePT extends SubDevice {

    public LinePT() {
        setName("线路PT");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("设备编号");
        inspectContent.add("瓷瓶");
        inspectContent.add("引线及各接头");
        inspectContent.add("二次接线及二次电压");
        inspectContent.add("基础");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("名称标志齐全、完好无锈蚀");
        inspectStandard.add("清洁完好，无破裂、无损伤放电现象，油位正确，无渗、漏油现象，内部无异音、异味");
        inspectStandard.add("引线无断股、无散股，各接头压接良好、无过热变色现象");
        inspectStandard.add("PT的二次接线盒应密封良好，不能有任何螺丝脱落现象，二次电压应正常，带电指示灯指示正确，电压继电器动作正确，二次空气开关位置正确。");
        inspectStandard.add("接地装置完好，基础无下沉、倾斜");
    }
}
