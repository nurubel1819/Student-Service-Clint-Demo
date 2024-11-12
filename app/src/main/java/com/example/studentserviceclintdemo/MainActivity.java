package com.example.studentserviceclintdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.activity.BottomNavigationActivity;
import com.example.studentserviceclintdemo.activity.FileUploadActivity;
import com.example.studentserviceclintdemo.activity.PostForProductActivity;
import com.example.studentserviceclintdemo.activity.TestUserActivity;
import com.example.studentserviceclintdemo.activity.UserLoginActivity;

public class MainActivity extends AppCompatActivity {

    Button start_app_button;
    Button test_user,test_file_upload,product_upload,test_bottom_nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // take component id
        start_app_button = findViewById(R.id.start_app_button_id);
        test_user = findViewById(R.id.test_user_info_button_id);
        test_file_upload = findViewById(R.id.test_file_upload);
        product_upload = findViewById(R.id.product_upload_test_id);
        test_bottom_nav = findViewById(R.id.bottom_navigation_test_id);

        //work with component
        start_app_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, UserLoginActivity.class));
            }
        });

        test_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TestUserActivity.class));
            }
        });

        test_file_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FileUploadActivity.class));
            }
        });

        product_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostForProductActivity.class));
            }
        });

        test_bottom_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BottomNavigationActivity.class));
            }
        });
    }
}