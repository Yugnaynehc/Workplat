package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 三侧套管的定义
 */
public class JacketedPipe extends SubDevice {

    public JacketedPipe() {
        setName("三侧套管");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    public void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("油位");
        inspectContent.add("油色");
        inspectContent.add("绝缘子");
        inspectContent.add("法兰");
        inspectContent.add("高、中压套管末屏");
        inspectContent.add("引线");
        inspectContent.add("声响");
    }

    @Override
    public void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.油位应在上、下油位标示线之间\n" +
                "2.油位计油位不容易看清楚时,可采取以下方法:\n" +
                "1)多角度观察\n" +
                "2)将两个温差较大的时刻所观察的现象相比较\n" +
                "3)与其它设备的同类油位相比较\n" +
                "4)比较油位计不同亮度下的底色板颜色\n" +
                "3.油位计应无破损和渗漏油,没有影响察看油位的油垢");
        inspectStandard.add("正常油色应为透明的淡黄色");
        inspectStandard.add("应清洁,无破损,裂纹,无放电声，无电蚀痕迹或破损，无影响设备运行的异物");
        inspectStandard.add("应无裂纹和严重腐蚀");
        inspectStandard.add("接地良好，无异常声响");
        inspectStandard.add("高压引线接连接正常");
        inspectStandard.add("无异常声响或放电声");
    }

}
