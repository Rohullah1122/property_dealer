package com.example.property_dealer.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;


public class profile extends Fragment {
    ImageView img1;
    EditText txtname;
    EditText phone;
    Spinner spcountry;
    Button btn_update;
    Button btnhome;
    FrameLayout frameLayout;

    FirebaseDatabase database;
    FirebaseAuth auth;
    FirebaseStorage storage;
    String userid;
    Uri selectedImage;
    cls_user user;
    ProgressDialog dialog;
    ArrayList<cls_user> ALU;
    private FirebaseUser currenuser;
    com.example.property_dealer.classess.cls_houseinfo cls_houseinfo;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public profile() {
        // Required empty public constructor
    }


    public static profile newInstance(String param1, String param2) {
        profile fragment = new profile();
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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        img1 = (ImageView)view.findViewById(R.id.userimage);
        txtname = (EditText)view.findViewById(R.id.txtname);
        phone = (EditText)view.findViewById(R.id.txtphone);
        spcountry = (Spinner)view.findViewById(R.id.spinnercountry);
        btnhome = (Button)view.findViewById(R.id.btn_houses);
        frameLayout = (FrameLayout)view.findViewById(R.id.flprofile);

        dialog = new ProgressDialog(getContext());
        dialog.setCancelable(false);
        dialog.setMessage("loading profile");
        dialog.show();

        auth = FirebaseAuth.getInstance();
        ALU = new ArrayList<>();
        storage = FirebaseStorage.getInstance();
        userid = auth.getUid();
        database = FirebaseDatabase.getInstance();
        select();







        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragment_insert_house();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.flprofile, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                img1.setVisibility(View.GONE);
                phone.setVisibility(View.GONE);
                spcountry.setVisibility(View.GONE);
                btnhome.setVisibility(View.GONE);
                txtname.setVisibility(View.GONE);
                frameLayout.setVisibility(View.VISIBLE);
            }

        });



        return  view;

    }
    public void select(){
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database.getReference("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cls_user loginuser = dataSnapshot.getValue(cls_user.class);
                if (loginuser != null){
                    dialog.dismiss();
                    String name = loginuser.getName();
                    String phon  = loginuser.getPhone();
                    String loc = loginuser.getLocation();
                    String img = loginuser.getImage();
                    txtname.setText(name+"");
                            String strArray[] = loc. split(" ");
                           phone.setText(phon);
                            spcountry.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item, strArray));
                    if (img.equals("no Image")){
                        Glide.with(profile.this)
                                .load(img)
                                .placeholder(R.drawable.ic_profile)
                                .into(img1);
                    }else{
                        Glide.with(profile.this)
                                .load(loginuser.image)
                                .into(img1);

                    }
                }else {
                    Toast.makeText(getContext(), "لود نشد", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        }


    }


