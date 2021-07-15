package com.andy.smartparking;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {
    private FirebaseAuth mAuth;
//    firstname
//    surname
//    password
//    email
    private EditText fname;
    private EditText sname;
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private ProgressBar progressBar;
    AwesomeValidation awesomeValidation;


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

        //        add validation to surname
        awesomeValidation.addValidation(this, R.id.surname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.surname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.wrong_input);

//        add valdiation to firstname
        awesomeValidation.addValidation(this, R.id.firstname, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        awesomeValidation.addValidation(this,R.id.firstname,"^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$",R.string.wrong_input);


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

    }

    public void signup(View view) {
        String firstname = fname.toString().trim();
        String surname = sname.toString().trim();
        String psword = password.toString().trim();
        String emailaddress = email.toString().trim();
    }
}
