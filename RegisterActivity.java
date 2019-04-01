package com.example.childsecurity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
EditText  name,phone,mail,engno,model,password,cpass;
Pattern p;

    String check1=null;
    String check=null;
Matcher m;
Button register;
    private FirebaseAuth mAuth;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
     //   updateUI(currentUser);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.editText3);
        mail=(EditText)findViewById(R.id.editText4);
        phone=(EditText)findViewById(R.id.editText5);
        model=(EditText)findViewById(R.id.editText6);
        engno=(EditText)findViewById(R.id.editText7);
        password=(EditText)findViewById(R.id.editText8);
        cpass=(EditText)findViewById(R.id.editText9);
        register=(Button)findViewById(R.id.button2);
        mAuth = FirebaseAuth.getInstance();

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rname=name.getText().toString();

                if(rname.isEmpty()){
                    name.setError("name is empty");
                }
            }
        });


        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mail.getText().toString();
                check1=String.valueOf(isValidMail(email));
                if(email.isEmpty()){
                    mail.setError("email is empty");
                }
                else if(check1.equals("false")){
                    mail.setError("Invalid format");
                }

            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph=phone.getText().toString();
                check=String.valueOf(isValidMobile(ph));
                if(ph.length()!=10)
                    phone.setError("check your number");
                else if(ph.isEmpty()){
                    phone.setError("phone number is empty");

                }
                else if(check.equals("false")){
                    phone.setError("Invalid format");
                }
            }
        });



        engno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mod=model.getText().toString();
                if(mod.isEmpty()){
                    model.setError("model is empty");
                }
            }
        });



        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String engine=engno.getText().toString();
                if(engine.isEmpty()){
                    engno.setError("engine no is empty");
                }

            }
        });

        cpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=password.getText().toString();
                if(pass.isEmpty()){
                    password.setError("password is empty");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correct=cpass.getText().toString();
                String pass=password.getText().toString();
                String engine=engno.getText().toString();
                String mod=model.getText().toString();
                String ph=phone.getText().toString();
                String email=mail.getText().toString();
                String rname=name.getText().toString();
                boolean rephone=isValidMobile(ph);
                boolean remeail=isValidMail(email);
                if(rname.isEmpty()){
                    name.setError("Name is empty");
                }else if(email.isEmpty()){

                    mail.setError("E mail is empty");
                }else if(remeail==false){
                    mail.setError("InValid Email Format");
                } else if(ph.isEmpty()){
                    phone.setError("phone is empty");
                }else if(ph.length()!=10){

                    phone.setError("Invalid Phone Number");
                }



                else if(rephone==false){
                    phone.setError("Invalid Phone Number");
                }else if(mod.isEmpty()){
                    model.setError("mODEL IS EMPTY");
                }else if(engine.isEmpty()){
                    engno.setError("Engine number is empty");
                }else if(pass.isEmpty()){
                    password.setError("password is empty");
                }else if(pass.length()<8){
                    password.setError("Password Length should be atleast 8");
                }else if(!correct.equals(pass)){
                    cpass.setError("Password and confirm password should be equal");
                }else {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String checkname=user.getEmail();
                    if(checkname.equals(email)){
                        Toast.makeText(RegisterActivity.this, "Email Already Exist Choose Another one" , Toast.LENGTH_SHORT).show();

                    }
                else {
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            //  Log.d(TAG, "createUserWithEmail:success");

                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            //updateUI(user);
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                            //    updateUI(null);
                                        }


                                    }
                                });


                    }

}}});}

    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }}
