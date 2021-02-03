package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DiscoverFragment extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latlng;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Authenticate
        mAuth = FirebaseAuth.getInstance();


        setContentView(R.layout.fragment_discover);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String url = "https://createnet-c8488-default-rtdb.firebaseio.com/";
        FirebaseUser current_user =FirebaseAuth.getInstance().getCurrentUser();
        String uid = current_user.getUid();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance(url).getReference().child("Users").child(uid).child("location");
        ValueEventListener listener =databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // for(DataSnapshot snap:snapshot.getChildren()) {
                Double latitude = snapshot.child("latitude").getValue(Double.class);
                Double longitude = snapshot.child("longitude").getValue(Double.class);
                //Double latitude = snap.child("latitude").getValue(Double.class);
                //Double longitude = snap.child("longitude").getValue(Double.class);

                LatLng location = new LatLng(latitude, longitude);

                mMap.addMarker(new MarkerOptions().position(location).title("User"));
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 5));

                //MarkerOptions marker = new MarkerOptions().position(location).title(/*snap.child("name").getValue().toString()*/"test");
                //marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_markers));
                //mMap.addMarker(marker);
                // }
               /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Intent i=new Intent(RetrieveMapActivity.this,DetailsActivity.class);
                      startActivity(i);
                        return false;
                    }
                });*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
