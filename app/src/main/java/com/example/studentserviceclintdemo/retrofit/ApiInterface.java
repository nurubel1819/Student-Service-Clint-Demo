package com.example.studentserviceclintdemo.retrofit;

import com.example.studentserviceclintdemo.model.FileResponseDto;
import com.example.studentserviceclintdemo.model.ImageUploadResponse;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.model.UserRegistrationModel;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {


    @POST("/user/registration")
    Call<UserRegistrationModel> registration_new_user(@Body UserRegistrationModel user);

    @POST("/user/login")
    Call<String> user_login(@Body LoginModel loginModel);

    @POST("/user/user_login")
    Call<LoginModel> app_user_login(@Body LoginModel loginModel);

    @GET("user/see_all_user")
    Call<List<UserModel>> see_all_user();

    /*@Multipart
    @POST("/file/upload")
    Call<FileResponseDto> upload_file(@Part MultipartBody.Part image);*/

    @Multipart
    @POST("/file/upload")
    Call<ImageUploadResponse> upload_image_server(@Part MultipartBody.Part image);
}
