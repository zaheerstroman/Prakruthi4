package com.prakruthi.ui.ui.profile;

public class ProfileSupportResponse {

    private Boolean statusCode;
    private String message;


    private ProfileSupportModel result;

    public ProfileSupportModel getResult() {
        return result;
    }

    public void setData(ProfileSupportModel result) {
        this.result = result;
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

    public void setResult(ProfileSupportModel result) {
        this.result = result;
    }



    public ProfileSupportResponse(Boolean statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }

    public static class ProfileSupportModel
    {

        private String id;
        private String mobile;
        private String email;
        private String address;

        public ProfileSupportModel(String id, String mobile, String email, String address) {
            this.id = id;
            this.mobile = mobile;
            this.email = email;
            this.address = address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}

