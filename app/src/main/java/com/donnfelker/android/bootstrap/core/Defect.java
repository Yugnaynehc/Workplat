package com.donnfelker.android.bootstrap.core;

/**
 * Created by Feather on 14-3-30.
 */
public class Defect {

    protected String bugid;
    protected String description;
    protected String deviceid;
    protected String resultid;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBugid() {
        return bugid;
    }

    public void setBugid(String bugid) {
        this.bugid = bugid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getResultid() {
        return resultid;
    }

    public void setResultid(String resultid) {
        this.resultid = resultid;
    }
}
