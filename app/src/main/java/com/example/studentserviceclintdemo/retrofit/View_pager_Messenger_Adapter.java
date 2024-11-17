package com.example.studentserviceclintdemo.retrofit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.studentserviceclintdemo.fragment.StudentFragment;
import com.example.studentserviceclintdemo.fragment.TeacherFragment;

public class View_pager_Messenger_Adapter extends FragmentPagerAdapter {
    public View_pager_Messenger_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0) return new StudentFragment();
        else return new TeacherFragment();
    }

    @Override
    public int getCount() {
        return 2; // number of Fragment
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0) return "Student";
        else return "Teacher";
    }
}
