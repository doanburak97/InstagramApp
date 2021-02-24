package com.doanburak.instagramapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText et_email, et_password, et_verifyPassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String email, password, verifyPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_verifyPassword = findViewById(R.id.et_verifyPassword);

    }

    public void SignUp(View view){

        email = et_email.getText().toString();
        password = et_password.getText().toString();
        verifyPassword = et_verifyPassword.getText().toString();

        PasswordCheck(email, password, verifyPassword);
    }

    public void PasswordCheck(String email, String password, String verifyPassword){

        this.email = email;
        this.password = password;
        this.verifyPassword = verifyPassword;

        if (email.isEmpty() || password.isEmpty() || verifyPassword.isEmpty()){
            Toast.makeText(this, "Fill in the all blanks.", Toast.LENGTH_SHORT).show();
        }else{
            if (!password.matches(verifyPassword)){
                Toast.makeText(this, "Passwords aren't matching.", Toast.LENGTH_SHORT).show();
            }else{
                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(SignUpActivity.this, "Verification message has been sent.Check email.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this, "User didn't create.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }
}