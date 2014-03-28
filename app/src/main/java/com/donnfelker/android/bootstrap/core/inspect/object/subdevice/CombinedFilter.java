package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 结合滤波器的定义
 */
public class CombinedFilter extends SubDevice {

    public CombinedFilter() {
        setName("结合滤波器");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("设备编号");
        inspectContent.add("外表");
        inspectContent.add("接地刀闸");
        inspectContent.add("地刀支持瓷瓶");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("名称标志齐全、完好");
        inspectStandard.add("清洁、完好，固定牢固，箱门关闭良好");
        inspectStandard.add("在“分”位，闸口接触良好，接地装置完好");
        inspectStandard.add("固定牢固，无破裂、损伤现象");
    }
}

