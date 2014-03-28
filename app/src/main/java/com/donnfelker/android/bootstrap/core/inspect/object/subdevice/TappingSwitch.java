package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 分接开关的定义
 */
public class TappingSwitch extends SubDevice {

    public TappingSwitch() {
        setName("分接开关");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("档位");
        inspectContent.add("油位");
        inspectContent.add("呼吸器");
        inspectContent.add("防爆膜");
        inspectContent.add("在线滤油装置");
        inspectContent.add("调压瓦斯");
    }

    // TODO 有一些检测是需要在一个巡检内容中填写多个值的。
    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.记录变压器运行档位，与后台遥测档位一致，分相变压器：A相（ ）档、B相（ ）档、C相（ ）档\n" +
                "2.分接开关总切换次数（   ）次，应低于厂家规定的*****次\n" +
                "3.控制回路正常，传动机构无卡涩，无滑档现象\n" +
                "4.调压控制箱电源开启，箱门密封良好");
        inspectStandard.add("分接开关油位正常，无过高过低现象，无渗漏。环境温度（  ）℃，油位指示（  ）");
        inspectStandard.add("1.呼吸器油封正常，硅胶潮解变色部分未超过总量的2/3\n" +
                "2.呼吸器外部无油迹，油杯完好,油位正常");
        inspectStandard.add("防爆膜完好、密封完好，无喷油、漏油现象");
        inspectStandard.add("1.在线滤油装置压力正常，无渗、漏油\n" +
                "2.滤油装置电源正常开启，各阀门正常开启\n" +
                "3.滤油装置工作方式在（  ）位置，动作次数（ ）次");
        inspectStandard.add("1.瓦斯继电器内应充满油,油色应为淡黄色透明,无渗漏油.瓦斯继电器内应无气体(气泡)\n" +
                "2.瓦斯继电器防雨措施完好,防雨罩牢固\n" +
                "3.瓦斯继电器的引出二次电缆应无油迹和腐蚀现象,无松脱");
    }
}
