package com.example.property_dealer.adabters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_houseinfo;
import com.example.property_dealer.databinding.LayoutSavedItemesBinding;
import com.example.property_dealer.house_details;
import com.example.property_dealer.layout_activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adabter_save_houses extends RecyclerView.Adapter<adabter_save_houses.houseviewholder>{
    Context context;
    ArrayList<cls_houseinfo> ALH;
    FirebaseAuth auth;


    public adabter_save_houses(Context context, ArrayList<cls_houseinfo> ALH) {
        this.context = context;
        this.ALH = ALH;
    }

    @NonNull
    @Override
    public adabter_save_houses.houseviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_saved_itemes,parent,false);
        return new  houseviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull houseviewholder holder, int position) {
        cls_houseinfo houseinfo = ALH.get(position);
        holder.binding.tvbname.setText(houseinfo.getHouseexactlocation());
        holder.binding.tvrating.setText(houseinfo.getHouserent());
        Glide.with(context).load(houseinfo.getHouseimg())
                .placeholder(R.drawable.ic_profile)
                .into(holder.binding.imgHouse);
        holder.binding.btnmore.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("rooms",houseinfo.numberofroom);
                intent.putExtra("txttype",houseinfo.getType());
                intent.putExtra("houseimg",houseinfo.getHouseimg());
                intent.putExtra("hid",houseinfo.getHid());
                intent.putExtra("kitchennumber",houseinfo.getNumberkitchen());
                intent.putExtra("washrooms",houseinfo.getNumberwashroom());
                intent.putExtra("housewidth",houseinfo.getHousewidth());
                intent.putExtra("househeigh",houseinfo.getHousehight());

                context.startActivity(intent);
            }
        });

            holder.binding.btndeletesave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String hid = houseinfo.getHid().toString();
                    deletesaved(hid);
                }
            });
    }

    public void deletesaved(String hid){
        auth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("saves").child(auth.getUid()).orderByChild("hid").equalTo(hid);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                    appleSnapshot.getRef().removeValue();
                    Intent in = new Intent(context, layout_activity.class);
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

    public class houseviewholder extends RecyclerView.ViewHolder{
        LayoutSavedItemesBinding binding;
        public houseviewholder(@NonNull View itemView) {
            super(itemView);
            binding = LayoutSavedItemesBinding.bind(itemView);
        }
    }
}

