package com.andy.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView register;
    private EditText email;
    private EditText pswrd;
    private TextView forgotpswrd;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = (EditText) findViewById(R.id.email_address);
        pswrd = (EditText) findViewById(R.id.pswrdL);
        register = (TextView)findViewById(R.id.signInTextView);
        forgotpswrd = (TextView)findViewById(R.id.forgotPassword);
        progressBar = (ProgressBar)findViewById(R.id.indeterminateBar);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //         add validation to email
        awesomeValidation.addValidation(this, R.id.email_address, RegexTemplate.NOT_EMPTY, R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.email_address, Patterns.EMAIL_ADDRESS,R.string.invalid_email);

        //        add validation to password
        awesomeValidation.addValidation(this,R.id.pswrdL,RegexTemplate.NOT_EMPTY,R.string.invalid_password);
        awesomeValidation.addValidation(this, R.id.pswrdL, ".{6,}", R.string.invalid_password);

        mAuth = FirebaseAuth.getInstance();

    }

    public void RedirectSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

    public void ResetPassword(View view) {
        startActivity(new Intent(this,ResetPassword.class));

    }

    public void login(View view) {
        String email1 = email.getText().toString().trim();
        String password1 = pswrd.getText().toString().trim();

        if(awesomeValidation.validate()){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                            //redirect to user profile
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()){
                            startActivity(new Intent(Login.this,user_profile.class));
                        }else{
                            user.sendEmailVerification();
                            Toast.makeText(Login.this,"Check your email to verify your accoiunt",Toast.LENGTH_LONG).show();
                        }

                    }else{
                        Toast.makeText(Login.this,"Failed to register",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            });

        }else{
            Toast.makeText(Login.this,"Failed to register",Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.GONE);
        }

    }
}
