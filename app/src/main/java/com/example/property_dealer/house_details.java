package com.example.property_dealer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.property_dealer.classess.cls_houseimg;
import com.example.property_dealer.databinding.ActivityHouseDetailsBinding;
import com.example.property_dealer.fragments.Buttomsheet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class house_details extends AppCompatActivity {
    ActivityHouseDetailsBinding binding;
    ArrayList<cls_houseimg> ALHI;
    FirebaseDatabase database;
    FirebaseAuth auth;
    int REQUEST_PHONE_CALL = 1;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHouseDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        String phone = null;
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ALHI = new ArrayList<>();



        String rent = getIntent().getStringExtra("rent");
        String loc = getIntent().getStringExtra("loc");
        String exactlocation = getIntent().getStringExtra("exactloc");
        String himge = getIntent().getStringExtra("houseimg");
        String owner = getIntent().getStringExtra("Woner");
        String userimge = getIntent().getStringExtra("userimg");
        String userid = getIntent().getStringExtra("uid");
        String userphone = getIntent().getStringExtra("phonenumber");
        String hid = getIntent().getStringExtra("hid");
        String  room = getIntent().getStringExtra("rooms");
        String type = getIntent().getStringExtra("txttype");
        String kitchen = getIntent().getStringExtra("kitchennumber");
        String washroom = getIntent().getStringExtra("washrooms");
        String housewidth = getIntent().getStringExtra("housewidth");
        String househight = getIntent().getStringExtra("househeigh");


        binding.txtrens.setText(rent);
        binding.txtloc.setText(exactlocation);
        binding.txtprovince.setText(loc);
        binding.txtuserinfo.setText(owner);
        binding.txtphonenumber.setText(userphone);
        binding.txtrooms.setText( room     +"اتاق");
        binding.txttype.setText("نویت"+ "_"+ type);
        binding.numberwashroo.setText(washroom +"حمام");
        binding.numberkitchen.setText(kitchen  +"آشپزخانه");
        binding.widthhome.setText(housewidth +"متر طول");
        binding.homesize.setText(househight +"متر آرز");
        Glide.with(house_details.this).load(himge)
                .into(binding.housimge);



        binding.btnimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent12 = new Intent(house_details.this,Activity_houses.class);
                intent12.putExtra("userid",userid);
                intent12.putExtra("hid",hid);
                startActivity(intent12);
            }
        });


        binding.btnContactowner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+userphone));
                if (ContextCompat.checkSelfPermission(house_details.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(house_details.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
                }
                else
                {
                    startActivity(callIntent);
                }

//                AlertDialog.Builder alert = new AlertDialog.Builder(house_details.this);
//                alert.setTitle("نوه ارتبات");
//                alert.setCancelable(false);
//                alert.setPositiveButton("تماس", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//
//                });
//
//                alert.setNegativeButton("بیام", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                    Intent intenmsg = new Intent(house_details.this,Message_activity.class);
//                    intenmsg.putExtra("userid",userid);
//                    intenmsg.putExtra("username",owner);
//                    startActivity(intenmsg);
//                    }
//                });
//
//                alert.show();







            }
        });

    }

    public void loadfragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.flhouse_details,fragment).commit();
    }


}