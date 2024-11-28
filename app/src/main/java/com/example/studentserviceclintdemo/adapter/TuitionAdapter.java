package com.example.studentserviceclintdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.activity.SingleTuitionViewActivity;
import com.example.studentserviceclintdemo.model.TuitionModel;

import java.util.List;

public class TuitionAdapter extends RecyclerView.Adapter<TuitionAdapter.TuitionViewHolder> {

    Context context;
    List<TuitionModel> all_tuition;

    public TuitionAdapter(Context context, List<TuitionModel> all_tuition) {
        this.context = context;
        this.all_tuition = all_tuition;
    }

    @NonNull
    @Override
    public TuitionAdapter.TuitionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_tuition_view,parent,false);
        return new TuitionAdapter.TuitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuitionAdapter.TuitionViewHolder holder, int position) {
        String category = all_tuition.get(position).getCategory();
        holder.category.setText(category);
        holder.location.setText("Location : "+all_tuition.get(position).getLocation());
        if(category.equals("Teacher"))
        {
            holder.imageView.setImageResource(R.drawable.teacher_icon);
            holder.cls.setText("Class : not applicable");
            holder.faculty_group.setText("Faculty : "+all_tuition.get(position).getFaculty());
        }
        else
        {
            holder.imageView.setImageResource(R.drawable.student_icon);
            holder.cls.setText("Class : "+all_tuition.get(position).getCls());
            holder.faculty_group.setText("Group : "+all_tuition.get(position).getGrp());
        }
        holder.gender.setText("Gender : "+all_tuition.get(position).getGender());
        holder.fee.setText("Fee : "+all_tuition.get(position).getFee());

        // one new activity to click tuition details
        holder.one_tuition_info_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleTuitionViewActivity.class);
                intent.putExtra("phone_number",all_tuition.get(position).getPhone());
                intent.putExtra("tuition_id",all_tuition.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_tuition.size();
    }

    public class TuitionViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView category,location,faculty_group,gender,cls,fee;
        LinearLayout one_tuition_info_id;


        public TuitionViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.one_tuition_image_view_id);
            category = itemView.findViewById(R.id.one_tuition_category_id);
            location = itemView.findViewById(R.id.one_tuition_location_id);
            faculty_group = itemView.findViewById(R.id.one_tuition_faculty_group_id);
            gender = itemView.findViewById(R.id.one_tuition_gender_id);
            cls =  itemView.findViewById(R.id.one_tuition_cls_id);
            fee =  itemView.findViewById(R.id.one_tuition_fee_id);
            one_tuition_info_id = itemView.findViewById(R.id.one_tuition_info_id);
        }
    }
}
