package com.example.studentserviceclintdemo.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.retrofit.Adapter;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycle_view_test_id);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.see_all_user()
                .enqueue(new Callback<List<UserModel>>() {
                    @Override
                    public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                        assert response.body() != null;
                        if(!response.body().isEmpty())
                        {
                            List<UserModel> userModelList = response.body();
                            Adapter adapter = new Adapter(TestUserActivity.this,userModelList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TestUserActivity.this);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(adapter);
                            Toast.makeText(TestUserActivity.this,"load successful",Toast.LENGTH_LONG).show();
                        }
                        else Toast.makeText(TestUserActivity.this, " Data can't load",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<List<UserModel>> call, Throwable throwable) {
                        Toast.makeText(TestUserActivity.this,"Data can't download",Toast.LENGTH_LONG).show();
                    }
                });

    }
}