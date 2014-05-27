package com.donnfelker.android.bootstrap.core.inspect.result;

/**
 * Created by feather on 14-4-13.
 */
public class InspectTool {
    private String name;
    private String type;
    private String num;

    public InspectTool(String name, String type, String num) {
        this.name = name;
        this.type = type;
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getNum() {
        return num;
    }

    public String toString() {
        return "name: " + name + "  type: " + type + "  num: " + num;
    }
}