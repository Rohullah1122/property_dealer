package com.example.property_dealer.adabters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.property_dealer.R;
import com.example.property_dealer.classess.cls_houseimg;
import com.example.property_dealer.databinding.LayoutImgeBinding;

import java.util.ArrayList;

public class adabter_imges extends RecyclerView.Adapter<adabter_imges.imgesviewholder> {
    Context context;
    ArrayList<cls_houseimg> ALHI;


    public adabter_imges(Context context, ArrayList<cls_houseimg> ALHI) {
        this.context = context;
        this.ALHI = ALHI;
    }

    @NonNull
    @Override
    public imgesviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_imge,parent,false);
        return new imgesviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull imgesviewholder holder, int position) {
        cls_houseimg houseimgs = ALHI.get(position);
        Glide.with(context).load(houseimgs.getHouseimg()).into(holder.binding.imghouses);
    }



    @Override
    public int getItemCount() {
        return ALHI.size();
    }

    public class imgesviewholder extends RecyclerView.ViewHolder {
        LayoutImgeBinding binding;
        public imgesviewholder(@NonNull View itemView) {
            super(itemView);
            binding =LayoutImgeBinding.bind(itemView);
        }
    }


}
