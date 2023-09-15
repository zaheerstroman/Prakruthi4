package com.prakruthi.ui.ui.profile;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status_code")
    boolean status_code;
    @SerializedName("status")
    boolean status;

    @SerializedName("msg")
    String msg;
    @SerializedName("message")
    String message;

    public boolean isStatus_code() {
        return status_code;
    }

    public void setStatus_code(boolean status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
