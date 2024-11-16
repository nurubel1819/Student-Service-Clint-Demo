package com.example.studentserviceclintdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.LoginModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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

        //Local database login confirmation
        LocalDB localDB = new LocalDB(MainActivity.this);
        ArrayList<LoginModel> login_info = localDB.find_login_info();
        if(login_info.isEmpty())
        {
            startActivity(new Intent(MainActivity.this,UserLoginActivity.class));
        }
        else
        {
            startActivity(new Intent(MainActivity.this,BottomNavigationActivity.class));
        }
    }
}