package com.example.property_dealer.adabters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_houseinfo;
import com.example.property_dealer.databinding.LayoutHousesBinding;
import com.example.property_dealer.house_details;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adabter_houses extends RecyclerView.Adapter<adabter_houses.houseviewholder>{
    Context context;
    ArrayList<cls_houseinfo> ALH;
    FirebaseAuth auth;
    FirebaseDatabase database;


    public adabter_houses(Context context, ArrayList<cls_houseinfo> ALH) {
        this.context = context;
        this.ALH = ALH;

    }

    @NonNull
    @Override
    public houseviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_houses,parent,false);
        return new houseviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull houseviewholder holder, int position) {
        cls_houseinfo houseinfo = ALH.get(position);
        holder.binding.tvbname.setText(houseinfo.getHouseexactlocation());
        holder.binding.tvrating.setText(houseinfo.getHouserent());
        Glide.with(context).load(houseinfo.getHouseimg())
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.imgHouse);
        holder.binding.btnmoreinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, house_details.class);
                intent.putExtra("rent",houseinfo.getHouserent());
                intent.putExtra("loc",houseinfo.getHouseloc());
                intent.putExtra("uid",houseinfo.getUserid());
                intent.putExtra("houseimg",houseinfo.getHouseimg());
                intent.putExtra("exactloc",houseinfo.getHouseexactlocation());
                intent.putExtra("Woner",houseinfo.getUsername());
                intent.putExtra("userimg",houseinfo.getUserprofile());
                intent.putExtra("phonenumber",houseinfo.getPhone());
                intent.putExtra("rooms",houseinfo.getNumberofroom());
                intent.putExtra("txttype",houseinfo.getType());
                intent.putExtra("hid",houseinfo.getHid());
                intent.putExtra("kitchennumber",houseinfo.getNumberkitchen());
                intent.putExtra("washrooms",houseinfo.getNumberwashroom());
                intent.putExtra("housewidth",houseinfo.getHousewidth());
                intent.putExtra("househeigh",houseinfo.getHousehight());
                context.startActivity(intent);
            }
        });



        holder.binding.btnsaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hid,exactloc,housimg,location,rent,phone,userid,uname,uprofile,numbroom,type,kitchens,washroom,housewithd,househight;
                hid = houseinfo.getHid().toString();
                exactloc = houseinfo.getHouseexactlocation();
                housimg = houseinfo.getHouseimg().toString();
                location = houseinfo.getHouseloc().toString();
                rent = houseinfo.getHouserent().toString();
                phone = houseinfo.getPhone().toString();
                userid = houseinfo.getUserid().toString();
                uname = houseinfo.getUsername().toString();
                uprofile = houseinfo.getUserprofile().toString();
                numbroom = houseinfo.getNumberofroom().toString();
                type = houseinfo.getType().toString();
                kitchens = houseinfo.getNumberkitchen();
                washroom = houseinfo.getNumberwashroom();
                housewithd = houseinfo.getHousewidth();
                househight = houseinfo.getHousehight();
                auth = FirebaseAuth.getInstance();

                database = FirebaseDatabase.getInstance();
                cls_houseinfo houseinfo1 = new cls_houseinfo(hid,uname,userid,uprofile,location,rent,exactloc,housimg,phone,numbroom,type,housewithd,househight,kitchens,washroom);
                database.getReference().child("saves").child(auth.getUid()).orderByChild("hid").equalTo(hid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(context, "قبلن وجود دارد", Toast.LENGTH_SHORT).show();

                        }else {
                            database.getReference().child("saves").child(auth.getUid()).push().setValue(houseinfo1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(context, "سفت شد", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });
    }


    @Override
    public int getItemCount() {
        return ALH.size();
    }




    public class houseviewholder extends RecyclerView.ViewHolder{
        LayoutHousesBinding binding;
        public houseviewholder(@NonNull View itemView) {
            super(itemView);
            binding = LayoutHousesBinding.bind(itemView);
        }
    }
}
