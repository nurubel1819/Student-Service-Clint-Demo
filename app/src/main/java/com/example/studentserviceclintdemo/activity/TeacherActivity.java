package com.example.studentserviceclintdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.SingleResponseModel;
import com.example.studentserviceclintdemo.model.TuitionModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherActivity extends AppCompatActivity {
    Spinner faculty,location,gender;
    EditText name,institute,fee,description;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_teacher);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // find id component
        faculty = findViewById(R.id.teacher_department_id);
        location = findViewById(R.id.teacher_location_id);
        gender = findViewById(R.id.teacher_gender_id);
        name = findViewById(R.id.teacher_name_id);
        institute = findViewById(R.id.teacher_institute_name_id);
        fee = findViewById(R.id.teacher_fee_id_);
        description = findViewById(R.id.teacher_description_id);
        upload = findViewById(R.id.teacher_upload_id);

        ArrayList<String> all_faculty = new ArrayList<>();
        all_faculty.add("Arts");
        all_faculty.add("Business");
        all_faculty.add("Engineering");
        all_faculty.add("Science");
        all_faculty.add("Social_Sciences");
        ArrayAdapter<String> adapter_faculty = new ArrayAdapter<>(TeacherActivity.this, android.R.layout.simple_spinner_item,all_faculty);
        adapter_faculty.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        faculty.setAdapter(adapter_faculty);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        ArrayList<String> location_list = new ArrayList<>();
        apiInterface.get_all_location()
                .enqueue(new Callback<List<LocationModel>>() {
                    @Override
                    public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                        List<LocationModel> all_location = response.body();
                        // load all location server to app
                        for(int i=0;i<all_location.size();i++)
                        {
                            location_list.add(all_location.get(i).getLocation());
                        }
                        location_list.add("Others");
                        ArrayAdapter<String> adapter_location = new ArrayAdapter<>(TeacherActivity.this, android.R.layout.simple_spinner_item,location_list);
                        adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        location.setAdapter(adapter_location);
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                        Toast.makeText(TeacherActivity.this,"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });

        ArrayList<String> all_gender = new ArrayList<>();
        all_gender.add("Male");
        all_gender.add("Female");
        ArrayAdapter<String> adapter_gender = new ArrayAdapter<>(TeacherActivity.this, android.R.layout.simple_spinner_item,all_gender);
        adapter_gender.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        gender.setAdapter(adapter_gender);

        // database work
        LocalDB localDB = new LocalDB(TeacherActivity.this);
        ArrayList<LoginModel> login_info = localDB.find_login_info();

        //upload button work
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if(name.getText().toString().isEmpty()||institute.getText().toString().isEmpty()||
                fee.getText().toString().isEmpty()||location.getSelectedItem().toString().isEmpty()||
                faculty.getSelectedItem().toString().isEmpty()||gender.getSelectedItem().toString().isEmpty())
                {
                    Toast.makeText(TeacherActivity.this,"Fill up all text file",Toast.LENGTH_SHORT).show();
                }
                else if(login_info.isEmpty())
                {
                    Toast.makeText(TeacherActivity.this,"Login again your account",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    TuitionModel model = new TuitionModel();
                    model.setCategory("Teacher");
                    model.setPhone(login_info.get(0).getPhone());
                    model.setName(name.getText().toString());
                    model.setInstitute(institute.getText().toString());
                    model.setFaculty(faculty.getSelectedItem().toString());
                    model.setLocation(location.getSelectedItem().toString());
                    model.setGender(gender.getSelectedItem().toString());
                    model.setCls(0);
                    model.setFee(Double.parseDouble(fee.getText().toString()));
                    model.setDescription(description.getText().toString());

                    apiInterface.upload_new_teacher(model)
                            .enqueue(new Callback<SingleResponseModel>() {
                                @Override
                                public void onResponse(Call<SingleResponseModel> call, Response<SingleResponseModel> response) {
                                    if(response.body()!=null)
                                    {
                                        String res = response.body().getMessage();
                                        if(res.equals("Upload Successful"))
                                        {
                                            Toast.makeText(TeacherActivity.this,res,Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else Toast.makeText(TeacherActivity.this,res,Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(TeacherActivity.this,"Empty response",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<SingleResponseModel> call, Throwable throwable) {
                                    Toast.makeText(TeacherActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

    }
}