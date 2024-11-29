package com.example.studentserviceclintdemo.retrofit;

import com.example.studentserviceclintdemo.model.CategoryModel;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.ProductModel;
import com.example.studentserviceclintdemo.model.ProductUploadResponseDto;
import com.example.studentserviceclintdemo.model.RentModel;
import com.example.studentserviceclintdemo.model.SingleUploadLongModel;
import com.example.studentserviceclintdemo.model.SingleResponseModel;
import com.example.studentserviceclintdemo.model.TuitionModel;
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
    Call<List<ProductModel>> get_all_product();

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

    @POST("/product/find_by_id")
    Call<ProductModel> get_single_product_info(@Body SingleUploadLongModel singleUploadLongModel);

    @POST("/product/find_by_category_and_price")
    Call<List<ProductModel>> find_by_category_and_location(@Body ProductModel productGetDto);

    @POST("/product/filter_name")
    Call<List<ProductModel>> filter_by_product_name(@Body ProductModel productModel);

    @POST("/rent/filter_all")
    Call<List<RentModel>> filter_rent_all(@Body RentModel rentModel);

    @POST("/tuition/upload_new_student")
    Call<SingleResponseModel> upload_new_student(@Body TuitionModel tuitionModel);

    @POST("/tuition/upload_new_teacher")
    Call<SingleResponseModel> upload_new_teacher(@Body TuitionModel tuitionModel);

    @GET("/tuition/get_all_tuition")
    Call<List<TuitionModel>> get_all_tuition();

    @POST("/tuition/find_by_id")
    Call<TuitionModel> get_single_tuition_info(@Body SingleUploadLongModel model);

    @POST("/tuition/filter_all")
    Call<List<TuitionModel>> filter_tuition_all(@Body TuitionModel model);

}
