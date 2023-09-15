package com.prakruthi.ui.ui.profile;

public class EditProfileUpdateDrailsUpdateModels {

    private Boolean statusCode;

    private String message;

    public EditProfileUpdateDrailsUpdateModels(Boolean statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public Boolean getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Boolean statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
