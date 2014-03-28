package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feather on 14-3-28.
 * 外部主导流部位
 */
public class StreamGuidancePart extends SubDevice{

    public StreamGuidancePart() {
        setName("外部主导流部位");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("主导流部位是否接触良好,有无发热现象");
        inspectContent.add("引线有无断股,线夹有无损伤接触是否良好");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.引线线夹压接应牢固,接触良好,无变色.变形,铜铝过渡部位无裂纹\n" +
                "2.主导流接触部位,看有无变色,有无氧化加剧,有无热气流上升.示温片有无融化变色现象,夜间有无发红等\n" +
                "3.雨雪天气,检查主导流接触部位,看有无积雪融化,水蒸气现象\n" +
                "4.以上检查,若需要鉴定,应使用测温仪对设备进行检测");
        inspectStandard.add("1.引线无断股,无烧伤痕迹\n" +
                "2.发现引线若有散股现象,应仔细辨认有无损伤,断股\n" +
                "3.检查母线.导线弧垂变化是否过大,对地.相间距离是否正常,有无挂落异物");
    }

}
