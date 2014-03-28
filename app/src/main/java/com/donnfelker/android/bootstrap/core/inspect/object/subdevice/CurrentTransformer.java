package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 电流互感器的定义
 */
public class CurrentTransformer extends SubDevice {

    private String type;

    public CurrentTransformer(String type) {
        setType(type);
        setName("电流互感器 " + type);
        setInspectContent();
        setInspectStandard();
    }

    private void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("油位");
        inspectContent.add("瓷套");
        inspectContent.add("接头");
        inspectContent.add("二次接线盒、放油阀");
        inspectContent.add("末屏");
        inspectContent.add("引线、接地");
        inspectContent.add("声响");
        inspectContent.add("膨胀器");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("油位指示在上下限之间，无渗漏");
        inspectStandard.add("完好，无裂纹、损伤、放电现象，无影响设备运行的异物");
        inspectStandard.add("无变色，压接良好，无过热变色现象");
        inspectStandard.add("1.放油阀关闭严密，无渗漏油\n" +
                "2.二次接线盒无油迹");
        inspectStandard.add("接地良好，无渗油");
        inspectStandard.add("高压引线、接地线等连接正常");
        inspectStandard.add("本体无异常声响或放电声");
        inspectStandard.add("膨胀器无异常升高");
    }
}
