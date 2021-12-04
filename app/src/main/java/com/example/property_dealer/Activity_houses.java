package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.property_dealer.adabters.adabter_imges;
import com.example.property_dealer.classess.cls_houseimg;
import com.example.property_dealer.databinding.ActivityHousesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_houses extends AppCompatActivity {
        ActivityHousesBinding binding;
    ArrayList<cls_houseimg> ALHI;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHousesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ALHI = new ArrayList<>();
        String userid = getIntent().getStringExtra("userid");
        String hid = getIntent().getStringExtra("hid");
        adabter_imges adabter = new adabter_imges(Activity_houses.this, ALHI);
        binding.rvphotos.setAdapter(adabter);
        database = FirebaseDatabase.getInstance();

        database.getReference("houseimg").child("images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ALHI.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    cls_houseimg houseimgs = snapshot1.getValue(cls_houseimg.class);
                    if (houseimgs.getHid().equals(hid)){
                        ALHI.add(houseimgs);
                    }
                }
                adabter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Activity_houses.this, "مشکل دیتابس", Toast.LENGTH_SHORT).show();
            }
        });
    }
}