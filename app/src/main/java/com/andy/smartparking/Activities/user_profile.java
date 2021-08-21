package com.andy.smartparking.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.andy.smartparking.Model.User;
import com.andy.smartparking.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_profile extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();


        final TextView firstNameTextView1 = findViewById(R.id.firstnamep);
        final TextView surnameTextView1 = findViewById(R.id.surnamep);
        final TextView emailTextView1 = findViewById(R.id.email_addressp);

        reference.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if(userProfile != null){
                    String firstname = userProfile.firstName;
                    String surname = userProfile.surName;
                    String email = userProfile.email;

                    firstNameTextView1.setText(firstname);
                    surnameTextView1.setText(surname);
                    emailTextView1.setText(email);
                }else{
                    Toast.makeText(user_profile.this,"Where is user profile",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull  DatabaseError error) {
                Toast.makeText(user_profile.this,"An error occured",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(user_profile.this, Login.class));
    }

}