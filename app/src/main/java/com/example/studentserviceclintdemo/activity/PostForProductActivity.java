package com.example.studentserviceclintdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

import com.example.studentserviceclintdemo.MainActivity;
import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.helper.RealPathUtil;
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.CategoryModel;
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

public class PostForProductActivity extends AppCompatActivity {

    ImageView imageView;

    Spinner category,location;

    Button upload_button;

    Uri selected_image_uri;
    String image_real_path = "no_path";

    EditText name,price,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_for_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // id find
        imageView = findViewById(R.id.image_selection_id);
        category = findViewById(R.id.product_category_id);
        location = findViewById(R.id.product_location_id);
        upload_button = findViewById(R.id.upload_product_button_id);
        name = findViewById(R.id.product_name_id);
        price = findViewById(R.id.product_price);
        description = findViewById(R.id.product_description_id);

        //code for category selection event
        /*category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getSelectedItem().toString();
                //String item_another = parent.getItemAtPosition(position).toString();
                //Toast.makeText(PostForProductActivity.this,"item = "+item,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/


        ApiInterface api = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        // find all category
        api.get_all_category()
                .enqueue(new Callback<List<CategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                        List<CategoryModel> all_category = response.body();
                        ArrayList<String> category_list = new ArrayList<>();
                        for(int i=0;i<all_category.size();i++)
                        {
                            category_list.add(all_category.get(i).getCategory());
                        }
                        ArrayAdapter<String> adapter_category = new ArrayAdapter<>(PostForProductActivity.this, android.R.layout.simple_spinner_item,category_list);
                        adapter_category.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        category.setAdapter(adapter_category);
                    }

                    @Override
                    public void onFailure(Call<List<CategoryModel>> call, Throwable throwable) {

                    }
                });

        // find all location
        ArrayList<String> location_list = new ArrayList<>();
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
                        ArrayAdapter<String> adapter_location = new ArrayAdapter<>(PostForProductActivity.this, android.R.layout.simple_spinner_item,location_list);
                        adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        location.setAdapter(adapter_location);
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                        Toast.makeText(PostForProductActivity.this,"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });

        // image selection
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code for image
                if (ContextCompat.checkSelfPermission(PostForProductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PostForProductActivity.this,
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
        LocalDB localDB = new LocalDB(PostForProductActivity.this);
        ArrayList<LoginModel> login_info = localDB.find_login_info();

        // upload button work
        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(image_real_path.equals("no_path") || name.getText().toString().isEmpty()
                || category.getSelectedItem().toString().isEmpty() || price.getText().toString().isEmpty()
                || location.getSelectedItem().toString().isEmpty() || description.getText().toString().isEmpty())
                {
                    Toast.makeText(PostForProductActivity.this,"select image and fill all text",Toast.LENGTH_SHORT).show();
                }
                else if(login_info.isEmpty())
                {
                    Toast.makeText(PostForProductActivity.this,"Login your account",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //convert it path to file
                    File file = new File(image_real_path);

                    //prepare file part
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("image",file.getName(),requestFile);

                    // find user phone number from local database
                    String user_phone_number = login_info.get(0).getPhone();

                    RequestBody phone_number = RequestBody.create(MediaType.parse("multipart/form-data"),user_phone_number);
                    RequestBody product_category = RequestBody.create(MediaType.parse("multipart/form-data"),category.getSelectedItem().toString());
                    RequestBody product_name = RequestBody.create(MediaType.parse("multipart/form-data"),name.getText().toString());
                    RequestBody product_price = RequestBody.create(MediaType.parse("multipart/form-data"),price.getText().toString());
                    RequestBody product_location = RequestBody.create(MediaType.parse("multipart/form-data"),location.getSelectedItem().toString());
                    RequestBody product_description = RequestBody.create(MediaType.parse("multipart/form-data"),description.getText().toString());

                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

                    apiInterface.upload_product_details(body,phone_number,product_category,product_name,product_price,product_location,product_description)
                            .enqueue(new Callback<ProductUploadResponseDto>() {
                                @Override
                                public void onResponse(Call<ProductUploadResponseDto> call, Response<ProductUploadResponseDto> response) {
                                    Toast.makeText(PostForProductActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                                    //startActivity(new Intent(PostForProductActivity.this,BottomNavigationActivity.class));
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ProductUploadResponseDto> call, Throwable throwable) {
                                    Toast.makeText(PostForProductActivity.this,"server error",Toast.LENGTH_SHORT).show();
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
            image_real_path = RealPathUtil.getRealPath(PostForProductActivity.this,selected_image_uri);

        }
    }
}