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

public class StudentActivity extends AppCompatActivity {

    Spinner cls,gender,location,group,version;
    EditText name,institute,fee,description;
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //find id
        cls = findViewById(R.id.student_class_id);
        gender = findViewById(R.id.student_gender_id);
        group = findViewById(R.id.student_group_id);
        version = findViewById(R.id.student_version_id);
        location = findViewById(R.id.student_location_id);
        name = findViewById(R.id.student_name_id);
        institute = findViewById(R.id.student_institute_name_id);
        fee = findViewById(R.id.student_fee_id);
        description = findViewById(R.id.student_description_id);
        upload = findViewById(R.id.student_upload_id);


        ArrayList<String> all_cls = new ArrayList<>();
        for(int i=1;i<13;i++) all_cls.add(String.valueOf(i));
        ArrayAdapter<String> adapter_cls = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item,all_cls);
        adapter_cls.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        cls.setAdapter(adapter_cls);

        ArrayList<String> all_gender = new ArrayList<>();
        all_gender.add("Male");
        all_gender.add("Female");
        ArrayAdapter<String> adapter_gender = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item,all_gender);
        adapter_gender.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        gender.setAdapter(adapter_gender);

        ArrayList<String> all_group = new ArrayList<>();
        all_group.add("No");
        all_group.add("Science");
        all_group.add("Commerce ");
        all_group.add("Arts");
        ArrayAdapter<String> adapter_group = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item,all_group);
        adapter_group.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        group.setAdapter(adapter_group);

        ArrayList<String> all_version = new ArrayList<>();
        all_version.add("Bangla");
        all_version.add("English");
        ArrayAdapter<String> adapter_version = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item,all_version);
        adapter_version.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        version.setAdapter(adapter_version);

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
                        ArrayAdapter<String> adapter_location = new ArrayAdapter<>(StudentActivity.this, android.R.layout.simple_spinner_item,location_list);
                        adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        location.setAdapter(adapter_location);
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                        Toast.makeText(StudentActivity.this,"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });

        // database work
        LocalDB localDB = new LocalDB(StudentActivity.this);
        ArrayList<LoginModel> login_info = localDB.find_login_info();
        //upload student details button
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().isEmpty()||institute.getText().toString().isEmpty()||
                fee.getText().toString().isEmpty()||description.getText().toString().isEmpty())
                {
                    Toast.makeText(StudentActivity.this,"Upload all text filed",Toast.LENGTH_SHORT).show();
                }
                else if(login_info.isEmpty())
                {
                    Toast.makeText(StudentActivity.this,"Login your account",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    TuitionModel model = new TuitionModel();
                    model.setCategory("Student");
                    model.setPhone(login_info.get(0).getPhone());
                    model.setName(name.getText().toString());
                    model.setInstitute(institute.getText().toString());
                    model.setVersion(version.getSelectedItem().toString());
                    model.setGrp(group.getSelectedItem().toString());
                    model.setLocation(location.getSelectedItem().toString());
                    model.setGender(gender.getSelectedItem().toString());
                    model.setCls(Integer.parseInt(cls.getSelectedItem().toString()));
                    model.setFee(Double.parseDouble(fee.getText().toString()));
                    model.setDescription(description.getText().toString());

                    apiInterface.upload_new_student(model)
                            .enqueue(new Callback<SingleResponseModel>() {
                                @Override
                                public void onResponse(Call<SingleResponseModel> call, Response<SingleResponseModel> response) {
                                    if(response.body()!=null)
                                    {
                                        String res = response.body().getMessage();
                                        if(res.equals("Upload Successful"))
                                        {
                                            Toast.makeText(StudentActivity.this,res,Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else Toast.makeText(StudentActivity.this,res,Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(StudentActivity.this,"Empty response",Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onFailure(Call<SingleResponseModel> call, Throwable throwable) {
                                    Toast.makeText(StudentActivity.this,"Server Error",Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }
}