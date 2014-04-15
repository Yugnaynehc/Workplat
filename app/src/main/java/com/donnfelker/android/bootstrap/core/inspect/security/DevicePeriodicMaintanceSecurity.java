package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class DevicePeriodicMaintanceSecurity extends Security {

    public static String danger[] = {
        "低压触电",
        "误碰误动设备",
        "高空坠落",
        "气体中毒或引发爆炸",
    };

    public static String measure[] = {
        "工具包扎绝缘胶带，穿绝缘鞋，两人工作",
        "重视维护前技术交底工作，且选派技术力量好的值班员担任监护人员",
        "使用人字应专人扶稳，开度符合规定",
        "工作中严禁烟火，开启通风装置",
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  4;}
}
