package com.andy.smartparking.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.andy.smartparking.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileActivity extends AppCompatActivity {

    private ImageButton imageButton;
    private Button doneBtn;
    private FirebaseAuth mAuth;
    private DatabaseReference
            mDatabaseUser;
    private StorageReference mStorageRef;
    private Uri profileImageUri = null;
    private final static int GALLERY_REQ = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        imageButton = findViewById(R.id.imagebutton);
        doneBtn = findViewById(R.id.doneBtn);

        mAuth = FirebaseAuth.getInstance();
        final String userID = mAuth.getCurrentUser().getUid();

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("profile_images");
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQ);
            }
        });
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( profileImageUri != null){
                    StorageReference profileImagePath = mStorageRef.child("profile_images").child(profileImageUri.getLastPathSegment());
                    profileImagePath.putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(taskSnapshot.getMetadata()!=null){
                                if(taskSnapshot.getMetadata().getReference()!=null){
                                    Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                    result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            final  String profileImage = uri.toString();
                                            mDatabaseUser.push();
                                            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    mDatabaseUser.child("profilePhoto").setValue(profileImage).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(ProfileActivity.this,"Profile Updated",Toast.LENGTH_SHORT).show();
                                                                Intent login = new Intent(ProfileActivity.this, MainActivity.class);
                                                                startActivity(login);
                                                            }
                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        }
                    });


                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == GALLERY_REQ && resultCode == RESULT_OK){
            profileImageUri = data.getData();
            imageButton.setImageURI(profileImageUri);
        }
    }


}

