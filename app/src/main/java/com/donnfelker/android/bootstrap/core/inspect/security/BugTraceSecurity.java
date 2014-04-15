package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class BugTraceSecurity extends Security {

    public static String danger[] = {
        "擅自打开设备网门及移动临时安全围栏，跨越设备固定围栏",
        "高压设备发生接地时，保持距离不够，造成人员伤害",
    };

    public static String measure[] = {
        "巡检时不得进行其他工作（严禁进行电气工作），不得移开或越过遮拦",
        "高压设备发生接地时，室内不得接近故障点4 m以内，室外室内不得靠近故障点8 m以内，进入上述范围人员必须穿绝缘靴，接触设备的外壳和架构时，必须带绝缘手套",
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  2;}
}
