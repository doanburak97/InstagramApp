package com.doanburak.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    EditText et_email, et_password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null){
            Intent intentToMainPage = new Intent(this, MainPageActivity.class);
            startActivity(intentToMainPage);
            finish();
        }

    }

    public void SignIn(View view){

        String email = et_email.getText().toString();
        String password = et_password.getText().toString();

        if (!email.isEmpty() || !password.isEmpty()){

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intentToMainPage = new Intent(SignInActivity.this, MainPageActivity.class);
                        startActivity(intentToMainPage);
                    }else{
                        Toast.makeText(SignInActivity.this, "This account unknown!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }else{
            Toast.makeText(this, "Email or Password cannot be empty.", Toast.LENGTH_SHORT).show();
        }

    }

    public void SignUp(View view){

        Intent intentToSignUp = new Intent(this, SignUpActivity.class);
        startActivity(intentToSignUp);

    }
}