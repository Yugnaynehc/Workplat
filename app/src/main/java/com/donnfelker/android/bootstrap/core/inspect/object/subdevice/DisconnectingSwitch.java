package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 隔离开关的定义
 */
public class DisconnectingSwitch extends SubDevice {

    private String type;

    public DisconnectingSwitch(String type) {
        setType(type);
        setName("隔离开关 " + type);
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
        inspectContent.add("触头、引线、线夹、接地线部位");
        inspectContent.add("基础");
        inspectContent.add("支柱绝缘子");
        inspectContent.add("传动部件");
        inspectContent.add("操作机构箱");
        inspectContent.add(type + "接地刀闸");
    }

    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.导线无断股\n" +
                "2.合闸时应合闸到位，触头接触良好无发红过热现象\n" +
                "3.观察接头有无热气流、变色严重、氧化加剧现象\n" +
                "4.无挂落异物\n" +
                "5.高压引线连接牢固、无异常\n" +
                "6．接地线连接牢固、无异常");
        inspectStandard.add("接地装置完好，基础无下沉、倾斜");
        inspectStandard.add("应完好、清洁、无破损、放电痕迹");
        inspectStandard.add("传动部件外观是否有异常");
        inspectStandard.add("1.箱内清洁、箱门关闭严密，间隔标识清晰，箱内分、合闸指示明确并与后台遥测一致，照明完好，加热器工作正常\n" +
                "2.二次线无松脱及发热变色现象\n" +
                "3.电缆二次线孔洞封堵严密\n" +
                "4.二次接线元件、电缆、隔离开关、断路器、电流互感器等标志正确、清晰\n" +
                "5.端子箱接地良好\n" +
                "6. 记录“远方/就地”切换开关的位置为（    ）（远方“1”，退出“0”，就地“2”）,正常情况下应在“退出”位置，操作电源、电机电源及加热电源正常开启");
        inspectStandard.add("在“分”位，助力弹簧无断股，闭锁良好，接地装置完好，接地辫子无断股和散股，接地刀杆应完全放平");
    }
}
