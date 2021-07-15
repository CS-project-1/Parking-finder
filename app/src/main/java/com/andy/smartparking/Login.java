package com.andy.smartparking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class Login extends AppCompatActivity {

    private TextView register;
    private EditText email;
    private EditText pswrd;
    private TextView forgotpswrd;
    private ProgressBar progressBar;

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

    }

    public void RedirectSignUp(View view) {
        Intent intent = new Intent(getApplicationContext(),SignUp.class);
        startActivity(intent);
    }

    public void ResetPassword(View view) {
        Toast.makeText(getApplicationContext(),"Changing your password shortly",Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {

    }
}
