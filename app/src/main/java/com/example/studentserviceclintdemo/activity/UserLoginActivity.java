package com.example.studentserviceclintdemo.activity;

import android.content.Intent;
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
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserLoginActivity extends AppCompatActivity {

    EditText phone_number,password;
    Button login,registration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // find id
        phone_number = findViewById(R.id.login_phone_id);
        password = findViewById(R.id.login_password_id);
        login = findViewById(R.id.login_button_id);
        registration = findViewById(R.id.user_registration_id);

        // work with component
        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLoginActivity.this,UserRegistrationActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = phone_number.getText().toString();
                String pass = password.getText().toString();

                LoginModel loginModel = new LoginModel();
                loginModel.setPhone(phone);
                loginModel.setPassword(pass);

                ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

                apiInterface.app_user_login(loginModel)
                        .enqueue(new Callback<LoginModel>() {
                            @Override
                            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                                LoginModel ack = response.body();
                                if(ack.getPassword().equals("valid"))
                                {
                                    LocalDB localDB = new LocalDB(UserLoginActivity.this);
                                    localDB.addLoginInfo(phone,pass);
                                    Toast.makeText(UserLoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(new Intent(UserLoginActivity.this,BottomNavigationActivity.class));
                                }
                                else Toast.makeText(UserLoginActivity.this,"Invalid phone or password",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<LoginModel> call, Throwable throwable) {
                                Toast.makeText(UserLoginActivity.this,"Server error",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}