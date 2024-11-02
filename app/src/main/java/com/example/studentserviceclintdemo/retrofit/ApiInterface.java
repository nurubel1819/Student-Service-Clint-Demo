package com.example.studentserviceclintdemo.retrofit;

import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.model.UserRegistrationModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("/user/registration")
    Call<UserRegistrationModel> registration_new_user(@Body UserRegistrationModel user);

    @GET("user/see_all_user")
    Call<List<UserModel>> see_all_user();

    //@POST("/user/login")

}
