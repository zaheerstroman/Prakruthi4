package com.prakruthi.ui.network;

import com.google.gson.JsonObject;
import com.prakruthi.ui.ui.profile.EditProfileUpdateDrailsUpdateModels;
import com.prakruthi.ui.ui.profile.GetDefaultDataPrakruthiDistrict;
import com.prakruthi.ui.ui.profile.GetDefaultDataPrakruthiStates;
import com.prakruthi.ui.ui.profile.GetDropdownDataReponsePrakruthiStateDistrict;
import com.prakruthi.ui.ui.profile.ProfileGetUserDataResponseRetrofit;

import org.json.JSONObject;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

//    String BASE_URL = "https://houseofspiritshyd.in/prakruthi/admin/api/";
String BASE_URL = "https://prakruthiepl.com/";


    //    PROFILE:-------------------------- PROFILE FRAGMENT & EDIT PROFILE FRAGMENT(Use)
    @POST("getUserData")
    Call<ProfileGetUserDataResponseRetrofit> fetchProfileDetails(@Body JsonObject postObj);



    @POST("userDetailsUpdate")
    Call<EditProfileUpdateDrailsUpdateModels> userDetailsUpdate(@Body RequestBody postObj);


////    GET METHOD:------------
    @GET("getDropdownData")
    Call<GetDropdownDataReponsePrakruthiStateDistrict> getDropdownData2(@Body JsonObject postObj);
    Call<JSONObject> getDropdownData();


    @POST("getDefaultData")
    Call<GetDefaultDataPrakruthiStates> getDropdownData(@Body JsonObject postObj);

    @POST("getDefaultData")
    Call<GetDefaultDataPrakruthiDistrict> getDropdownData1(@Body JsonObject postObj);


    @FormUrlEncoded
    @POST("userDetailsUpdate")
    Call<String> updateUserDetails(
            @Field("user_id") String userId,
            @Field("token") String token,
            @Field("name") String name,
            @Field("email") String email,
            @Field("city") String city,
            @Field("state") String state,
            @Field("district") String district
    );

}
