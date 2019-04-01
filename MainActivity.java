package com.example.childsecurity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    TextView t1;
    String check1=null;
    EditText logname,lpass;
    Button login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logname=(EditText)findViewById(R.id.editText);
        lpass=(EditText)findViewById(R.id.editText2);
        login=(Button)findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        lpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lname=logname.getText().toString();
                check1=String.valueOf(isValidMail(lname));
                if(lname.isEmpty()){
                    logname.setError("user name is empty");
                }
                else if(check1.equals("false")){
                    logname.setError("Invalid format");
                }
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lpword= lpass.getText().toString();
                if(lpword.isEmpty())
                {
                    lpass.setError("Password empty");
                }
                else {
                    final String email = logname.getText().toString();
                    String password = lpass.getText().toString();

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        //Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        // updateUI(user);
                                        SaveSharedPreference saveSharedPreference=new SaveSharedPreference();
                                        saveSharedPreference.setUserName(getApplicationContext(),email);
                                        Intent intent = new Intent(MainActivity.this, Homepage.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(getApplicationContext(),"mail id or password incorrect",Toast.LENGTH_LONG);
                                        // If sign in fails, display a message to the user.
                                        // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        // Toast.makeText(EmailPasswordActivity.this, "Authentication failed.",
                                        //      Toast.LENGTH_SHORT).show();
                                        // updateUI(null);
                                    }

                                    // ...
                                }
                            });


                }
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference("user");
                DatabaseReference myred=database.getReference("pass");
// myRef.setValue("Hello, World!");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);
                       // myRef.setValue("anandhu");
                     //   Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();

                        Log.d("buggie", "Value is: " + value);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.d("buggie", "Failed to read value.", error.toException());
                    }
                });
            }
        });

        t1=(TextView)findViewById(R.id.textView5);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
