package com.example.property_dealer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.property_dealer.R;
import com.example.property_dealer.adabters.adabter_save_houses;
import com.example.property_dealer.classess.cls_houseinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class saves_saves extends Fragment {
    RecyclerView rv1;
    ArrayList<cls_houseinfo> ALH;
    FirebaseAuth auth;
    FirebaseDatabase database;
    adabter_save_houses adabter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public saves_saves() {
        // Required empty public constructor
    }

    public static saves_saves newInstance(String param1, String param2) {
        saves_saves fragment = new saves_saves();
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
        View view =  inflater.inflate(R.layout.fragment_saves_saves, container, false);
        rv1 = (RecyclerView)view.findViewById(R.id.rcsaved);
        ALH = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        adabter = new adabter_save_houses(getContext(),ALH);
        rv1.setAdapter(adabter);
        database.getReference().child("saves").child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ALH.clear();
                        for (DataSnapshot snapshot1 :snapshot.getChildren()){
                            cls_houseinfo clsHouseinfo = snapshot1.getValue(cls_houseinfo.class);
                                ALH.add(clsHouseinfo);

                        }
                        adabter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(), "انجام نشد", Toast.LENGTH_SHORT).show();
                    }
                });




        return view;
    }
}