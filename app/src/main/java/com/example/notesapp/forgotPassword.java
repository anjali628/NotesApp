package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {

    private EditText mforgotPassword;
    private Button mpasswordrecoverbutton;
    private TextView mgobacktologin;


    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getSupportActionBar().hide();

        mforgotPassword=findViewById(R.id.forgotPassword);
        mpasswordrecoverbutton=findViewById(R.id.passwordRecoverButton);
        mgobacktologin=findViewById(R.id.goBackToLoginScreen);

        firebaseAuth=FirebaseAuth.getInstance();


        mgobacktologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(forgotPassword.this,MainActivity.class);
                startActivity(intent);
            }
        });


        mpasswordrecoverbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=mforgotPassword.getText().toString().trim();
                if(mail.isEmpty()){
                    Toast.makeText(getApplicationContext(),"First Enter Your Email",Toast.LENGTH_SHORT).show();

                }
                else
                {
                    //we have to send password recover email

                    firebaseAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Mail Sent, You Can Recover Your Password Using Mail",Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(forgotPassword.this,MainActivity.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Email Entered Is Wrong or Your Account Not Exists",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }





            }
        });


    }
}