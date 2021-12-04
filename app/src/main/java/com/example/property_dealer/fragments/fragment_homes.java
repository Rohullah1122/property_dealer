package com.example.property_dealer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.property_dealer.R;
import com.example.property_dealer.adabters.adabter_houses;
import com.example.property_dealer.classess.cls_houseinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class fragment_homes extends Fragment {
    RecyclerView rv;
    ArrayList<cls_houseinfo> ALH;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public fragment_homes() {

    }


    public static fragment_homes newInstance(String param1, String param2) {
        fragment_homes fragment = new fragment_homes();
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
        View v = inflater.inflate(R.layout.fragment_homes, container, false);
        rv = (RecyclerView) v.findViewById(R.id.rvhome);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ALH = new ArrayList<>();
        storage = FirebaseStorage.getInstance();

        adabter_houses adabterHouses = new adabter_houses(getContext(),ALH);
        rv.setAdapter(adabterHouses);
        database.getReference().child("HouseInfo").child("houses")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ALH.clear();
                        for (DataSnapshot snapshot1 :snapshot.getChildren()){
                            cls_houseinfo clsHouseinfo = snapshot1.getValue(cls_houseinfo.class);
                            ALH.add(clsHouseinfo);
                        }
                        adabterHouses.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),"didnt work", Toast.LENGTH_SHORT).show();
                    }
                });

        return v;



    }
}