package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 阻波器的定义
 */
public class LineTrap extends SubDevice {

    public LineTrap() {
        setName("阻波器");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("外表");
        inspectContent.add("内部");
        inspectContent.add("引线及各接头");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("固定牢固，无较大摆动，整体无变形现象");
        inspectStandard.add("无异常响声，内部无蜂窝、鸟巢，内部电容器及避雷器无倾倒现象");
        inspectStandard.add("引线无断股、无散股，各接头压接良好、无过热变色现象");
    }
}
