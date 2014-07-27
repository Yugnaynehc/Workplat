package com.donnfelker.android.bootstrap.core.inspect.result;

import android.support.v7.appcompat.R;

import com.donnfelker.android.bootstrap.core.inspect.security.Security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by feather on 14-4-13.
 * result of inspect
 * 记录巡检结果的类
 */
public class Result implements Serializable {

    private String type;                // type of inspect
    private String resultid;            // result id
    private List<InspectTool> tools;         // using tools list of inspect
    private InspectEnvironment env;     // environment information of inspect
    private Security security;          // security information of inspect
    private List<DeviceResult> deviceResults;        // inspectd devices information list of inspect
    protected String objectId;

    public Result() {

    }

    public Result(String type, String resultid, List<InspectTool> tools, InspectEnvironment env, Security security, List<DeviceResult> deviceResults) {
        this.type = type;
        this.resultid = resultid;
        this.tools = tools;
        this.env = env;
        this.security = security;
        this.deviceResults = deviceResults;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResultid() { return resultid; }

    public void setResultid(String resultid) { this.resultid = resultid; }

    public List<InspectTool> getTools() {
        return tools;
    }

    public void setTools(List<InspectTool> tools) {
        this.tools = tools;
    }

    public InspectEnvironment getEnv() {
        return env;
    }

    public void setEnv(InspectEnvironment env) {
        this.env = env;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public List<DeviceResult> getDeviceResults() {
        return deviceResults;
    }

    public void setDeviceResults(List<DeviceResult> deviceResults) {
        this.deviceResults = deviceResults;
    }

    public void addInspectTools(InspectTool tool) {
        if (this.tools == null) {
            this.tools = new ArrayList<InspectTool>();
            this.tools.add(tool);
        }
        else
            this.tools.add(tool);
    }

    public void addDeviceResult(DeviceResult deviceResult) {
        if (this.deviceResults == null) {
            this.deviceResults = new ArrayList<DeviceResult>();
            this.deviceResults.add(deviceResult);
        }
        else
            this.deviceResults.add(deviceResult);
    }
}
