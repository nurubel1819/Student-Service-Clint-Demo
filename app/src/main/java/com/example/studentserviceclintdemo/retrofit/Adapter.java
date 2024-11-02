package com.example.studentserviceclintdemo.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.UserModel;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.CustomViewHolder> {

    Context context;
    List<UserModel> userModelList;

    public Adapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public Adapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_user_info,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.CustomViewHolder holder, int position) {
        holder.id.setText("Id : "+userModelList.get(position).getId().toString());
        holder.name.setText("Name : "+userModelList.get(position).getName().toString());
        holder.phone.setText("Phone : "+userModelList.get(position).getPhone().toString());
        holder.password.setText("Password : "+userModelList.get(position).getPassword().toString());
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView id,name,phone,password;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.user_id_id);
            name = itemView.findViewById(R.id.user_name_id);
            phone = itemView.findViewById(R.id.user_phone_id);
            password = itemView.findViewById(R.id.user_password_id);
        }
    }
}
