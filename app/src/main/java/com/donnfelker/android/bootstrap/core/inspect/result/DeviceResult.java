package com.donnfelker.android.bootstrap.core.inspect.result;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Feather on 14-6-28.
 */
public class DeviceResult implements Serializable {

    private static final long serialVersionUID = -5413972855563452036L;

    private String deviceID;
    private String deviceName;
    private String deviceDate;
    private ArrayList<String> inspectContent;
    private ArrayList<String> inspectStandard;
    private ArrayList<String> inspectResult;

    private String objectId;

    public DeviceResult() {}

    public DeviceResult(String deviceID,
                        String deviceName,
                        String  deviceDate,
                        ArrayList<String> inspectContent,
                        ArrayList<String> inspectStandard,
                        ArrayList<String> inspectResult) {
        this.deviceDate = deviceDate;
        this.deviceID = deviceID;
        this.inspectStandard = inspectStandard;
        this.inspectContent = inspectContent;
        this.inspectResult = inspectResult;
        this.deviceName = deviceName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public ArrayList<String> getInspectResult() {
        return inspectResult;
    }

    public void setInspectResult(ArrayList<String> inspectResult) {
        this.inspectResult = inspectResult;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDeviceDate() {
        return deviceDate;
    }

    public void setDeviceDate(String deviceDate) {
        this.deviceDate = deviceDate;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public ArrayList<String> getInspectContent() {
        return inspectContent;
    }

    public void setInspectContent(ArrayList<String> inspectContent) {
        this.inspectContent = inspectContent;
    }

    public ArrayList<String> getInspectStandard() {
        return inspectStandard;
    }

    public void setInspectStandard(ArrayList<String> inspectStandard) {
        this.inspectStandard = inspectStandard;
    }

    public void addInspectContent(String content) {
        if (this.inspectContent == null) {
            this.inspectContent = new ArrayList<String>();
            this.inspectContent.add(content);
        } else
            this.inspectContent.add(content);
    }

    public void addInspectStandard(String standard) {
        if (this.inspectStandard == null) {
            this.inspectStandard = new ArrayList<String>();
            this.inspectStandard.add(standard);
        } else
            this.inspectStandard.add(standard);
    }

    public void addInspectResult(String result) {
        if (this.inspectResult == null) {
            this.inspectResult = new ArrayList<String>();
            this.inspectResult.add(result);
        } else
            this.inspectResult.add(result);
    }

}

