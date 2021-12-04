package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.property_dealer.databinding.ActivityLoginBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;


import java.util.concurrent.TimeUnit;


public class Login extends AppCompatActivity {
    ActivityLoginBinding binding;
    FirebaseAuth auth;
    ProgressDialog dialog;
//    CountryCodePicker ccp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("ارسال کود");




        auth = FirebaseAuth.getInstance();



        if (auth.getCurrentUser() != null){
            Intent intent = new Intent(Login.this,layout_activity.class);
            startActivity(intent);
            finishAffinity();
        }


        binding.btnCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = binding.txtnumber.getText().toString();
                if (phone.isEmpty()){
                    binding.txtnumber.requestFocus();
                    binding.txtnumber.setError("please insert the phone number");
                    return;
                }


                if (phone.length() == 12){
                    binding.txtnumber.requestFocus();
                    binding.txtnumber.setError("please insert complete phone number");
                    return;
                }



                if(binding.txtnumber.getText().charAt(0) == 0){
                    phone = phone.substring(1);
                    Toast.makeText(Login.this, phone, Toast.LENGTH_SHORT).show();
                }



                Intent intent = new Intent(Login.this,sign_up_activity.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);

            }
        });

    }
}