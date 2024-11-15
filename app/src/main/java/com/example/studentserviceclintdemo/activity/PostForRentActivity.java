package com.example.studentserviceclintdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.helper.RealPathUtil;
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.ProductUploadResponseDto;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostForRentActivity extends AppCompatActivity {

    Spinner location;
    ImageView imageView;
    EditText price,floor,member,description;
    Button upload;

    // image selection
    Uri selected_image_uri;
    String image_real_path="no_path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_for_rent);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        location = findViewById(R.id.rent_location_id);
        imageView = findViewById(R.id.rent_image_selection_id);
        price = findViewById(R.id.rent_per_month_id);
        floor = findViewById(R.id.rent_floor_number_id);
        member = findViewById(R.id.rent_member_number_id);
        description = findViewById(R.id.rent_description_id);
        upload = findViewById(R.id.rent_upload_button_id);

        // find all location
        ArrayList<String> location_list = new ArrayList<>();
        ApiInterface api = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        api.get_all_location()
                .enqueue(new Callback<List<LocationModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                        List<LocationModel> all_location = response.body();
                        // load all location server to app
                        for(int i=0;i<all_location.size();i++)
                        {
                            location_list.add(all_location.get(i).getLocation());
                        }
                        ArrayAdapter<String> adapter_location = new ArrayAdapter<>(PostForRentActivity.this, android.R.layout.simple_spinner_item,location_list);
                        adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        location.setAdapter(adapter_location);
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                        Toast.makeText(PostForRentActivity.this,"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });

        // image upload
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for image
                if (ContextCompat.checkSelfPermission(PostForRentActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostForRentActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            100);
                }
                //image selection
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,10);
            }
        });

        // database work
        LocalDB localDB = new LocalDB(PostForRentActivity.this);
        ArrayList<LoginModel> login_info = localDB.find_login_info();

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // upload work
                if(image_real_path.equals("no_path")||location.getSelectedItem().toString().isEmpty()
                ||price.getText().toString().isEmpty()||floor.getText().toString().isEmpty()
                ||member.getText().toString().isEmpty()||description.getText().toString().isEmpty())
                {
                    Toast.makeText(PostForRentActivity.this,"select image and fill all text",Toast.LENGTH_SHORT).show();
                }
                else if(login_info.isEmpty())
                {
                    Toast.makeText(PostForRentActivity.this,"Login your account",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // upload code here
                    //convert it path to file
                    File file = new File(image_real_path);

                    //prepare file part
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);

                    // find user phone number from local database
                    String user_phone_number = login_info.get(0).getPhone();

                    RequestBody phone_number = RequestBody.create(MediaType.parse("multipart/form-data"),user_phone_number);
                    RequestBody rent_location = RequestBody.create(MediaType.parse("multipart/form-data"),location.getSelectedItem().toString());

                    RequestBody rent_price = RequestBody.create(MediaType.parse("multipart/form-data"),price.getText().toString());
                    RequestBody rent_floor = RequestBody.create(MediaType.parse("multipart/form-data"),floor.getText().toString());
                    RequestBody rent_member = RequestBody.create(MediaType.parse("multipart/form-data"),member.getText().toString());
                    RequestBody rent_description = RequestBody.create(MediaType.parse("multipart/form-data"),description.getText().toString());

                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    apiInterface.upload_rent_details(body,phone_number,rent_location,rent_price,rent_floor,rent_member,rent_description)
                            .enqueue(new Callback<ProductUploadResponseDto>() {
                                @Override
                                public void onResponse(Call<ProductUploadResponseDto> call, Response<ProductUploadResponseDto> response) {
                                    Toast.makeText(PostForRentActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<ProductUploadResponseDto> call, Throwable throwable) {
                                    Toast.makeText(PostForRentActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10 && resultCode == Activity.RESULT_OK)
        {
            selected_image_uri = data.getData();
            imageView.setImageURI(selected_image_uri);
            image_real_path = RealPathUtil.getRealPath(PostForRentActivity.this,selected_image_uri);

        }
    }
}