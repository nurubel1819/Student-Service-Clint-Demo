package com.example.studentserviceclintdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.UserRegistrationModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRegistrationActivity extends AppCompatActivity {

    EditText name,phone,password,confirm_password;
    Button registration_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //find id
        name = findViewById(R.id.user_name_id);
        phone = findViewById(R.id.user_phone_id);
        password = findViewById(R.id.user_password_id);
        confirm_password = findViewById(R.id.user_confirm_password_id);
        registration_button = findViewById(R.id.user_registration_button_id);

        // work with component
        registration_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user_name = name.getText().toString();
                String user_phone = phone.getText().toString();
                String user_password = password.getText().toString();
                String user_confirm_password = confirm_password.getText().toString();

                if(user_phone.length()!=11) Toast.makeText(UserRegistrationActivity.this,"Phone number not valid",Toast.LENGTH_SHORT).show();
                else  if(user_name.isEmpty() || user_password.isEmpty() || user_confirm_password.isEmpty()) Toast.makeText(UserRegistrationActivity.this,"Fill up all text file",Toast.LENGTH_SHORT).show();
                else if(!user_password.equals(user_confirm_password)) Toast.makeText(UserRegistrationActivity.this,"Password and confirm password are not equal",Toast.LENGTH_SHORT).show();
                else {
                    UserRegistrationModel user = new UserRegistrationModel();

                    user.setName(user_name);
                    user.setPhone(user_phone);
                    user.setPassword(user_password);
                    user.setConfirm_password(user_confirm_password);

                    ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
                    apiInterface.registration_new_user(user)
                            .enqueue(new Callback<UserRegistrationModel>() {
                                @Override
                                public void onResponse(Call<UserRegistrationModel> call, Response<UserRegistrationModel> response) {
                                    Toast.makeText(UserRegistrationActivity.this,"Registration successful",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<UserRegistrationModel> call, Throwable throwable) {
                                    Toast.makeText(UserRegistrationActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                finish();
            }
        });
    }
}