package com.example.studentserviceclintdemo.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.studentserviceclintdemo.R;
import com.example.studentserviceclintdemo.fragment.AccountFragment;
import com.example.studentserviceclintdemo.fragment.HomeFragment;
import com.example.studentserviceclintdemo.fragment.PostFragment;
import com.example.studentserviceclintdemo.fragment.ProductFragment;
import com.example.studentserviceclintdemo.fragment.TuitionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bottom_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation_id);
        replace_fragment(new HomeFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();
                if(id==R.id.home_menu_id){
                    replace_fragment(new HomeFragment());
                }
                else if(id==R.id.tutor_menu_id){
                    replace_fragment(new TuitionFragment());
                }
                else if(id==R.id.post_menu_id){
                    replace_fragment(new PostFragment());
                }
                else if(id==R.id.product_menu_id){
                    replace_fragment(new ProductFragment());
                }
                else if(id==R.id.account_menu_id){
                    replace_fragment(new AccountFragment());
                }

                return true;
            }
        });
    }
    private void replace_fragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_id,fragment);
        fragmentTransaction.commit();
    }
}