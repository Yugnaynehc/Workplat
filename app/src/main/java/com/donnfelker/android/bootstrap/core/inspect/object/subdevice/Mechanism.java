package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 操作机构的定义
 */
public class Mechanism extends SubDevice {

    private String type;

    public Mechanism(String type) {
        setType(type);
        setName("操作机构 " + type);
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
        inspectContent.add("操作机构箱");
        inspectContent.add("储能电机");
        inspectContent.add("油（气）压力表");
        inspectContent.add("驱潮器、储能气泵");
        inspectContent.add("开关声音");
        inspectContent.add("气动机构供气系统");
        inspectContent.add("油泵电源开关");
        inspectContent.add("计数器电源开关");
        inspectContent.add("控制方式选择");
        inspectContent.add("密度继电器");
        inspectContent.add("端子箱");
        inspectContent.add("");
    }

    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.检查机构无异常声音、异味、电容无漏液现象\n" +
                "2.箱内密封及照明良好，加热器工作正常\n" +
                "3.分、合闸弹簧储能正常");
        inspectStandard.add("储能电机运行正常无异响、外壳无明显锈蚀");
        inspectStandard.add("外观无损坏、指示无异常，并记录油（气）压力为（      ）Mpa，与厂家规定压力一致（写出合格压力）");
        inspectStandard.add("1.检查驱潮器完好，工作正常\n" +
                "2.储能电源开关投入\n" +
                "3.泵的补压时间满足厂家技术条件要求（写出厂家要求）\n" +
                "4.液压(气动)机构打压频度符合厂家规定的24小时内打压次数（写出具体次数）");
        inspectStandard.add("开关应无任何异常声音");
        inspectStandard.add("1.压缩机、气水分离器、电磁排水阀运行正常\n" +
                "2.储气缸无泄漏");
        inspectStandard.add("记录实际位置（    ）（投入“1”，退出“0”），正常情况下应在投入位置");
        inspectStandard.add("记录实际位置（    ）（投入“1”，退出“0”），正常情况下应在“投入”位置");
        inspectStandard.add("记录“远方/就地”切换开关的位置（    ）（远方“1”，就地“0”）,正常情况下应在“远方”位置");
        inspectStandard.add("记录巡检时的实际值（    ）Mpa，与厂家规定压力一致（写出合格压力）。");
        inspectStandard.add("1.端子箱内清洁、门关闭严密\n" +
                "2.二次线无松脱及发热变色现象\n" +
                "3.电缆二次线孔洞封堵严密\n" +
                "4.二次接线元件、电缆、隔离开关、断路器、电流互感器等标志正确、清晰\n" +
                "5.端子箱接地良好");
        inspectStandard.add("液压机构油压正常、气动机构气压正常、弹簧机构弹簧位置正确");
    }
}
