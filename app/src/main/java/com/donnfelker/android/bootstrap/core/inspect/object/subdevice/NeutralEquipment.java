package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 中性点设备及避雷器的定义
 */
public class NeutralEquipment extends SubDevice {

    public NeutralEquipment() {
        setName("中性点设备及避雷器");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("中性点刀闸位置");
        inspectContent.add("中性点电流互感器");
        inspectContent.add("避雷器");
        inspectContent.add("220kV避雷器");
        inspectContent.add("110kV避雷器");
        inspectContent.add("接地装置");
    }

    // TODO 有一些检测是需要在一个巡检内容中填写多个值的。
    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("记录刀闸位置（    ）（合“1”，分“0”），符合电网运行要求,与变压器有关保护投退方式相对应.合闸时应合闸到位，接地连接两端接触良好，在巡检时将其视为带电设备。（正常运行方式下接地刀闸投入情况）");
        inspectStandard.add("1.套管无破损裂纹,引线连接良好\n" +
                "2.无渗漏油现象");
        inspectStandard.add("外绝缘完好、清洁，无裂纹、闪络痕迹及异常声响，放电计数器完好，能正确动作，接地扁铁接地良好，无锈蚀");
        inspectStandard.add("在线监测装置：泄漏电流有无显著增加(写出允许电流值)\n" +
                "\n" +
                "\n" +
                "避雷器动作次数");
        inspectStandard.add("在线监测装置：泄漏电流有无显著增加(写出允许电流值)\n" +
                "\n" +
                "\n" +
                "避雷器动作次数");
        inspectStandard.add("完好,无松脱及脱焊");
    }
}
