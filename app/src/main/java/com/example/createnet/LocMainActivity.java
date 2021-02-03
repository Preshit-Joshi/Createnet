/*package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocMainActivity extends AppCompatActivity {

    //initialize variables
    Button btLocation;
    private DatabaseReference mStoreLocDb;
    TextView textView1, textView2, textView3, textView4, textView5;
    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btLocation = findViewById(R.id.bt_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(LocMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(LocMainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });
    }

    private void getLocation() {

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        //initialize geocoder
                        Geocoder geocoder = new Geocoder(LocMainActivity.this, Locale.getDefault());
                        //initialize address list
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                        LocationHelper helper = new LocationHelper(location.getLongitude(), location.getLatitude());
                        String url = "https://createnet-c8488-default-rtdb.firebaseio.com/";
                        //    FirebaseDatabase.getInstance(url).getReference("Get Location").setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>()

                        //mStoreLocDb=FirebaseDatabase.getInstance(url).getReference().child("Users");
                        //mStoreLocDb.child("Get Location").setValue(helper);
                       // for(int i=0;i<5;i++) {
                            FirebaseDatabase.getInstance(url).getReference().child("Users").child.child().setValue(helper).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LocMainActivity.this, "Location Saved", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LocMainActivity.this, "Location Not Saved", Toast.LENGTH_SHORT).show();
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

    public void btnRetrieveLocation(View view) {

        startActivity(new Intent(getApplicationContext(),RetrieveMapActivity.class));

    }
}
*/