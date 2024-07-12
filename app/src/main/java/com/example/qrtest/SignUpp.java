package com.example.qrtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpp extends AppCompatActivity {
    private EditText fName, lName, dob, eMail, setPass, conPass;

    private Button signupBtn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    //static int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upp);

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        fName = findViewById(R.id.idUserName11);
        lName = findViewById(R.id.idUserName22);
          dob = findViewById(R.id.idDOBB);
        eMail = findViewById(R.id.idEmaill);
        setPass = findViewById(R.id.idPasss);
        conPass = findViewById(R.id.idPassConn);

        signupBtn = findViewById(R.id.SignUpButtonn);

//        signupBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //  System.out.println(Up);
////                dateofbirthEditText = findViewById(R.id.DOBS);
//
//
//
//
//                //Toast.makeText(SignUp.this, name, Toast.LENGTH_SHORT).show();
////                User usr = new User(username,email,password);
////                mDatabase.child("users").child("info").setValue(usr);
////                User usr1 = new User();
////                User usr2 = new User();
////                usr1.Basic(dob,username);
////                usr2.Cred(email,password);
////                mDatabase.child("users").child("Basic").child(email).setValue(usr1);
////                mDatabase.child("users").child("Credentials").child(email).setValue(usr2);
//
//                //  id++;
//                // Convert the increased value to a string and display it
//                //   String counterAsString = String.valueOf(id);
//
//
//                DatabaseReference usersReference = mDatabase.child("users");
//
//// Query to retrieve the latest ID
////                usersReference.orderByKey().limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
////                    @Override
////                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        // Check if there are any existing IDs
////                        if (dataSnapshot.exists()) {
////                            // Get the latest ID
////                            String latestId = dataSnapshot.getChildren().iterator().next().getKey();
////
////                            // Increment the ID
////                            int newId = Integer.parseInt(latestId) + 1;
////
////                            // Now you have the new ID, and you can proceed to sign up the user
////                            // Register the user using the new ID
//                            signUpUser();
//
//                    }
//
//
//
//            }
//        });
//    }


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });


    }


//    public void signUpUser ()
//    {
//
//        String firstName = fName.getText().toString();
//        String lastName = lName.getText().toString();
//        String email = eMail.getText().toString();
//        String password = setPass.getText().toString();
//        String confirmPassword = conPass.getText().toString();
//
//
//        if (!password.equals(confirmPassword)) {
//            Toast.makeText(SignUpp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        User user = new User(firstName, lastName, email, password);
//        String counterAsString = String.valueOf(newID);
//        mDatabase.child("users").child(counterAsString).setValue(user);
//        finish();
//
//
//        //       Intent intent = new Intent(SignUp.this,MainActivity.class);
////                intent.putExtra("DateOfBirth",dob);
////                intent.putExtra("UserName",username);
////                intent.putExtra("Email",email);
////                intent.putExtra("Password",password);
////               startActivity(intent);
//        //Toast.makeText(SignUp.this,"")
//
//
//    }


    public void signUpUser() {
        String firstName = fName.getText().toString();
        String lastName = lName.getText().toString();
        String dateOfBirth = dob.getText().toString();
        String email = eMail.getText().toString();
        String password = setPass.getText().toString();
        String confirmPassword = conPass.getText().toString();
//
        if (!password.equals(confirmPassword)) {
            Toast.makeText(SignUpp.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Signup successful, add user data to Firebase Realtime Database
                            String userId = mAuth.getCurrentUser().getUid();

                            // User user = new User(firstName, lastName, email, password); // Create a User class to hold user data
                            //  mDatabase.child("users").child(userId).setValue(user);

                            User user1 = new User();
                            // User user2 = new User();
                            user1.AccInfo(firstName, lastName,dateOfBirth, email, password);
                            // user2.TextFiles("");
                            mDatabase.child("users").child(userId).child("Account Info").setValue(user1);
                            mDatabase.child("users").child(userId).child("Text Files");
                            Toast.makeText(SignUpp.this, "Signed up as a SeQR user! Log in to continue", Toast.LENGTH_SHORT).show();
                            // Redirect to your main activity or perform any necessary actions
                            // Example: startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();
                        } else {
                            // Signup failed
                            Toast.makeText(SignUpp.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });






        // Signup successful, add user data to Firebase Realtime Database
//                            String userId = mAuth.getCurrentUser().getUid();
//
//                            // User user = new User(firstName, lastName, email, password); // Create a User class to hold user data
//                            //  mDatabase.child("users").child(userId).setValue(user);
//
//                            User user1 = new User();
//                            // User user2 = new User();
//                            user1.AccInfo(firstName, lastName, email, password);
//                            // user2.TextFiles("");
//                            mDatabase.child("users").child(userId).child("Account Info").setValue(user1);
//                            mDatabase.child("users").child(userId).child("Text Files");
//                            Toast.makeText(SignUpp.this, "Signed up as a SeQR user! Log in to continue", Toast.LENGTH_SHORT).show();
//                            // Redirect to your main activity or perform any necessary actions
//                            // Example: startActivity(new Intent(SignupActivity.this, MainActivity.class));
//                            finish();
//
//                            // Signup failed
//                            Toast.makeText(SignUpp.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();







    }




}









