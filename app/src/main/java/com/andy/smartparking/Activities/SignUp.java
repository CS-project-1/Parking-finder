package com.andy.smartparking.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andy.smartparking.Model.User;
import com.andy.smartparking.Permissions.AppPermissions;
import com.andy.smartparking.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText fname;
    private EditText sname;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private ProgressBar progressBar;
    AwesomeValidation awesomeValidation;

    private Uri profileImageUri = null;
    private AppPermissions appPermissions;
    private StorageReference storageReference;
    private DatabaseReference userDetailsReference;
    private final static int GALLERY_REQ = 1;
    private ImageButton imageButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        fname = (EditText)findViewById(R.id.firstname);
        sname = (EditText)findViewById(R.id.surname);
        password = (EditText)findViewById(R.id.pswrd);
        confirmPassword = (EditText)findViewById(R.id.confirmpswrd);
        email = (EditText)findViewById(R.id.email_address);
        progressBar = (ProgressBar)findViewById(R.id.indeterminateBar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        appPermissions = new AppPermissions();

        storageReference = FirebaseStorage.getInstance().getReference();
        imageButton = findViewById(R.id.userProfilePicture);

        userDetailsReference = FirebaseDatabase.getInstance().getReference().child("Users");


        //        add validation to surname
        awesomeValidation.addValidation(this, R.id.surname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.surname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.invalid_name);

//        add valdiation to firstname
        awesomeValidation.addValidation(this, R.id.firstname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.firstname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.invalid_name);


//         add validation to email
        awesomeValidation.addValidation(this, R.id.email_address, RegexTemplate.NOT_EMPTY, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.email_address, Patterns.EMAIL_ADDRESS,R.string.invalid_email);


//        awesomeValidation.addValidation(this, R.id.etEmail, Patterns.EMAIL_ADDRESS, R.string.invalid_email);


//        add validation to password
        awesomeValidation.addValidation(this,R.id.pswrd,RegexTemplate.NOT_EMPTY,R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.pswrd, ".{6,}", R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.confirmpswrd, R.id.pswrd, R.string.invalid_confirm_password);



        mAuth = FirebaseAuth.getInstance();
    }

    public void LoginRedirect(View view) {
        startActivity(new Intent(SignUp.this, Login.class));
    }
    public void signup(View view) {
        String firstname1 = fname.getText().toString().trim();
        String surname2 = sname.getText().toString().trim();
        String psword = password.getText().toString().trim();
        String emailaddress1 = email.getText().toString().trim();

        if(awesomeValidation.validate()){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(emailaddress1,psword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                User user = new User(firstname1,surname2,emailaddress1);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(SignUp.this,"User has been registered succesfully",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }else {
                                            Toast.makeText(SignUp.this,"Failed to register",Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }else {
                                Toast.makeText(SignUp.this,"Something went wrong",Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }else{
            Toast.makeText(getApplicationContext(),"Form validation failed",Toast.LENGTH_SHORT).show();
        }
    }
}

