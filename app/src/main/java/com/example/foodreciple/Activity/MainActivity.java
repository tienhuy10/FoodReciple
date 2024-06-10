package com.example.foodreciple.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.foodreciple.Fragment.Home;
import com.example.foodreciple.Fragment.Notification;
import com.example.foodreciple.Fragment.Post;
import com.example.foodreciple.Fragment.Profile;
import com.example.foodreciple.Fragment.Saved;
import com.example.foodreciple.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    LinearLayout viewPager;
    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_NavView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if(itemId == R.id.home){
                    loadFragment(new Home(), false);

                } else if (itemId == R.id.saved) {
                    loadFragment(new Saved(), false);

                } else if (itemId == R.id.post) {
                    loadFragment(new Post(), false);

                }else{
                    loadFragment(new Profile(), false);
                }
                return true;
            }
        });

        loadFragment(new Home(), true);

    }

    private void loadFragment(Fragment fragment, boolean isAppInitialized){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(isAppInitialized){
            fragmentTransaction.add(R.id.fragment_container, fragment);
        } else {
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }

        fragmentTransaction.commit();
    }

}