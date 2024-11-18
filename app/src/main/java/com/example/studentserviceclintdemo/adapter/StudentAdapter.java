package com.example.studentserviceclintdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.activity.SingleStudentViewActivity;
import com.example.studentserviceclintdemo.model.StudentModel;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    Context context;
    List<StudentModel> all_student;

    public StudentAdapter(Context context, List<StudentModel> all_student) {
        this.context = context;
        this.all_student = all_student;
    }

    @NonNull
    @Override
    public StudentAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_student_view,parent,false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.StudentViewHolder holder, int position) {
        holder.student_class.setText("Class : "+all_student.get(position).getCls());
        holder.location.setText("Location : "+all_student.get(position).getLocation());
        holder.group.setText("Group : "+all_student.get(position).getDepartment());
        holder.gender.setText("Gender : "+all_student.get(position).getGender());

        //click single item view
        holder.one_student_info_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleStudentViewActivity.class);
                intent.putExtra("phone_number",all_student.get(position).getPhone());
                intent.putExtra("student_id",all_student.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_student.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView student_class,location,group,gender;
        LinearLayout one_student_info_id;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            one_student_info_id = itemView.findViewById(R.id.one_student_info_id);
            student_class = itemView.findViewById(R.id.one_student_class_id);
            location = itemView.findViewById(R.id.one_student_location_id);
            group = itemView.findViewById(R.id.one_student_group_id);
            gender = itemView.findViewById(R.id.one_student_gender_id);
        }
    }
}
