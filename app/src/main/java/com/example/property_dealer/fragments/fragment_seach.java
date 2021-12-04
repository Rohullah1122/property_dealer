package com.example.property_dealer.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.property_dealer.R;
import com.example.property_dealer.adabters.adabter_houses;
import com.example.property_dealer.classess.cls_houseinfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class fragment_seach extends Fragment {
    AutoCompleteTextView txt1;
    RecyclerView rv1;
    FirebaseAuth auth;
    FirebaseDatabase database;
    adabter_houses adabter_insertedhouses;
    ArrayList<cls_houseinfo> ALH;
    cls_houseinfo houseinfo;
    cls_houseinfo clsHouseinfo;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_seach() {
        // Required empty public constructor
    }


    public static fragment_seach newInstance(String param1, String param2) {
        fragment_seach fragment = new fragment_seach();
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

        View view =  inflater.inflate(R.layout.fragment_seach, container, false);
        rv1 = (RecyclerView) view.findViewById(R.id.rvsearch);
        txt1 = (AutoCompleteTextView) view.findViewById(R.id.txtsearch) ;

        ALH = new ArrayList<>();
        auth = FirebaseAuth.getInstance();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        final ArrayAdapter<String> autoComplete = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1);
        adabter_insertedhouses = new adabter_houses(getContext(),ALH);
        rv1.setAdapter(adabter_insertedhouses);




        database.child("HouseInfo").child("houses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    String location2 = ds.child("houseexactlocation").getValue(String.class);
                    autoComplete.add(location2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Can not Find", Toast.LENGTH_SHORT).show();
            }
        });
        txt1.setAdapter(autoComplete);
        txt1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String location1= txt1.getText().toString();
                searchlocation(location1);
            }

            private void searchlocation(String location) {
                database.child("HouseInfo").child("houses")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ALH.clear();
                                for (DataSnapshot snapshot1 :snapshot.getChildren()){
                                    cls_houseinfo clsHouseinfo = snapshot1.getValue(cls_houseinfo.class);
                                    if (clsHouseinfo.getHouseexactlocation().equals(location)){
                                        ALH.add(clsHouseinfo);
                                        adabter_insertedhouses.notifyDataSetChanged();
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), "دیتابس مشکیل دارد", Toast.LENGTH_SHORT).show();
                            }
                        });


            }

        });


    return view;
    }
}