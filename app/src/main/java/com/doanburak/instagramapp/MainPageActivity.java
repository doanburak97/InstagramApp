package com.doanburak.instagramapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> emailFromFB;
    ArrayList<String> commentFromFB;
    ArrayList<String> urlFromFB;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.add_post){
            Intent intentToUpload = new Intent(this, UploadActivity.class);
            startActivity(intentToUpload);
        }else if(item.getItemId() == R.id.signOut){
            mAuth.signOut();
            Intent intentToSignIn = new Intent(this, SignInActivity.class);
            startActivity(intentToSignIn);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        emailFromFB = new ArrayList<>();
        commentFromFB = new ArrayList<>();
        urlFromFB = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        getDataFromFirestore();
    }

    public void getDataFromFirestore(){

        CollectionReference collectionReference = firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Toast.makeText(MainPageActivity.this, error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                if (value != null){

                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> data = snapshot.getData();

                        //Casting
                        String comment = (String) data.get("comment");
                        String email = (String) data.get("email");
                        String downloadUrl = (String) data.get("downloadUrl");

                        emailFromFB.add(email);
                        commentFromFB.add(comment);
                        urlFromFB.add(downloadUrl);

                    }

                }


            }
        });

    }
}