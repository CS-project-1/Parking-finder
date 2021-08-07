package com.andy.smartparking.Activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andy.smartparking.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {
    private EditText emailAddress;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        emailAddress = findViewById(R.id.editTextTextEmailAddress);
        progressBar =findViewById(R.id.indeterminateBar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //         add validation to email
        awesomeValidation.addValidation(this, R.id.email_address, RegexTemplate.NOT_EMPTY, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.email_address, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        mAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(View view) {
        String email = emailAddress.getText().toString().trim();
        if(awesomeValidation.validate()){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(ResetPassword.this,"Try again something went wrong",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }else{
            Toast.makeText(ResetPassword.this,"Try again something went wrong",Toast.LENGTH_LONG).show();
        }
    }
}