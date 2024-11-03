package com.example.studentserviceclintdemo.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    //private static final String base_url = "http://192.168.147.184:8080/";
    private static final String base_url = "http://192.168.0.100:8080/";

    public static Retrofit getRetrofit()
    {
        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
