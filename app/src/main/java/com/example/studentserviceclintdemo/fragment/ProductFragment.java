package com.example.studentserviceclintdemo.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.CategoryModel;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.ProductModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.ProductAdapter;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends Fragment {

    // code here rubel
    Button filter_button;
    Spinner category;
    EditText search_product,price;
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filter_button = view.findViewById(R.id.filter_product_button_id);
        category = view.findViewById(R.id.filter_product_category_id);
        search_product = view.findViewById(R.id.search_product_id);
        price = view.findViewById(R.id.filter_product_price_id);
        recyclerView = view.findViewById(R.id.product_recycle_view_id);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        ArrayList<String> category_list = new ArrayList<>();
        category_list.add("All");
        apiInterface.get_all_category()
                .enqueue(new Callback<List<CategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                        List<CategoryModel> all_category = response.body();
                        //ArrayList<String> category_list = new ArrayList<>();
                        for(int i=0;i<all_category.size();i++)
                        {
                            category_list.add(all_category.get(i).getCategory());
                        }
                        ArrayAdapter<String> adapter_category = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,category_list);
                        adapter_category.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        category.setAdapter(adapter_category);
                    }
                    @Override
                    public void onFailure(Call<List<CategoryModel>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });
        // load location list in drop down menu
        /*ArrayList<String> location_list = new ArrayList<>();
        location_list.add("All");
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
                        ArrayAdapter<String> adapter_location = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,location_list);
                        adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                        location.setAdapter(adapter_location);
                    }

                    @Override
                    public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"Location not load",Toast.LENGTH_SHORT).show();
                    }
                });*/

        // load all product
        recyclerView.removeAllViewsInLayout();
        apiInterface.get_all_product()
                .enqueue(new Callback<List<ProductModel>>() {
                    @Override
                    public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                        List<ProductModel> all_product = response.body();
                        ProductAdapter adapter = new ProductAdapter(getContext(),all_product);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ProductModel>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"data can't load",Toast.LENGTH_SHORT).show();
                    }
                });

        // filter all product
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.removeAllViewsInLayout();

                ProductModel model = new ProductModel();
                model.setCategory(category.getSelectedItem().toString());
                double product_price = -1;
                if(!price.getText().toString().isEmpty()) product_price = Double.parseDouble(price.getText().toString());
                model.setPrice(product_price);

                apiInterface.find_by_category_and_location(model)
                        .enqueue(new Callback<List<ProductModel>>() {
                            @Override
                            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                                List<ProductModel> all_product = response.body();
                                ProductAdapter adapter = new ProductAdapter(getContext(),all_product);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                            @Override
                            public void onFailure(Call<List<ProductModel>> call, Throwable throwable) {

                            }
                        });
            }
        });

        search_product.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                recyclerView.removeAllViewsInLayout();

                ProductModel model = new ProductModel();
                model.setName(search_product.getText().toString());

                apiInterface.filter_by_product_name(model)
                        .enqueue(new Callback<List<ProductModel>>() {
                            @Override
                            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                                List<ProductModel> all_product = response.body();
                                ProductAdapter adapter = new ProductAdapter(getContext(),all_product);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                recyclerView.setLayoutManager(linearLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                            @Override
                            public void onFailure(Call<List<ProductModel>> call, Throwable throwable) {

                            }
                        });

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}