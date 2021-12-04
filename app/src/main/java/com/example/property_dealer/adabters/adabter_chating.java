package com.example.property_dealer.adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_houseinfo;
import com.example.property_dealer.classess.clss_messages;
import com.example.property_dealer.databinding.ChattingLayoutBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class adabter_chating extends RecyclerView.Adapter<adabter_chating.chatting>{
    Context context;
    ArrayList<clss_messages> ALM;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String rvid;
    String senderid;

    public adabter_chating(Context context, ArrayList<clss_messages> ALM, String rvid, String senderid) {
        this.context = context;
        this.ALM = ALM;
        this.rvid = rvid;
        this.senderid = senderid;

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    @NonNull
    @Override
    public chatting onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chatting_layout,parent,false);
        return new chatting(view);
    }

    @Override
    public void onBindViewHolder(@NonNull chatting holder, int position) {
        clss_messages msgs = ALM.get(position);
        if (msgs.getMessages().isEmpty()){

            holder.binding.sendertv.setVisibility(View.GONE);
            holder.binding.rvtv.setVisibility(View.GONE);
        }else{
            if (auth.getUid() == senderid){


            }


        }


    }

    @Override
    public int getItemCount() {
        return ALM.size();
    }

    public class chatting extends RecyclerView.ViewHolder {
        ChattingLayoutBinding binding;
        public chatting(@NonNull View itemView) {
            super(itemView);
            binding = ChattingLayoutBinding.bind(itemView);

        }
    }
}
