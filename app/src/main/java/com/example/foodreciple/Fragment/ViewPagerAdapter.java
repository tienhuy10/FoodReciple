package com.example.foodreciple.Fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> arr;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> arr) {
        super(fragmentActivity);
        this.arr = arr;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        return arr.get(position);
//        switch (position){
//            case 0:
//                return new Home();
//            case 1:
//                return new Saved();
//            default:
//                return new Home();
//        }
    }

    @Override
    public int getItemCount() {
        return arr.size();
    }
}
