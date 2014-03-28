package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 风冷系统的定义
 */
public class AirCoolingSystem extends SubDevice {

    public AirCoolingSystem() {
        setName("风冷系统");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("风扇");
        inspectContent.add("潜油泵");
        inspectContent.add("散热器");
        inspectContent.add("风冷系统运行正常");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("变压器风扇运转方向正常,无异常声音,扇叶应无抖动、碰壳。");
        inspectStandard.add("运转方向正确, 无异常声音,无渗漏油，油流指示器指示正确。");
        inspectStandard.add("散热装置清洁,散热片不应有过多的积灰等附着脏物");
        inspectStandard.add("冷却器工作、辅助、备用组数应符合制造厂和现场运行规程的规定,位置正确,相应位置指示灯正确。冷却器冷却器开启组数（    ），冷却器电源取自站用电（ ）段，工作方式：第一组（ ）、第二组（ ）、第三组（ ）、第四组（ ）");
    }
}
