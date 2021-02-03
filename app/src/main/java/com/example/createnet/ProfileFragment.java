package com.example.createnet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.w3c.dom.Text;

import static android.content.ContentValues.TAG;


public class ProfileFragment extends Fragment {

    private static final int RESULT_OK = 1 ;
    private static final int GALLERY_PICK = 1 ;
    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;

    //Android Layout
    private ImageView mDisplayImage;
    private TextView mName;
    private TextView mStatus;
    private Button mStatusBtn;
    private Button mImageBtn;
    private TextView mCategory;
    private ProgressDialog mProgressDialog;




    //Storage reference(Firebase)
    private StorageReference mImageStorage;

    Activity activity=getActivity();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        String url ="https://createnet-c8488-default-rtdb.firebaseio.com/";
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        mDisplayImage=(ImageView)root.findViewById(R.id.profile_image);
        mName=(TextView)root.findViewById(R.id.profile_name);
        mStatus=(TextView)root.findViewById(R.id.profile_bio);
        mStatusBtn = (Button)root.findViewById(R.id.profile_bio_btn);
        mImageBtn = (Button)root.findViewById(R.id.profile_image_btn);
        mCategory=(TextView)root.findViewById(R.id.profile_category);

        String imgurl = "gs://createnet-c8488.appspot.com";
        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser=FirebaseAuth.getInstance().getCurrentUser();

        String current_uid=mCurrentUser.getUid();
        mUserDatabase=FirebaseDatabase.getInstance(url).getReference().child("Users").child(current_uid);

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

                if (!image.contains("default"))
                {Picasso.get().load(image).into(mDisplayImage);}

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String status_value =mStatus.getText().toString();

                Intent status_intent = new Intent(getContext(),BioActivity.class);
                status_intent.putExtra("status_value",status_value);
                startActivity(status_intent);
            }
        });

        mImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Toast.makeText(getActivity(),"Working!!!!!!!",Toast.LENGTH_SHORT).show();
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent,"SELECT IMAGE"),GALLERY_PICK);

             /* CropImage.activity()
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setAspectRatio(1,1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(getActivity());
                    */
            }
        });


        return root;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int i=0;
        int j=0;
        int k=0;

        Toast.makeText(getActivity(), "Working!!!!!!!", Toast.LENGTH_SHORT).show();
        if (i == 0) {
            i++;

            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setTitle("Uploading Image");
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
            Uri imageUri = data.getData();

            Toast.makeText(getActivity(), "Firebase Started", Toast.LENGTH_SHORT).show();
            final StorageReference filepath = mImageStorage.child("profile_images").child(mCurrentUser.getUid());

            filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d(TAG, "onSuccess: uri= " + uri.toString());
                            String profile_get_url = uri.toString();
                            mUserDatabase.child("image").setValue(uri.toString());
                            Toast.makeText(getActivity(), "Image Url Done", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });
                }
            });


        }




        }
    }
