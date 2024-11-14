package com.example.studentserviceclintdemo.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentserviceclintdemo.MainActivity;
import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.activity.PostForProductActivity;
import com.example.studentserviceclintdemo.localDatabase.LocalDB;
import com.example.studentserviceclintdemo.model.LoginModel;
import com.example.studentserviceclintdemo.model.UserModel;
import com.example.studentserviceclintdemo.retrofit.ApiInterface;
import com.example.studentserviceclintdemo.retrofit.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    // code
    TextView user_name,user_phone;
    Button save,logout;
    ImageView user_image;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find id
        user_name = view.findViewById(R.id.account_name_id);
        user_phone = view.findViewById(R.id.account_phone_number_id);
        save = view.findViewById(R.id.account_save_button_id);
        logout = view.findViewById(R.id.account_logout_button_id);
        user_image = view.findViewById(R.id.account_image_view_id);

        LocalDB localDB = new LocalDB(getContext());
        ArrayList<LoginModel> login_info = localDB.find_login_info();
        if(login_info.isEmpty())
        {
            Toast.makeText(getContext(),"Login again your account",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String user_phone_number = login_info.get(0).getPhone();

            ApiInterface apiInterface = RetrofitInstance.getRetrofit().create(ApiInterface.class);

            UserModel model = new UserModel();
            model.setPhone(user_phone_number);
            Log.d("kaka",user_phone_number);
            apiInterface.see_single_user_by_phone(model)
                    .enqueue(new Callback<UserModel>() {
                        @Override
                        public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                            UserModel user_info = response.body();
                            user_name.setText("Name : "+user_info.getName());
                            user_phone.setText("Phone Number : "+user_info.getPhone());

                        }

                        @Override
                        public void onFailure(Call<UserModel> call, Throwable throwable) {
                            Toast.makeText(getContext(),"server error",Toast.LENGTH_SHORT).show();
                        }
                    });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDB localDB1 = new LocalDB(getContext());
                localDB1.delete_login_info();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });

    }
}