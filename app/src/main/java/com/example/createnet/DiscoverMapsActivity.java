package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
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

public class DiscoverMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng latlng;
    private FirebaseAuth mAuth;
    private FirebaseUser mAuthSelf;
    String selfuid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Intent iin =getIntent();


        //Authenticate
        mAuth = FirebaseAuth.getInstance();
        mAuthSelf =FirebaseAuth.getInstance().getCurrentUser();

         selfuid=mAuthSelf.getUid();

        setContentView(R.layout.activity_discover_maps);
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
        final String category = getIntent().getStringExtra("Category");
        //Toast.makeText(DiscoverMapsActivity.this,category,Toast.LENGTH_SHORT).show();




        String url = "https://createnet-c8488-default-rtdb.firebaseio.com/";
        DatabaseReference databaseReference= FirebaseDatabase.getInstance(url).getReference().child("Users");
        ValueEventListener listener=databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snap:snapshot.getChildren()) {
                    /*Double latitude = snapshot.child("latitude").getValue(Double.class);
                    Double longitude = snapshot.child("longitude").getValue(Double.class);*/
                    String key = snap.getKey().toString();

                   // Toast.makeText(DiscoverMapsActivity.this, key, Toast.LENGTH_LONG).show();
                    Double latitude = snap.child("location").child("latitude").getValue(Double.class);
                    Double longitude = snap.child("location").child("longitude").getValue(Double.class);


                    if (latitude != null && longitude != null) {

                        LatLng location = new LatLng(latitude, longitude);

                /*mMap.addMarker(new MarkerOptions().position(location).title("User"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,14F));*/
                        if (key.contains(selfuid)){
                            Double latitudeself = snap.child("location").child("latitude").getValue(Double.class);
                            Double longitudeself = snap.child("location").child("longitude").getValue(Double.class);
                            LatLng locationself = new LatLng(latitudeself, longitudeself);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationself, 15));
                            Circle circle=mMap.addCircle(new CircleOptions().center(locationself).radius(5).fillColor(Color.BLUE));

                            //mMap.animateCamera( CameraUpdateFactory.zoomTo( 2.0f ) );
                        }

                        // MarkerOptions marker = new MarkerOptions().position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString());

                        //Marker marker2=mMap.addMarker(new MarkerOptions().position(location).visible(false));
                        //marker2.setTitle(key);


                        //marker2.setPosition(location);
                        //Passing to hashmap
                        //takeuserid.put(marker, key);

                        //marker=mMap.addMarker(new MarkerOptions().position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                        //Added key

                        String choice = category;
                        if (snap.child("category").getValue().toString() != null) {
                            String categorydb = snap.child("category").getValue().toString();


                            if ((choice.equals(categorydb)) || (choice.contains("ALL"))) {

                                if (categorydb.equals("Acting")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                                else if (categorydb.equals("Comedy")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                                else if (categorydb.equals("Design")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                               else if (categorydb.equals("Literature")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                               else if (categorydb.equals("Music")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                                else if (categorydb.equals("Painting")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                                else if (categorydb.equals("Photography")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }
                                else if (categorydb.equals("Other")) {
                                    Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("category").getValue().toString()));
                                    marker.setTag(key);

                                }


                            }
                        }
                 /*   if(choice.equals(category1)) {

                        Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                        marker.setTag(key);

                    }
                    else if(choice.equals(category1)) {
                        Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                        marker.setTag(key);
                    }
                    else if(choice.equals("All")) {
                        String category = snap.child("category").getValue().toString();
                        switch (category) {
                            case "Dancer":
                                Marker marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                                marker.setTag(key);
                                break;
                            case "Singer":
                                marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                                marker.setTag(key);
                                break;
                            case "Artist":
                                marker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).position(location).title(snap.child("name").getValue().toString()).snippet(snap.child("bio").getValue().toString()));
                                marker.setTag(key);
                                break;
                        }
                    }*/
                    }
                }
                // marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
                //marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.my_markers));
                // mMap.addMarker(marker);

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        Intent i=new Intent(DiscoverMapsActivity.this,ViewPinProfile.class);
                        i.putExtra("User_id", marker.getTag().toString());

                        startActivity(i);
                        return false;
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}