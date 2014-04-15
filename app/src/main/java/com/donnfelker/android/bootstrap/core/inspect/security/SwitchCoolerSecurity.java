package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class SwitchCoolerSecurity extends Security {

    public static String danger[] = {
        "低压触电",
        "主变瓦斯保护误动跳闸",
        "冷控失电造成保护误动",
    };

    public static String measure[] = {
        "双人工作，设专人监护；漏电保护器工作正常。",
        "按作业指导卡程序作业。",
        "工作中专人检查保护信号，备用电源不能自投时，手动投入工作电源",
    };

    public String[] getPoint() {
        return danger;
    }

    public String[] getMeasure() {
        return measure;
    }

    public int getCount() { return  3;}

}
