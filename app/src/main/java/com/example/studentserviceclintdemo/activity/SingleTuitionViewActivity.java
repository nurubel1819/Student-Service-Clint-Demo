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

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.SingleUploadLongModel;
import com.example.studentserviceclintdemo.model.TuitionModel;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SingleTuitionViewActivity extends AppCompatActivity {

    FloatingActionButton floatingActionButton;
    TextView name,phone,location,category,institute,version;
    TextView faculty,group,gender,cls,fee,description;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_tuition_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        floatingActionButton = findViewById(R.id.floatingActionButton_tuition_id);
        name = findViewById(R.id.tuition_view_name_id);
        phone = findViewById(R.id.tuition_view_phone_id);
        location = findViewById(R.id.tuition_view_location_id);
        category = findViewById(R.id.tuition_view_category_id);
        institute = findViewById(R.id.tuition_view_institute_id);
        version = findViewById(R.id.tuition_view_version_id);
        faculty = findViewById(R.id.tuition_view_faculty_id);
        group = findViewById(R.id.tuition_view_group_id);
        gender = findViewById(R.id.tuition_view_gender_id);
        cls = findViewById(R.id.tuition_view_class_id);
        fee = findViewById(R.id.tuition_view_fee_id);
        description = findViewById(R.id.tuition_view_description_id);
        imageView = findViewById(R.id.tuition_view_image_view_id);

        //get data from previous activity
        Intent intent = getIntent();
        String phone_number = intent.getStringExtra("phone_number");
        Long tuition_id = intent.getLongExtra("tuition_id",0L);

        phone.setText(phone.getText()+phone_number);

        // load post user information
        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        UserModel model = new UserModel();
        model.setPhone(phone_number);

        apiInterface.see_single_user_by_phone(model)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel one_user = response.body();
                        name.setText(name.getText()+one_user.getName());
                    }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable throwable) {
                        Toast.makeText(SingleTuitionViewActivity.this,"Server error!",Toast.LENGTH_SHORT).show();
                    }
                });
        SingleUploadLongModel model_with_long = new SingleUploadLongModel();
        model_with_long.setId(tuition_id);
        apiInterface.get_single_tuition_info(model_with_long)
                .enqueue(new Callback<TuitionModel>() {
                    @Override
                    public void onResponse(Call<TuitionModel> call, Response<TuitionModel> response) {
                        TuitionModel info = response.body();
                        String cat = info.getCategory();
                        location.setText(location.getText()+info.getLocation());
                        category.setText(category.getText()+cat);
                        institute.setText(institute.getText()+info.getInstitute());
                        if(cat.equals("Teacher"))
                        {
                            imageView.setImageResource(R.drawable.teacher_icon);
                            cls.setText(cls.getText()+"not applicable");
                            group.setText(group.getText()+"not applicable");
                            faculty.setText(faculty.getText()+info.getFaculty());
                            version.setText(version.getText()+"not applicable");
                        }
                        else
                        {
                            imageView.setImageResource(R.drawable.student_icon);
                            cls.setText(cls.getText()+String.valueOf(info.getCls()));
                            group.setText(group.getText()+info.getGrp());
                            faculty.setText(faculty.getText()+"not applicable");
                            version.setText(version.getText()+info.getVersion());
                        }
                        gender.setText(gender.getText()+info.getGender());
                        fee.setText(fee.getText()+String.valueOf(info.getFee()));
                        description.setText(description.getText()+info.getDescription());
                    }

                    @Override
                    public void onFailure(Call<TuitionModel> call, Throwable throwable) {
                        Toast.makeText(SingleTuitionViewActivity.this,"server error",Toast.LENGTH_SHORT).show();
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