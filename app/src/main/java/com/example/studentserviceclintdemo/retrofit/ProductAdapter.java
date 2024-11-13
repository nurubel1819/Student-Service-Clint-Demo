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
import com.example.studentserviceclintdemo.model.ProductGetDto;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    Context context;
    List<ProductGetDto> all_product_list;

    public ProductAdapter(Context context, List<ProductGetDto> all_product_list) {
        this.context = context;
        this.all_product_list = all_product_list;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.one_product_view,parent,false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
        holder.name.setText(all_product_list.get(position).getName());
        holder.location.setText("Location :"+all_product_list.get(position).getLocation());
        holder.price.setText(String.valueOf(all_product_list.get(position).getPrice())+" tk");

        String image_url = RetrofitInstance.getBase_url()+"file/images/"+all_product_list.get(position).getImage();
        Glide.with(context).load(image_url).into(holder.imageView);

        // click singe item
        holder.one_product_info_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //

                // some change in
                Intent intent = new Intent(context, SingleProductViewActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_product_list.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,location,price;
        LinearLayout one_product_info_id;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image_view_id);
            name = itemView.findViewById(R.id.one_product_name_id);
            location = itemView.findViewById(R.id.one_product_location_id);
            price = itemView.findViewById(R.id.one_product_price_id);
            one_product_info_id = itemView.findViewById(R.id.one_product_info_id);
        }
    }
}
