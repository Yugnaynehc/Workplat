package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class BatteryPeriodicTestingSecurity extends Security {

    public static String danger[] = {
        "直流短路",
        "蓄电池室未排气造成人员窒息或中毒",

    };

    public static String measure[] = {
        "两人工作；使用合格的表笔，并注意表笔金属部位之间的距离。",
        "进入蓄电室前应开启风机强制通风3分钟。",
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  2;}
}
