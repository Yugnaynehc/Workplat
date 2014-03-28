package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;

/**
 * Created by Feather on 14-3-28.
 * 220KV开关的定义
 */
public class Switch220KV extends SubDevice {

    private String type;

    public Switch220KV(String type) {
        setType(type);
        setName("220KV" + type + "开关");
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
        inspectContent.add("开关位置");
        inspectContent.add("气体压力");
        inspectContent.add("均压环");
        inspectContent.add("开关套管、支持瓷瓶");
        inspectContent.add("开关引线连接线夹");
        inspectContent.add("声响");
        inspectContent.add("接地");
        inspectContent.add("基础及支架");
        inspectContent.add("开关均压电容");
        inspectContent.add("加热器");
    }

    // TODO 有些括号内容是需要填写的
    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("记录分、合闸指示器指示位置（    ）（合“1”，分“0”），与实际运行状态一致，与后台遥测一致");
        inspectStandard.add("记录SF6气体压力是（    ）Mpa，与厂家规定压力一致（写出合格压力）。\n" +
                "密度继电器完好，正常，无异常报警信号\n" +
                "开关本体周围无刺激性气味及其它异味，异常声音");
        inspectStandard.add("均压环无锈蚀、变形、破损");
        inspectStandard.add("检查套管、支持瓷瓶清洁、完好，无破损、裂纹、电晕放电声");
        inspectStandard.add("1.检查开关引线及线夹压接牢固、接触良好，无变色，铜铝过渡部位无裂纹\n" +
                "2.利用检查导线及线夹的颜色变化、有无热气流上升、氧化加剧、夜间熄灯察看有无发红等方法，检查是否发热\n" +
                "3.雨雪天气，检查引线、线夹，对比有无积雪融化、水蒸气现象进行检查是否发热\n" +
                "4.以上检查，若需要鉴定，应使用测温仪对设备进行检测\n" +
                "5.检查高处的引线有无断股、无烧毁痕迹，可使用望远镜");
        inspectStandard.add("运行中无异常振动、声响");
        inspectStandard.add("接地连接良好,无锈蚀或油漆剥落");
        inspectStandard.add("基础完好，无下沉或倾斜；支架完好无锈蚀");
        inspectStandard.add("瓷瓶清洁、完好，无破损、裂纹、电晕放电声，无发红、过热现象");
        inspectStandard.add("功能正常（半年检查一次）");
    }

}
