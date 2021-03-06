package com.donnfelker.android.bootstrap.core;

import java.io.Serializable;

/**
 * Created by Feather on 14-3-11.
 */
public class Work implements Serializable {

    // 唯一标识
    protected String applyid;
    // 计划执行人的id
    protected String applyUser;
    // 计划预定的执行时间
    protected String date;
    // 巡检的类型
    protected String type;
    // 变电站的id
    protected String substation;
    // 计划实际的执行时间
    protected String excutedate;
    // 申请的理由
    protected String reason;
    // 记录审批情况
    protected String authorized;
    // 记录执行状态
    protected int status;

    protected String planid;

    protected String objectId;

    public String getApplyid() {
        return applyid;
    }

    public void setApplyid(String applyid) {
        this.applyid = applyid;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubstation() {
        return substation;
    }

    public void setSubstation(String substation) {
        this.substation = substation;
    }

    public String getExcutedate() {
        return excutedate;
    }

    public void setExcutedate(String excutedate) {
        this.excutedate = excutedate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String athorized) {
        this.authorized = athorized;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getPlanid() {
        return planid;
    }

    public void setPlanid(String planid) {
        this.planid = planid;
    }
}
