package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText mloginemail,mloginpassword;
    private RelativeLayout mgotosignup,mlogin;
    private TextView mgotoforgotpassword;




    private FirebaseAuth firebaseAuth;


    ProgressBar mprogressBarofmainactivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().hide();

        mloginemail=findViewById(R.id.loginEmail);
        mloginpassword=findViewById(R.id.loginPassword);
        mlogin=findViewById(R.id.login);
        mgotoforgotpassword=findViewById(R.id.gotoforgotpssword);
        mgotosignup=findViewById(R.id.gotosignup);
        mprogressBarofmainactivity=findViewById(R.id.progressbarofmainactivity);



        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();


        if(firebaseUser!=null){
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));
        }



        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,signup.class));
            }
        });



        mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,forgotPassword.class));
            }
        });


        mlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=mloginemail.getText().toString().trim();
                String password=mloginpassword.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Fields Are Required",Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //login the user

                    mprogressBarofmainactivity.setVisibility(View.VISIBLE);


                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                checkmailverification();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                                mprogressBarofmainactivity.setVisibility(View.INVISIBLE);
                            }

                        }
                    });
                }
            }
        });

    }

    private  void checkmailverification(){
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,notesActivity.class));

        }
        else {
            mprogressBarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify Your Mail First",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}