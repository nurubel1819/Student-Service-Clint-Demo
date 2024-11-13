package com.example.studentserviceclintdemo.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.activity.BottomNavigationActivity;
import com.example.studentserviceclintdemo.activity.TestUserActivity;
import com.example.studentserviceclintdemo.model.ProductGetDto;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.ProductAdapter;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

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
    EditText search_product;
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

        /*search_product = container.findViewById(R.id.search_product_id);
        recyclerView = container.findViewById(R.id.product_recycle_view_id);*/

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        search_product = view.findViewById(R.id.search_product_id);
        recyclerView = view.findViewById(R.id.product_recycle_view_id);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);
        apiInterface.get_all_product()
                .enqueue(new Callback<List<ProductGetDto>>() {
                    @Override
                    public void onResponse(Call<List<ProductGetDto>> call, Response<List<ProductGetDto>> response) {
                        List<ProductGetDto> all_product = response.body();
                        ProductAdapter adapter = new ProductAdapter(getContext(),all_product);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<ProductGetDto>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"data can't load",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}