package com.donnfelker.android.bootstrap.core.inspect.result;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Feather on 14-6-28.
 */
public class DeviceResult implements Serializable {

    private static final long serialVersionUID = -5413972855563452036L;

    private String deviceName;
    private ArrayList<String> inspectResult;
    private String deviceID;
    private String deviceDate;
    private String objectId;

    public DeviceResult(String deviceID, String deviceName, String deviceDate, ArrayList<String> inspectResult) {
        this.deviceName = deviceName;
        this.inspectResult = inspectResult;
        this.deviceID = deviceID;
        this.deviceDate = deviceDate;
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
}

