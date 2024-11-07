package com.example.studentserviceclintdemo.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.studentserviceclintdemo.model.FileResponseDto;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileUploader {
    public static void UploadImage(Context context, Uri imageeUri)
    {

        String real_path = RealPathUtil.getRealPath(context,imageeUri);
        File file = new File(real_path);

        //File file = new File(imageeUri.getPath());

        Log.d("uri_path",imageeUri.toString());

        Log.d("real_path",real_path);

        //prepare file part
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);

        Log.d("file_name",file.getName());
        Log.d("file_path",file.getPath());


        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        Log.d("api_interface",apiInterface.toString());

        /*apiInterface.upload_file(body)
                .enqueue(new Callback<FileResponseDto>() {
                    @Override
                    public void onResponse(Call<FileResponseDto> call, Response<FileResponseDto> response) {
                        Toast.makeText(context,"Upload successful",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<FileResponseDto> call, Throwable throwable) {
                        Toast.makeText(context,throwable.toString(),Toast.LENGTH_SHORT).show();
                        Log.e("upload_log", "Error: " + throwable);
                    }
                });*/

    }
}
