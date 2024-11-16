package com.example.studentserviceclintdemo.retrofit;

import com.example.studentserviceclintdemo.model.CategoryModel;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.ProductGetDto;
import com.example.studentserviceclintdemo.model.ProductUploadResponseDto;
import com.example.studentserviceclintdemo.model.RentModel;
import com.example.studentserviceclintdemo.model.SingleUploadLongModel;
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

    @Multipart
    @POST("/product/upload_details_with_image")
    Call<ProductUploadResponseDto> upload_product_details(@Part MultipartBody.Part image,
                                                          @Part("phone") RequestBody phone,
                                                          @Part("category") RequestBody category,
                                                          @Part("name") RequestBody name,
                                                          @Part("price") RequestBody price,
                                                          @Part("location") RequestBody location,
                                                          @Part("description") RequestBody description);
    //rent house post upload
    @Multipart
    @POST("/rent/upload_details_with_image")
    Call<ProductUploadResponseDto> upload_rent_details(@Part MultipartBody.Part image,
                                                       @Part("phone") RequestBody phone,
                                                       @Part("location") RequestBody location,
                                                       @Part("price") RequestBody price,
                                                       @Part("floor") RequestBody floor,
                                                       @Part("member") RequestBody member,
                                                       @Part("description") RequestBody description);
    //get all product information
    @GET("product/see_all_product")
    Call<List<ProductGetDto>> get_all_product();

    @POST("/user/see_single_user")
    Call<UserModel> see_single_user_by_phone(@Body UserModel userModel);

    @GET("/location/get_all_location")
    Call<List<LocationModel>> get_all_location();

    @GET("/product_category/see_all_category")
    Call<List<CategoryModel>> get_all_category();

    @GET("/rent/get_all")
    Call<List<RentModel>> get_all_rent_info();

    @POST("/rent/find_by_id")
    Call<RentModel> get_single_rent_info(@Body SingleUploadLongModel singleUploadLongModel);

}
