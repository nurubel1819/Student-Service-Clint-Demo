package com.example.studentserviceclintdemo.retrofit;

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

import com.bumptech.glide.Glide;
import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.activity.SingleProductViewActivity;
import com.example.studentserviceclintdemo.activity.SingleRentViewActivity;
import com.example.studentserviceclintdemo.model.RentModel;

import java.util.List;

public class RentAdapter extends RecyclerView.Adapter<RentAdapter.RentViewHolder> {

    Context context;
    List<RentModel> all_rent_list;

    public RentAdapter(Context context, List<RentModel> all_rent_list) {
        this.context = context;
        this.all_rent_list = all_rent_list;
    }

    @NonNull
    @Override
    public RentAdapter.RentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_rent_view,parent,false);
        return new RentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RentAdapter.RentViewHolder holder, int position) {
        holder.location.setText("Location : "+all_rent_list.get(position).getLocation());
        holder.floor.setText("Floor : "+all_rent_list.get(position).getFloor());
        holder.room_member.setText("Room Member : "+all_rent_list.get(position).getMember()+" জন");
        holder.rent_price.setText("Rent : "+all_rent_list.get(position).getPrice()+" tk");

        //image load
        String image_url = RetrofitInstance.getBase_url()+"file/images/"+all_rent_list.get(position).getImage();
        Glide.with(context).load(image_url).into(holder.imageView);

        // click singe item
        holder.one_product_info_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SingleRentViewActivity.class);
                intent.putExtra("phone_number",all_rent_list.get(position).getPhone());
                intent.putExtra("rent_id",all_rent_list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_rent_list.size();
    }

    public class RentViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView location,floor,room_member,rent_price;
        LinearLayout one_product_info_id;

        public RentViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.one_rent_image_view_id);
            location = itemView.findViewById(R.id.one_rent_location_id);
            floor = itemView.findViewById(R.id.one_floor_id);
            room_member = itemView.findViewById(R.id.one_room_member_id);
            rent_price = itemView.findViewById(R.id.one_product_price_id);
            one_product_info_id = itemView.findViewById(R.id.one_rent_info_id);
        }
    }
}
