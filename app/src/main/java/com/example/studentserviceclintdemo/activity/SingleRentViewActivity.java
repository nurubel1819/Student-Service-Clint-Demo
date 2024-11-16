package com.example.studentserviceclintdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.RentModel;
import com.example.studentserviceclintdemo.model.SingleUploadLongModel;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleRentViewActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    ImageView imageView;
    TextView name,phone,location,price,floor,member,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_rent_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //get data from previous activity
        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("phone_number");
        Long rent_id = intent.getLongExtra("rent_id",0L);

        // find all id
        floatingActionButton = findViewById(R.id.floatingActionButton_rent_id);
        name = findViewById(R.id.rent_view_name_id);
        phone = findViewById(R.id.rent_view_phone_id);
        location = findViewById(R.id.rent_view_location_id);
        price = findViewById(R.id.rent_view_price_id);
        floor = findViewById(R.id.rent_view_floor_id);
        member = findViewById(R.id.rent_view_member_id);
        description = findViewById(R.id.rent_view_description_id);
        imageView = findViewById(R.id.rent_view_image_id);

        //set data in this activity
        phone.setText(phone.getText()+" "+phone_number);

        // load post(user) information
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        UserModel userModel = new UserModel();
        userModel.setPhone(phone_number);

        apiInterface.see_single_user_by_phone(userModel)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel model = response.body();
                        name.setText(name.getText()+" "+model.getName());
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {
                        Toast.makeText(SingleRentViewActivity.this,"Post data can't load",Toast.LENGTH_SHORT).show();
                    }
                });
        SingleUploadLongModel singleUploadLongModel = new SingleUploadLongModel();
        singleUploadLongModel.setId(rent_id);
        apiInterface.get_single_rent_info(singleUploadLongModel)
                .enqueue(new Callback<RentModel>() {
                    @Override
                    public void onResponse(Call<RentModel> call, Response<RentModel> response) {
                        RentModel model = response.body();
                        location.setText(location.getText()+model.getLocation());
                        price.setText(price.getText()+String.valueOf(model.getPrice())+" টাকা");
                        floor.setText(floor.getText()+String.valueOf(model.getFloor())+" তলা");
                        member.setText(member.getText()+String.valueOf(model.getMember())+" জন");
                        description.setText(description.getText()+model.getDescription());

                        // image load
                        String image_url = RetrofitInstance.getBase_url()+"file/images/"+model.getImage();
                        Glide.with(SingleRentViewActivity.this).load(image_url).into(imageView);
                    }
                    @Override
                    public void onFailure(Call<RentModel> call, Throwable throwable) {
                        Toast.makeText(SingleRentViewActivity.this,"Post data can't load",Toast.LENGTH_SHORT).show();
                    }
                });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Start Phone app to this number
                Intent intent_call = new Intent(Intent.ACTION_DIAL);
                intent_call.setData(Uri.parse("tel: "+phone_number));
                startActivity(intent_call);
            }
        });
    }
}