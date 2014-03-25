package com.donnfelker.android.bootstrap.core.inspect.security;

/**
 * Created by Feather on 14-3-25.
 */
public class EmergencyLightSwitchSecurity extends Security{

    public static String danger[] = {
        "",
        "人员低压触电",
        "交直流短路",
    };

    public static String measure[] = {
        "",
        "使用合格的漏电保护器；两人工作，专人监护。",
        "做好交直流短路后的事故处理预案，并按照预案执行。",
    };
}
