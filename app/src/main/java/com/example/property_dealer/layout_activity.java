package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.property_dealer.databinding.ActivityLayoutActivityBinding;
import com.example.property_dealer.fragments.fragment_homes;
import com.example.property_dealer.fragments.fragment_seach;
import com.example.property_dealer.fragments.profile;
import com.example.property_dealer.fragments.saves_saves;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class layout_activity extends AppCompatActivity {
    ActivityLayoutActivityBinding binding;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLayoutActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fragment = new fragment_homes();
        loadfragment(fragment);


        binding.navigationbar.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btn_home:
                        fragment = new fragment_homes();
                        loadfragment(fragment);
                        break;

                    case R.id.btn_profile:
                        fragment = new profile();
                        loadfragment(fragment);
                        break;

                    case R.id.btn_saves:
                        fragment = new saves_saves();
                        loadfragment(fragment);
                        break;

                    case R.id.btn_search:
                        fragment = new fragment_seach();
                        loadfragment(fragment);
                        break;

                    case R.id.btn_setting:
                    Intent intent = new Intent(layout_activity.this,setting_activity.class);
                    startActivity(intent);
                        break;

                }
            }
        });



    }
    public void loadfragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment).commit();
    }
}