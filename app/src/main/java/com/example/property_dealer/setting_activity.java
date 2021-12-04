package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.property_dealer.classess.cls_rating;
import com.example.property_dealer.classess.cls_user;
import com.example.property_dealer.databinding.ActivitySettingActivityBinding;
import com.example.property_dealer.fragments.fragment_insert_house;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setting_activity extends AppCompatActivity {
    ActivitySettingActivityBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    cls_user user;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        database.getReference("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(cls_user.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        binding.btnInsertedhouses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting_activity.this, hose_info_activity.class);
                startActivity(intent);
            }
        });
        binding.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder alert = new AlertDialog.Builder(setting_activity.this);
                alert.setTitle("درج نظریات");
                View view = getLayoutInflater().inflate(R.layout.layout_comment,null);
                alert.setView(view);
                alert.setCancelable(false);
                alert.setPositiveButton("زخیره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText Name = view.findViewById(R.id.tvalertd);
                        String feedback = Name.getText().toString();
                        if (feedback.isEmpty()) {
                            Name.setError("لوطفن بنوسید");
                            Name.requestFocus();
                            return;
                        }else{
                            cls_rating cls_rating = new cls_rating();
                            cls_rating.setUid(auth.getUid());
                            cls_rating.setUname(user.getName());
                            cls_rating.setFeedback(feedback);
                            database.getReference().child("feedback").child("feedbacks").push()
                                    .setValue(cls_rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(setting_activity.this,   "ممنون از نظریات شما" +user.getName(), Toast.LENGTH_SHORT).show();
                                    alert.setCancelable(true);
                                }
                            });
                        }
                    }

                });

                alert.setNegativeButton("کنسل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.setCancelable(true);
                    }
                });

                alert.show();

            }
        });
        binding.btnRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert1 = new AlertDialog.Builder(setting_activity.this);
                alert1.setTitle("درج رات");
                View view1 = getLayoutInflater().inflate(R.layout.layout_rating,null);
                alert1.setView(view1);
                alert1.setCancelable(false);

                alert1.setPositiveButton("زخیره", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RatingBar ratingbar = view1.findViewById(R.id.ratingBar);
                        int rating = ratingbar.getNumStars();
                        if (rating == 0) {
                            Toast.makeText(setting_activity.this,  "لوطفن رات نماید" +user.getName(), Toast.LENGTH_SHORT).show();
                            return;
                        }else{
                            cls_rating cls_rating = new cls_rating();
                            cls_rating.setUid(auth.getUid());
                            cls_rating.setUname(user.getName());
                            cls_rating.setUname(rating +"");
                            database.getReference().child("rating").child("rates").push()
                                    .setValue(cls_rating).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(setting_activity.this, "ممنون از نظریات شما", Toast.LENGTH_SHORT).show();
                                    alert1.setCancelable(true);
                                }
                            }); }
                    }
                });

                alert1.setNegativeButton("کنسل", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert1.setCancelable(true);
                    }
                });

                alert1.show();
            }
        });
        binding.btnInserthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new fragment_insert_house();
                loadfragment(fragment);
                hide();

            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(setting_activity.this, Login.class);
                startActivity(intent);
                setting_activity.this.finishAffinity();
            }
        });



    }


    public void hide(){
        binding.btnRating.setVisibility(View.GONE);
        binding.btnComment.setVisibility(View.GONE);
        binding.btnLogout.setVisibility(View.GONE);
        binding.btnInsertedhouses.setVisibility(View.GONE);
        binding.btnInserthome.setVisibility(View.GONE);

        binding.im8.setVisibility(View.GONE);
        binding.imageView2.setVisibility(View.GONE);
        binding.img3.setVisibility(View.GONE);
        binding.img4.setVisibility(View.GONE);
        binding.imageView2.setVisibility(View.GONE);
        binding.img6.setVisibility(View.GONE);


        binding.view1.setVisibility(View.GONE);
        binding.view3.setVisibility(View.GONE);
        binding.view4.setVisibility(View.GONE);
        binding.view5.setVisibility(View.GONE);
        binding.view6.setVisibility(View.GONE);


        binding.fl.setVisibility(View.VISIBLE);

    }

    public void loadfragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl,fragment).commit();
    }

    }
