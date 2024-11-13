package com.example.studentserviceclintdemo.retrofit;

import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.ProductGetDto;
import com.example.studentserviceclintdemo.model.ProductUploadResponseDto;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.model.UserRegistrationModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    @POST("/product/upload_with_image")
    Call<ProductUploadResponseDto> upload_product_details(@Part MultipartBody.Part image,
                                                          @Part("category") RequestBody category,
                                                          @Part("name") RequestBody name,
                                                          @Part("price") RequestBody price,
                                                          @Part("location") RequestBody location,
                                                          @Part("description") RequestBody description);
    //get all product information
    @GET("product/see_all_product")
    Call<List<ProductGetDto>> get_all_product();

}
