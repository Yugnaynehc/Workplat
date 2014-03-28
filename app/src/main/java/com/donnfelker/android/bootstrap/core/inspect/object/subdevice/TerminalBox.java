package com.donnfelker.android.bootstrap.core.inspect.object.subdevice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feather on 14-3-28.
 * 端子箱的定义
 */
public class TerminalBox extends SubDevice {

    public TerminalBox() {
        setName("端子箱");
        setInspectContent();
        setInspectStandard();
    }

    @Override
    protected void setInspectContent() {
        inspectContent = new ArrayList<String>();
        inspectContent.add("端子箱");
    }

    @Override
    protected void setInspectStandard() {
        inspectStandard = new ArrayList<String>();
        inspectStandard.add("1.箱门关闭严密，密封良好，二次接线紧固，箱内无焦臭味,电源正常，加热器工作正常\n" +
                "2.端子箱接地良好");
    }

}
