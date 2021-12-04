package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.property_dealer.classess.cls_user;
import com.example.property_dealer.databinding.ActivityUserInfoBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class user_info extends AppCompatActivity {
    ActivityUserInfoBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserInfoBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        dialog = new ProgressDialog(user_info.this);
        dialog.setTitle("لطفن انطزار بکشد");

        binding.userimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "عکس خودرا انتخاب نماید"), 45);
            }
        });

        final String CountriesNames[] =
                {"کابل", "هرات", "بامیان", "مزار", "پنچشیر", "پروان"};
        ArrayAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, CountriesNames);
        binding.spinnercountry.setAdapter(myAdapter);


        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                String name = binding.txtname.getText().toString();
                String loc = binding.spinnercountry.getSelectedItem().toString();
                if (name.isEmpty()){
                    binding.txtname.setError("please fill the name");
                    binding.txtname.requestFocus();
                    dialog.dismiss();
                    return;
                }
                if (loc.isEmpty()){
                    binding.spinnercountry.getSelectedItem().toString();
                    Toast.makeText(user_info.this, "Please select the location first", Toast.LENGTH_SHORT).show();
                    binding.spinnercountry.requestFocus();
                    dialog.dismiss();
                    return;}

                if (selectedImage != null){
                    StorageReference reference = storage.getReference().child("userimg").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageuri = uri.toString();
                                        String uid = auth.getUid();
                                        String phone = auth.getCurrentUser().getPhoneNumber();

                                        cls_user user = new cls_user(uid,name,loc,phone,imageuri);
                                        database.getReference()
                                                .child("Users")
                                                .child(uid)
                                                .setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(user_info.this,layout_activity.class);
                                                        startActivity(intent);
                                                        finishAffinity();
                                                    }
                                                });
                                    }
                                });

                            }
                        }
                    });

                }
                else{
                    String uid = auth.getUid();
                    String phone = auth.getCurrentUser().getPhoneNumber();
                    cls_user user = new cls_user(uid,name,loc,phone,"no Image");
                    database.getReference()
                            .child("Users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(user_info.this,layout_activity.class);
                                    startActivity(intent);
                                    finishAffinity();

                                }
                            });

                }


            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            binding.userimage.setImageURI(data.getData());
            selectedImage = data.getData();
        }

    }
}