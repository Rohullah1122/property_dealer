package com.example.property_dealer.adabters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_houseinfo;
import com.example.property_dealer.databinding.ActivityLayoutActivityBinding;
import com.example.property_dealer.databinding.LayoutInsertedhousesBinding;
import com.example.property_dealer.layout_activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adabter_insertedhouses extends RecyclerView.Adapter<adabter_insertedhouses.insertedhouse> {
    Context context;
    ArrayList<cls_houseinfo> ALH;
    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;



    public adabter_insertedhouses(Context context, ArrayList<cls_houseinfo> ALH) {
        this.context = context;
        this.ALH = ALH;
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public insertedhouse onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_insertedhouses,parent,false);
        return new insertedhouse(view);
    }

    @Override
    public void onBindViewHolder(@NonNull insertedhouse holder, int position) {
        cls_houseinfo houseinfo = ALH.get(position);
        holder.binding.tvbname.setText(houseinfo.getHouseexactlocation());
        holder.binding.tvrating.setText(houseinfo.getHouserent());
        Glide.with(context).load(houseinfo.getHouseimg())
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.imgHouse);


        holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hid1 = houseinfo.getHid().toString();
                    holder.binding.btnDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteimg(hid1);
                            deletehouse(hid1);
                            deletesave(hid1);

                        }
                    });
            }
        });

    }


    public void deleteimg(String hid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("houseimg").child("images").orderByChild("hid").equalTo(hid);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent in = new Intent(context,layout_activity.class);
                    context.startActivity(in);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void deletehouse(String hid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("HouseInfo").child("houses").orderByChild("hid").equalTo(hid);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent in = new Intent(context,layout_activity.class);
                    context.startActivity(in);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }



    public void deletesave(String hid){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("saves").child(auth.getUid()).orderByChild("hid").equalTo(hid);
        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent in = new Intent(context,layout_activity.class);
                    context.startActivity(in);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return ALH.size();
    }

    public class insertedhouse extends RecyclerView.ViewHolder{
        LayoutInsertedhousesBinding binding;
        public insertedhouse(@NonNull View itemView) {
            super(itemView);
            binding = LayoutInsertedhousesBinding.bind(itemView);
        }
    }






}

