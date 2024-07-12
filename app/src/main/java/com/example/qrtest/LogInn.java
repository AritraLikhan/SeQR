package com.example.qrtest;

import static android.service.controls.ControlsProviderService.TAG;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class LogInn extends AppCompatActivity {

    private Button loginBtn, createBtn ;
    private TextView email , pass ;
    private DatabaseReference drf;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_inn);

        email = findViewById(R.id.idLogMail);
        pass = findViewById(R.id.idLogPass);
        loginBtn = findViewById(R.id.idLogBtn);
        createBtn = findViewById(R.id.idCreateBtn);
        //drf= FirebaseDatabase.getInstance().getReference().child("users");

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInn.this, SignUpp.class);
                startActivity(intent);
            }
        });



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String ps = pass.getText().toString();

                mAuth.signInWithEmailAndPassword(em, ps)
                        .addOnCompleteListener(LogInn.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    if (user != null && user.) {
//                                        // User is logged in and their email is verified
//                                        Toast.makeText(LogInn.this,"Successful",Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(LogInn.this,"Mail not verified",Toast.LENGTH_SHORT).show();
//                                        // User's email is not verified
//                                    }

                                    Log.d(TAG, "signInWithCustomToken:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String uid = user.getUid();

                                    drf= FirebaseDatabase.getInstance().getReference().child("users");

                                    drf.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if(snapshot.exists())
                                            {
                                                User ussr = snapshot.getValue(User.class);
                                                String data = ussr.firstName;
                                                Intent intent = new Intent(LogInn.this, HomeActivity3.class);
                                                intent.putExtra("userId",uid);
                                                startActivity(intent);
                                                Toast.makeText(LogInn.this,"Successfully logged in,user data: "+uid,Toast.LENGTH_SHORT).show();
                                            }
                                            else
                                            {
                                                Toast.makeText(LogInn.this,"Successfully logged in,user data doesn't exist",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                   // Toast.makeText(LogInn.this,"Successful"+data,Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.w(TAG, "signInWithCustomToken:failure", task.getException());
                                    Toast.makeText(LogInn.this,"Authentication failed",Toast.LENGTH_SHORT).show();
                                    // Authentication failed
                                }
                            }
                        });

            }

        });

//        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
//            @Override
//            public void handleOnBackPressed() {
//                // Handle the back button event
////                Profile fragment = Profile.newInstance(getArguments().getString("curruserId"));
//////
//////        // Replace the current fragment with the new instance
//////
////                FragmentTransaction transaction = getFragmentManager().beginTransaction();
////                transaction.replace(R.id.idFragContainer, fragment);
////                transaction.addToBackStack(null);
////
////                // Commit the transaction
////                transaction.commit();
//                Intent intent = new Intent(LogInn.this, SignUpp.class);
//                startActivity(intent);
//
//            }
//        };
//        LogInn.getOnBackPressedDispatcher().addCallback(this, callback);



    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}