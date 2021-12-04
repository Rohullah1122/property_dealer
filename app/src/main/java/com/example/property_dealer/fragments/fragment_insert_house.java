package com.example.property_dealer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.property_dealer.R;
import com.example.property_dealer.adabters.adabter_insertedhouses;
import com.example.property_dealer.classess.cls_houseinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragment_insert_house extends Fragment {

 RecyclerView rvp;
    FirebaseAuth auth;
    FirebaseDatabase database;
    adabter_insertedhouses adabter_insertedhouse;
    ArrayList<cls_houseinfo>ALH;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public fragment_insert_house() {

    }


    public static fragment_insert_house newInstance(String param1, String param2) {
        fragment_insert_house fragment = new fragment_insert_house();
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

        View view =  inflater.inflate(R.layout.fragment_insert_house, container, false);
        rvp = (RecyclerView) view.findViewById(R.id.rvinsertedhouse);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String houseid = auth.getUid();
        ALH = new ArrayList<>();
        adabter_insertedhouse = new adabter_insertedhouses(getContext(),ALH);
        rvp.setAdapter(adabter_insertedhouse);
        String user = auth.getCurrentUser().getUid();





        database.getReference().child("HouseInfo").child("houses")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ALH.clear();
                        for (DataSnapshot snapshot1 :snapshot.getChildren()){
                            cls_houseinfo clsHouseinfo = snapshot1.getValue(cls_houseinfo.class);
                            if (clsHouseinfo.getUserid().equals(user)){
                                ALH.add(clsHouseinfo);
                                adabter_insertedhouse.notifyDataSetChanged();
                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "not Working", Toast.LENGTH_SHORT).show();
                    }
                });

        return view;
    }

    }
