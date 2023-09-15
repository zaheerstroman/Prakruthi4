package com.prakruthi.ui.ui.profile;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @GET("getDropdownData")
    Call<JSONObject> getDropdownData();

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
