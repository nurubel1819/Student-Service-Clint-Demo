package com.example.studentserviceclintdemo.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.RentModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RentAdapter;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    Button filter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // my code here
        recyclerView = view.findViewById(R.id.rent_recycle_view_id);
        filter = view.findViewById(R.id.filter_rent_id);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.get_all_rent_info()
                .enqueue(new Callback<List<RentModel>>() {
                    @Override
                    public void onResponse(Call<List<RentModel>> call, Response<List<RentModel>> response) {
                        //
                        List<RentModel> all_rent = response.body();
                        RentAdapter adapter = new RentAdapter(getContext(),all_rent);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<RentModel>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"Data can't load,Server error",Toast.LENGTH_SHORT).show();
                    }
                });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.daylog_rent_filter);

                // find dialog id
                EditText price = dialog.findViewById(R.id.filter_rent_price_id);
                Spinner location = dialog.findViewById(R.id.filter_rent_location_id);
                Spinner member = dialog.findViewById(R.id.filter_rent_member_id);
                Spinner floor = dialog.findViewById(R.id.filter_rent_floor_id);
                Button filter = dialog.findViewById(R.id.filter_rent_dialog_button_id);

                //load drop down menu
                ArrayList<String> all_member = new ArrayList<>();
                all_member.add("5");
                for(int i=1;i<5;i++) all_member.add(String.valueOf(i));
                ArrayAdapter<String> adapter_member = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,all_member);
                adapter_member.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                member.setAdapter(adapter_member);


                ArrayList<String> all_floor = new ArrayList<>();
                all_floor.add("11");
                for(int i=1;i<11;i++) all_floor.add(String.valueOf(i));
                ArrayAdapter<String> adapter_floor = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,all_floor);
                adapter_floor.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                floor.setAdapter(adapter_floor);


                ArrayList<String> location_list = new ArrayList<>();
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
                });

                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // filter code here
                        recyclerView.removeAllViewsInLayout();

                        double rent_price = -1;
                        if(!price.getText().toString().isEmpty()) rent_price = Double.parseDouble(price.getText().toString());

                        RentModel model = new RentModel();
                        model.setLocation(location.getSelectedItem().toString());
                        model.setPrice(rent_price);
                        model.setFloor(Integer.parseInt(floor.getSelectedItem().toString()));
                        model.setMember(Integer.parseInt(member.getSelectedItem().toString()));

                        apiInterface.filter_rent_all(model)
                                .enqueue(new Callback<List<RentModel>>() {
                                    @Override
                                    public void onResponse(Call<List<RentModel>> call, Response<List<RentModel>> response) {
                                        List<RentModel> all_rent = response.body();
                                        RentAdapter adapter = new RentAdapter(getContext(),all_rent);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<List<RentModel>> call, Throwable throwable) {

                                    }
                                });
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}