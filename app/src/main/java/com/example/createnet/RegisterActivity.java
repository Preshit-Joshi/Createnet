package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText mDisplayName;
    private EditText mEmail;
    private EditText mPassword;
    private  Button mCreateBtn ;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    //Location get
    FusedLocationProviderClient fusedLocationProviderClient;
    private String select_category;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //
        final Spinner dropdown = findViewById(R.id.spinner1);

        //create a list of items for the spinner.
        String[] items = new String[]{"Acting","Comedy","Design","Literature","Music","Painting","Photography","Other"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(RegisterActivity.this);
        //

        //Location get
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //
        mAuth = FirebaseAuth.getInstance();
        mDisplayName=(EditText)findViewById(R.id.reg_display_name);
        mEmail=(EditText)findViewById(R.id.reg_email);
        mPassword=(EditText)findViewById(R.id.reg_password);
        //Location Get

        //ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        //
        mCreateBtn= (Button)findViewById(R.id.reg_create_btn);
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String display_name = mDisplayName.getText().toString();
            String email = mEmail.getText().toString();
            String password = mPassword.getText().toString();



            register_user(email, password);

           //Location get
            if (ActivityCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


                getLocation();


           } else {
                ActivityCompat.requestPermissions(RegisterActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

           }






            //

        }

        });




    }
    private void register_user(String email, String password) {
    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){

                FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();

                String uid = current_user.getUid();
                String url ="https://createnet-c8488-default-rtdb.firebaseio.com/";
                mDatabase = FirebaseDatabase.getInstance(url).getReference().child("Users").child(uid);



                HashMap<String,String> userMap = new HashMap<>();
                userMap.put("name",mDisplayName.getText().toString());
                userMap.put("status","Hi there,I'm using CreateNet");
                userMap.put("image","default");
                userMap.put("thumb_image","default");
                userMap.put("category",select_category);
                mDatabase.setValue(userMap);











            }else{

                //Toast.makeText(RegisterActivity.this,"You got some error",Toast.LENGTH_LONG).show();
            }



        }
    });


    }

    //Location Get

    @SuppressLint("MissingPermission")
    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();

                        String uid = current_user.getUid();
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(RegisterActivity.this, Locale.getDefault());
                        //initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        LocationHelper helper = new LocationHelper(location.getLongitude(), location.getLatitude());
                        String url = "https://createnet-c8488-default-rtdb.firebaseio.com/";
                        //    FirebaseDatabase.getInstance(url).getReference("Get Location").setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>()

                        //mStoreLocDb=FirebaseDatabase.getInstance(url).getReference().child("Users");
                        //mStoreLocDb.child("Get Location").setValue(helper);
                        // for(int i=0;i<5;i++) {
                        FirebaseDatabase.getInstance(url).getReference().child("Users").child(uid).child("location").setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Location Saved", Toast.LENGTH_SHORT).show();

                                    Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(mainIntent);
                                    finish();

                                } else {
                                    Toast.makeText(RegisterActivity.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        // }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
        //}

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        select_category= adapterView.getSelectedItem().toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //






}