package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feedback {
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("error_msg")
    @Expose
    private String errorMsg;

    /**
     * No args constructor for use in serialization
     */
    public Feedback() {
    }

    public Feedback(String tag, Boolean status) {
        this.tag = tag;
        this.status = status;
    }

    /**
     * @param status
     * @param tag
     * @param errorMsg
     */
    public Feedback(String tag, Boolean status, String errorMsg) {
        super();
        this.tag = tag;
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
