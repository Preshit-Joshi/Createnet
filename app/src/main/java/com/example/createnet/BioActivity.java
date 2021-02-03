package com.example.createnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BioActivity extends AppCompatActivity {


    private EditText mStatus;
    private Button mSavebtn;
//Firebase
    private DatabaseReference mStatusDatabase;
    private FirebaseUser mCurrentUser;


//Progress
private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        String url ="https://createnet-c8488-default-rtdb.firebaseio.com/";
        mCurrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mCurrentUser.getUid();
        mStatusDatabase = FirebaseDatabase.getInstance(url).getReference().child("Users").child(current_uid);


        String status_value = getIntent().getStringExtra("status_value");


         mStatus = (EditText)findViewById(R.id.bio_input);
         mSavebtn=(Button)findViewById(R.id.bio_input_btn);

         mStatus.setText(status_value);

         mSavebtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 //Progress
                 mProgress=new ProgressDialog(BioActivity.this);
                 mProgress.setTitle("Saving Changes");
                 mProgress.setMessage("Please wait while we save changes");
                 mProgress.show();

                 String bio = mStatus.getEditableText().toString();

                 mStatusDatabase.child("status").setValue(bio).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if(task.isSuccessful()){
                             mProgress.dismiss();
                         }else{

                             Toast.makeText(BioActivity.this,"There was some error in saving changes.",Toast.LENGTH_LONG).show();
                         }
                     }
                 });


             }
         });

    }
}