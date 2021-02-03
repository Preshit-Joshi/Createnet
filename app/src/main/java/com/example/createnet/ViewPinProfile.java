package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ViewPinProfile extends AppCompatActivity {
    private ImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private TextView mCategory;
    private DatabaseReference mUserDatabase;
    private StorageReference mImageStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userid = getIntent().getStringExtra("User_id");
        setContentView(R.layout.activity_view_pin_profile);

        String url ="https://createnet-c8488-default-rtdb.firebaseio.com/";

        mDisplayImage=(ImageView)findViewById(R.id.pin_profile_image);
        mName=(TextView)findViewById(R.id.pin_profile_name);
        mStatus=(TextView)findViewById(R.id.pin_profile_bio);
        mCategory=(TextView)findViewById(R.id.pin_profile_category);
        String imgurl = "gs://createnet-c8488.appspot.com";
        mImageStorage = FirebaseStorage.getInstance().getReference();

        String current_uid=userid;
        mUserDatabase= FirebaseDatabase.getInstance(url).getReference().child("Users").child(current_uid);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String bio =dataSnapshot.child("status").getValue().toString();
                String category=dataSnapshot.child("category").getValue().toString();
                String thumb_image = dataSnapshot.child("thumb_image").getValue().toString();
                mName.setText(name);
                mStatus.setText(bio);
                mCategory.setText(category);

                if (!image.contains("default") ) {
                    Picasso.get().load(image).into(mDisplayImage);
                }
               // Toast.makeText(ViewPinProfile.this,image,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
}
}