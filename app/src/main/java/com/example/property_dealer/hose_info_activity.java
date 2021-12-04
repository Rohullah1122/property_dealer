package com.example.property_dealer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.property_dealer.classess.cls_houseimg;
import com.example.property_dealer.classess.cls_houseinfo;
import com.example.property_dealer.classess.cls_user;
import com.example.property_dealer.databinding.ActivityHoseInfoActivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class hose_info_activity extends AppCompatActivity {

    ActivityHoseInfoActivityBinding binding;
    ArrayList<Image> ALI;
    Uri selectedImage;
    ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private Uri ImageUri;
    private ProgressDialog progressDialog;
    private int upload_count = 0;
    FirebaseAuth auth;
    FirebaseStorage storage;
    FirebaseDatabase database;
    List<String> file, statuse;
    ProgressDialog dialog;
    cls_user user;
    long hid = 0;
    ArrayList<Uri> ALP = new ArrayList<Uri>();
    private Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHoseInfoActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(hose_info_activity.this);
        dialog.setCancelable(false);
        dialog.setMessage("انداختن خانه");
        String uid = auth.getUid();


        final String CountriesNames[] =
                {"کابل", "هرات", "بامیان", "مزار", "پنچشیر", "پروان"};
        ArrayAdapter myAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, CountriesNames);
        binding.spinnerhouse.setAdapter(myAdapter);


        binding.btnmoreimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);

            }
        });


        binding.houseprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);


            }
        });

        database.getReference().child("Users").child(auth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(cls_user.class);
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





        String roomtype[] =
                {"کیرایی","فروشی"};
        ArrayAdapter typeadabter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, roomtype);
        binding.spinnersaleOrrent.setAdapter(typeadabter);





        database.getReference().child("HouseInfo").child("houses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    hid = (snapshot.getChildrenCount());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rent = binding.houseprice.getText().toString();
                String locationcorrect = binding.txtlocation.getText().toString();
                String loc = binding.spinnerhouse.getSelectedItem().toString();
                String rno = binding.numberroom.getText().toString();
                String spinersale = binding.spinnersaleOrrent.getSelectedItem().toString();
                String kichen = binding.numberkitchen.getText().toString();
                String washroom = binding.numberwashroo.getText().toString();
                String housewidth = binding.widthhome.getText().toString();
                String househieght = binding.homesize.getText().toString();
                dialog.show();

                if (locationcorrect.isEmpty()) {
                    binding.txtlocation.setError("please fill the Location");
                    binding.txtlocation.requestFocus();
                    return;
                }
                if (loc.isEmpty()) {
                    binding.spinnerhouse.getSelectedItem().toString();
                    Toast.makeText(hose_info_activity.this, "Please select the location first", Toast.LENGTH_SHORT).show();
                    binding.spinnerhouse.requestFocus();
                    return;
                }

                if (rent.isEmpty()) {
                    binding.houseprice.setError("please fill the Rent");
                    binding.houseprice.requestFocus();
                    return;
                }

                if (rno.isEmpty()) {
                    binding.numberroom.setError("please fill the Rent");
                    binding.numberroom.requestFocus();
                    return;
                }

                if (spinersale.isEmpty()) {
                    Toast.makeText(hose_info_activity.this, "Please fill the location spinner", Toast.LENGTH_SHORT).show();
                    binding.spinnerhouse.requestFocus();
                    return;
                }

                if (kichen.isEmpty()) {
                    binding.numberkitchen.setError("");
                    binding.numberkitchen.requestFocus();
                    return;
                }

                if (washroom.isEmpty()) {
                    binding.numberwashroo.setError("");
                    binding.numberwashroo.requestFocus();
                    return;
                }


                if (househieght.isEmpty()) {
                    binding.homesize.setError("");
                    binding.homesize.requestFocus();
                    return;
                }


                if (housewidth.isEmpty()) {
                    binding.widthhome.setError("");
                    binding.widthhome.requestFocus();
                    return;
                }

                Date date = new Date();
                StorageReference reference = storage.getReference().child("houseimge").child(date.getTime() +"");
                reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageuri = uri.toString();
                                    cls_houseinfo cls_houseinfo = new cls_houseinfo();
                                    cls_houseinfo.setHid(hid +1 +"");
                                    cls_houseinfo.setUsername(user.name);
                                    cls_houseinfo.setUserid(uid);
                                    cls_houseinfo.setUserprofile(user.image);
                                    cls_houseinfo.setHouseloc(loc);
                                    cls_houseinfo.setHouserent(rent);
                                    cls_houseinfo.setNumberofroom(rno);
                                    cls_houseinfo.setType(spinersale);
                                    cls_houseinfo.setHouseexactlocation(locationcorrect);
                                    cls_houseinfo.setHouseimg(imageuri);
                                    cls_houseinfo.setPhone(user.phone);
                                    cls_houseinfo.setHousewidth(housewidth);
                                    cls_houseinfo.setHousehight(househieght);
                                    cls_houseinfo.setNumberkitchen(kichen);
                                    cls_houseinfo.setNumberwashroom(washroom);
                                    database.getReference()
                                            .child("HouseInfo")
                                            .child("houses")
                                            .push()
                                            .setValue(cls_houseinfo)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Intent intent = new Intent(hose_info_activity.this,layout_activity.class);
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                }
                                            });
                                }
                            });




                        }
                    }
                });




            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == 1){
            selectedImage = data.getData();
            binding.houseprofile.setImageURI(selectedImage);
        }


        String rent = binding.houseprice.getText().toString();
        String locationcorrect = binding.txtlocation.getText().toString();
        String loc = binding.spinnerhouse.getSelectedItem().toString();
        String rno = binding.numberroom.getText().toString();
        String spinersale = binding.spinnersaleOrrent.getSelectedItem().toString();


        if (locationcorrect.isEmpty()) {
            binding.txtlocation.setError("please fill the Location");
            binding.txtlocation.requestFocus();
            return;
        }
        if (loc.isEmpty()) {
            binding.spinnerhouse.getSelectedItem().toString();
            Toast.makeText(hose_info_activity.this, "Please select the location first", Toast.LENGTH_SHORT).show();
            binding.spinnerhouse.requestFocus();
            return;
        }

        if (rent.isEmpty()) {
            binding.houseprice.setError("please fill the Rent");
            binding.houseprice.requestFocus();
            return;
        }

        if (rno.isEmpty()) {
            binding.numberroom.setError("please fill the Rent");
            binding.numberroom.requestFocus();
            return;
        }

        if (spinersale.isEmpty()) {
            Toast.makeText(hose_info_activity.this, "Please select the TYPE first", Toast.LENGTH_SHORT).show();
            binding.spinnersaleOrrent.requestFocus();
            return;
        }

        if(requestCode == 2){
            if(data.getClipData() !=null  && rent != ""){
                int countClipData = data.getClipData().getItemCount();
                int currentImageSelect  = 0;
                while(currentImageSelect < countClipData){
                    ImageUri = data.getClipData().getItemAt(currentImageSelect).getUri();
                    ImageList.add(ImageUri);
                    currentImageSelect = currentImageSelect +1;
                }

                    Toast.makeText(this, "You have Select" + ImageList.size() + "Images", Toast.LENGTH_SHORT).show();
                    StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
                    for(upload_count = 0; upload_count < ImageList.size(); upload_count++){
                        Uri IndividualImage = ImageList.get(upload_count);
                        final StorageReference ImageName = ImageFolder.child("Image" + IndividualImage.getLastPathSegment());
                        ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String url = String.valueOf(uri);
                                        cls_houseimg houseimg = new cls_houseimg(hid +1 +"",user.getUserID(),user.getImage(),url);
                                        database = FirebaseDatabase.getInstance();
                                        database.getReference().child("houseimg").child("images").push().setValue(houseimg);

                                    }
                                });
                            }
                        });

                }


            }
        } else {
            Toast.makeText(this,"لطفن جندین عکس انتخاب نماید یا هم تکمیل معلومات را",Toast.LENGTH_LONG).show();

        }
    }

}
