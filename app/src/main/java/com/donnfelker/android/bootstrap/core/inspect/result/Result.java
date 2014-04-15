package com.donnfelker.android.bootstrap.core.inspect.result;

import com.donnfelker.android.bootstrap.core.inspect.object.Device;
import com.donnfelker.android.bootstrap.core.inspect.security.Security;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by feather on 14-4-13.
 * result of inspect
 * 记录巡检结果的类
 */
public class Result {

    private String type;                // type of inspect
    private List<InspectTool> tools;         // using tools list of inspect
    private InspectEnvironment env;     // environment information of inspect
    private Security security;          // security information of inspect
    private List<Device> devices;        // inspectd devices information list of inspect

    public Result() {

    }

    public Result(String type, List<InspectTool> tools, InspectEnvironment env, Security security, List<Device> device) {
        this.type = type;
        this.tools = tools;
        this.env = env;
        this.security = security;
        this.devices = device;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTools(List<InspectTool> tools) {
        this.tools = tools;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public void setEnv(InspectEnvironment env) {
        this.env = env;
    }

    public void addDevice(Device device) {
        if (this.devices == null) {
            this.devices = new ArrayList<Device>();
            this.devices.add(device);
        }
        else
            this.devices.add(device);
    }

    public String getType() {
        return type;
    }

    public List<InspectTool> getTools() {
        return tools;
    }

    public InspectEnvironment getEnv() {
        return env;
    }

    public Security getSecurity() {
        return security;
    }

    public List<Device> getDevices() {
        return devices;
    }
}
