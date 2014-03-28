package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 耦合电容器的定义
 */
public class CouplingCapacitor extends SubDevice {

    public CouplingCapacitor() {
        setName("耦合电容器");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("设备编号");
        inspectContent.add("瓷件");
        inspectContent.add("引线及各接头");
        inspectContent.add("基础");
        inspectContent.add("渗漏油");
        inspectContent.add("接地");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("名称标志齐全、完好");
        inspectStandard.add("完好，无破裂，无损伤放电现象，无渗、漏油现象");
        inspectStandard.add("引线无断股、无散股，上、下接头压接良好、无过热变色现象");
        inspectStandard.add("接地装置完好，基础无下沉、倾斜");
        inspectStandard.add("电容器无油渗漏");
        inspectStandard.add("接地线连接良好，无松动");
    }
}
