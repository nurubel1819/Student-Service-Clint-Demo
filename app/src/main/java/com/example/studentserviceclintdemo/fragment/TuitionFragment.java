package com.example.studentserviceclintdemo.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.adapter.TuitionAdapter;
import com.example.studentserviceclintdemo.model.LocationModel;
import com.example.studentserviceclintdemo.model.TuitionModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TuitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TuitionFragment extends Fragment {
    RecyclerView recyclerView;
    Button filter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TuitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TutorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TuitionFragment newInstance(String param1, String param2) {
        TuitionFragment fragment = new TuitionFragment();
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
        return inflater.inflate(R.layout.fragment_tutor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // find id
        recyclerView = view.findViewById(R.id.tuition_recycle_view_id);
        filter = view.findViewById(R.id.filter_tuition_id);

        ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

        apiInterface.get_all_tuition()
                .enqueue(new Callback<List<TuitionModel>>() {
                    @Override
                    public void onResponse(Call<List<TuitionModel>> call, Response<List<TuitionModel>> response) {
                        List<TuitionModel> all_tuition = response.body();
                        TuitionAdapter adapter = new TuitionAdapter(getContext(),all_tuition);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(Call<List<TuitionModel>> call, Throwable throwable) {
                        Toast.makeText(getContext(),"Server error !!!!",Toast.LENGTH_SHORT).show();
                    }
                });
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_tuition_filter);

                //find dialog all component id
                Spinner category = dialog.findViewById(R.id.filter_tuition_category_id);
                EditText fee = dialog.findViewById(R.id.filter_tuition_fee_id);
                Spinner location = dialog.findViewById(R.id.filter_tuition_location_id);
                Spinner gender =  dialog.findViewById(R.id.filter_tuition_gender_id);
                Button filter_button = dialog.findViewById(R.id.filter_tuition_dialog_button_id);

                //load category
                ArrayList<String> all_category = new ArrayList<>();
                all_category.add("Teacher");
                all_category.add("Student");
                ArrayAdapter<String> adapter_category = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,all_category);
                adapter_category.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                category.setAdapter(adapter_category);

                //load gender
                ArrayList<String> all_gender = new ArrayList<>();
                all_gender.add("Male");
                all_gender.add("Female");
                ArrayAdapter<String> adapter_gender = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,all_gender);
                adapter_gender.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                gender.setAdapter(adapter_gender);

                ArrayList<String> all_location = new ArrayList<>();
                all_location.add("All");
                apiInterface.get_all_location()
                        .enqueue(new Callback<List<LocationModel>>() {
                            @Override
                            public void onResponse(Call<List<LocationModel>> call, Response<List<LocationModel>> response) {
                                List<LocationModel> response_all_location = response.body();
                                for(int i=0;i<response_all_location.size();i++)
                                {
                                    all_location.add(response_all_location.get(i).getLocation());
                                }
                                ArrayAdapter<String> adapter_location = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,all_location);
                                adapter_location.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                                location.setAdapter(adapter_location);
                            }

                            @Override
                            public void onFailure(Call<List<LocationModel>> call, Throwable throwable) {
                                Toast.makeText(getContext(),"Server error!!",Toast.LENGTH_SHORT).show();
                            }
                        });
                filter_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerView.removeAllViewsInLayout();
                        double tuition_fee = -1;
                        if(!fee.getText().toString().isEmpty())
                        {
                            tuition_fee = Double.parseDouble(fee.getText().toString());
                        }

                        TuitionModel model = new TuitionModel();
                        model.setCategory(category.getSelectedItem().toString());
                        model.setFee(tuition_fee);
                        model.setLocation(location.getSelectedItem().toString());
                        model.setGender(gender.getSelectedItem().toString());

                        apiInterface.filter_tuition_all(model)
                                .enqueue(new Callback<List<TuitionModel>>() {
                                    @Override
                                    public void onResponse(Call<List<TuitionModel>> call, Response<List<TuitionModel>> response) {
                                        List<TuitionModel> all_tuition = response.body();
                                        TuitionAdapter adapter = new TuitionAdapter(getContext(),all_tuition);
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                                        recyclerView.setLayoutManager(linearLayoutManager);
                                        recyclerView.setAdapter(adapter);
                                    }

                                    @Override
                                    public void onFailure(Call<List<TuitionModel>> call, Throwable throwable) {
                                        Toast.makeText(getContext(),"Server error",Toast.LENGTH_SHORT).show();
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