package com.donnfelker.android.bootstrap.core.inspect.result;

import java.io.Serializable;

/**
 * Created by feather on 14-4-13.
 */
public class InspectTool implements Serializable {
    private String name;
    private String type;
    private String num;

    public InspectTool() {}

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

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String toString() {
        return "name: " + name + "  type: " + type + "  num: " + num;
    }
}
