package com.prakruthi.ui.ui.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfileGetUserDataResponseRetrofit extends BaseResponse{

//    @SerializedName("status_code")
//    @Expose
//    private Boolean statusCode;

//    @SerializedName("privacyPolicy")
//    @Expose
//    private String privacyPolicy;
//
//    @SerializedName("termsAndConditions")
//    @Expose
//    private String termsAndConditions;
//
//    @SerializedName("message")
//    @Expose
//    private String message;

    //---------------------------

//    @SerializedName("data")
//    @Expose
//    private List<ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit> data;
//
//    public List<ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit> getData() {
//        return data;
//    }
//
//    public void setData(List<ProfileGetUserDataResponseRetrofit.ProfileGetUserDataModelRetrofit> data) {
//        this.data = data;
//    }

    //--------------------------

    @SerializedName("data")
    @Expose
    private ProfileGetUserDataModelRetrofit data;

    public ProfileGetUserDataModelRetrofit getData() {
        return data;
    }

    public void setData(ProfileGetUserDataModelRetrofit data) {
        this.data = data;
    }

//    public Boolean getStatusCode() {
//        return statusCode;
//    }
//
//    public void setStatusCode(Boolean statusCode) {
//        this.statusCode = statusCode;
//    }

//    public String getPrivacyPolicy() {
//        return privacyPolicy;
//    }
//
//    public void setPrivacyPolicy(String privacyPolicy) {
//        this.privacyPolicy = privacyPolicy;
//    }
//
//    public String getTermsAndConditions() {
//        return termsAndConditions;
//    }
//
//    public void setTermsAndConditions(String termsAndConditions) {
//        this.termsAndConditions = termsAndConditions;
//    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ProfileGetUserDataModelRetrofit{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("department_id")
        @Expose
        private Integer departmentId;
        @SerializedName("stationid")
        @Expose
        private String stationid;
        @SerializedName("user_code")
        @Expose
//        private Object userCode;
        private String userCode;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("last_name")
        @Expose
//        private Object lastName;
        private String lastName;

        @SerializedName("email")
        @Expose
//        private Object email;
        private String email;

        @SerializedName("password")
        @Expose
//        private Object password;
        private String password;

        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("gender")
        @Expose
//        private Object gender;
        private String gender;

        @SerializedName("dob")
        @Expose
//        private Object dob;
        private String dob;

        @SerializedName("city")
        @Expose
//        private Object city;
        private String city;

        @SerializedName("postCode")
        @Expose
//        private Object postCode;
        private String postCode;

        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("state")
        @Expose
//        private Object state;
        private String state;

        @SerializedName("country")
        @Expose
//        private Object country;
        private String country;

        @SerializedName("district")
        @Expose
//        private Object country;
        private String district;

        @SerializedName("street")
        @Expose
//        private Object street;
        private String street;

        @SerializedName("proof_type")
        @Expose
//        private Object proofType;
        private String proofType;

        @SerializedName("proof_attachment")
        @Expose
//        private Object proofAttachment;
        private String proofAttachment;

        @SerializedName("profile_photo")
        @Expose
//        private Object profilePhoto;
        private String profilePhoto;

        @SerializedName("fb_id")
        @Expose
//        private Object fbId;
        private String fbId;

        @SerializedName("google_id")
        @Expose
//        private Object googleId;
        private String googleId;

        @SerializedName("category_id")
        @Expose
//        private Object categoryId;
        private String categoryId;

        @SerializedName("about")
        @Expose
//        private Object about;
        private String about;

        @SerializedName("expert_in_yrs")
        @Expose
//        private Object expertInYrs;
        private String expertInYrs;

        @SerializedName("NI_number")
        @Expose
//        private Object nINumber;
        private String nINumber;

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("mobile_verified")
        @Expose
        private String mobileVerified;
        @SerializedName("is_verified")
        @Expose
        private String isVerified;
        @SerializedName("log_date_created")
        @Expose
//        private Object logDateCreated;
        private String logDateCreated;

        @SerializedName("created_by")
        @Expose
//        private Object createdBy;
        private String createdBy;

        @SerializedName("log_date_modified")
        @Expose
//        private Object logDateModified;
        private String logDateModified;

        @SerializedName("modified_by")
        @Expose
//        private Object modifiedBy;
        private String modifiedBy;

        @SerializedName("fcm_token")
        @Expose
//        private Object fcmToken;
        private String fcmToken;

        @SerializedName("device_id")
        @Expose
//        private Object deviceId;
        private String deviceId;

        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("no_vendor")
        @Expose
        private String noVendor;
        @SerializedName("specifications")
        @Expose
//        private Object specifications;
        private String specifications;

        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("brandid")
        @Expose
        private String brandid;
        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("suburb")
        @Expose
//        private Object suburb;
        private String suburb;

        @SerializedName("allow_email")
        @Expose
        private String allowEmail;
        @SerializedName("allow_sms")
        @Expose
        private String allowSms;
        @SerializedName("allow_push")
        @Expose
        private String allowPush;
        @SerializedName("mobile_model")
        @Expose
//        private Object mobileModel;
        private String mobileModel;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(Integer departmentId) {
            this.departmentId = departmentId;
        }

        public String getStationid() {
            return stationid;
        }

        public void setStationid(String stationid) {
            this.stationid = stationid;
        }

        public String getUserCode() {
            return userCode;
        }

        public void setUserCode(String userCode) {
            this.userCode = userCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getProofType() {
            return proofType;
        }

        public void setProofType(String proofType) {
            this.proofType = proofType;
        }

        public String getProofAttachment() {
            return proofAttachment;
        }

        public void setProofAttachment(String proofAttachment) {
            this.proofAttachment = proofAttachment;
        }

        public String getProfilePhoto() {
            return profilePhoto;
        }

        public void setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
        }

        public String getFbId() {
            return fbId;
        }

        public void setFbId(String fbId) {
            this.fbId = fbId;
        }

        public String getGoogleId() {
            return googleId;
        }

        public void setGoogleId(String googleId) {
            this.googleId = googleId;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public String getExpertInYrs() {
            return expertInYrs;
        }

        public void setExpertInYrs(String expertInYrs) {
            this.expertInYrs = expertInYrs;
        }

        public String getnINumber() {
            return nINumber;
        }

        public void setnINumber(String nINumber) {
            this.nINumber = nINumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMobileVerified() {
            return mobileVerified;
        }

        public void setMobileVerified(String mobileVerified) {
            this.mobileVerified = mobileVerified;
        }

        public String getIsVerified() {
            return isVerified;
        }

        public void setIsVerified(String isVerified) {
            this.isVerified = isVerified;
        }

        public String getLogDateCreated() {
            return logDateCreated;
        }

        public void setLogDateCreated(String logDateCreated) {
            this.logDateCreated = logDateCreated;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getLogDateModified() {
            return logDateModified;
        }

        public void setLogDateModified(String logDateModified) {
            this.logDateModified = logDateModified;
        }

        public String getModifiedBy() {
            return modifiedBy;
        }

        public void setModifiedBy(String modifiedBy) {
            this.modifiedBy = modifiedBy;
        }

        public String getFcmToken() {
            return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
            this.fcmToken = fcmToken;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getNoVendor() {
            return noVendor;
        }

        public void setNoVendor(String noVendor) {
            this.noVendor = noVendor;
        }

        public String getSpecifications() {
            return specifications;
        }

        public void setSpecifications(String specifications) {
            this.specifications = specifications;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getBrandid() {
            return brandid;
        }

        public void setBrandid(String brandid) {
            this.brandid = brandid;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getSuburb() {
            return suburb;
        }

        public void setSuburb(String suburb) {
            this.suburb = suburb;
        }

        public String getAllowEmail() {
            return allowEmail;
        }

        public void setAllowEmail(String allowEmail) {
            this.allowEmail = allowEmail;
        }

        public String getAllowSms() {
            return allowSms;
        }

        public void setAllowSms(String allowSms) {
            this.allowSms = allowSms;
        }

        public String getAllowPush() {
            return allowPush;
        }

        public void setAllowPush(String allowPush) {
            this.allowPush = allowPush;
        }

        public String getMobileModel() {
            return mobileModel;
        }

        public void setMobileModel(String mobileModel) {
            this.mobileModel = mobileModel;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }
    }

}
