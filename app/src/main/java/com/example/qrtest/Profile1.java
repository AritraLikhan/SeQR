package com.example.qrtest;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.collection.LLRBNode;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    TextView showName, showMail,showDate;
    EditText editFirst, editLast, editDate;
    EditText oldPass, newPass;
    Button upPassBtn;
    Button upInfoBtn;
    private String mParam1;
    private String mParam2;

    public Profile1() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile1.
     */
    // TODO: Rename and change types and number of parameters
    public static Profile1 newInstance(String uid) {
        Profile1 fragment = new Profile1();
        Bundle args = new Bundle();
        args.putString("userId", uid);
        fragment.setArguments(args);
        return fragment;
    }

    public static Profile1 newInstance(String param1, String param2) {
        Profile1 fragment = new Profile1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile1, container, false);
         showName = view.findViewById(R.id.idTextName);
         showMail =  view.findViewById(R.id.idTextEmail);
         showDate = view.findViewById(R.id.idTextDOB);

         editFirst = view.findViewById(R.id.idFirstName);
         editLast = view.findViewById(R.id.idLastName);
         editDate = view.findViewById(R.id.idEditDOB);

//         oldPass = view.findViewById(R.id.idOldPass);
//         newPass = view.findViewById(R.id.idNewPass);
         upInfoBtn = view.findViewById(R.id.idEditBtn);
     //    upPassBtn = view.findViewById(R.id.idCngBtn);

      DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("users").child(getArguments().getString("userId")).child("Account Info");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String show = user.getEmail();
        Toast.makeText(requireContext(),show,Toast.LENGTH_SHORT).show();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                    String uid = nameSnapshot.getKey();
//                    myIdList.add(uid);
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);
                    String fullname = firstName +" "+ lastName;
                    showName.setText(showName.getText()+fullname);
                    String date = snapshot.child("dateOfBirth").getValue(String.class);
                    //showName.setTextColor(Color.parseColor("#47944A"));
               //     String email = snapshot.child("email").getValue(String.class);
                   showMail.setText("Email:\n"+show);
                    showDate.setText(showDate.getText()+date);

                    //showMail.setTextColor(Color.parseColor("#47944A"));
//                    myArrayList.add(firstName+" "+lastName+"\n"+email);
//                    myArrayAdapter.notifyDataSetChanged()

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


       upInfoBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


//               user.updateEmail(editMail.getText().toString())
//                       .addOnCompleteListener(new OnCompleteListener<Void>() {
//                           @Override
//                           public void onComplete(@NonNull Task<Void> task) {
//                               if (task.isSuccessful()) {
//                                   Log.d(TAG, "User email address updated.");
//                               }
//                           }
//                       });

               Map<String, Object> updates = new HashMap<>();
               updates.put("dateOfBirth", editDate.getText().toString());
               updates.put("firstName", editFirst.getText().toString());
               updates.put("lastName",editLast.getText().toString());
             //  DatabaseReference mRef2 =  FirebaseDatabase.getInstance().getReference("users").child(getArguments().getString("userId"));

               mRef.updateChildren(updates)
                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                           @Override
                           public void onComplete(@NonNull Task<Void> task) {
                               if (task.isSuccessful()) {

                                   showDate.setText("Date:\n"+editDate.getText().toString());
                                   showName.setText("Name:\n"+editFirst.getText().toString()+" "+editLast.getText().toString());
                                  // showMail.setText(showMail.getText().toString());
                                   Log.d(TAG, "User data updated.");
                               }
                           }
                       });

           }
       });


//      upPassBtn.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View v) {
//              user.updatePassword(oldPass.getText().toString())
//                      .addOnCompleteListener(new OnCompleteListener<Void>() {
//                          @Override
//                          public void onComplete(@NonNull Task<Void> task) {
//                              if (task.isSuccessful()) {
//                                  Log.d(TAG, "User password updated.");
//                              }
//                          }
//                      });
//
//
//              Map<String, Object> updates = new HashMap<>();
//              updates.put("password", newPass.getText().toString());
//              //  DatabaseReference mRef2 =  FirebaseDatabase.getInstance().getReference("users").child(getArguments().getString("userId"));
//
//              mRef.updateChildren(updates)
//                      .addOnCompleteListener(new OnCompleteListener<Void>() {
//                          @Override
//                          public void onComplete(@NonNull Task<Void> task) {
//                              if (task.isSuccessful()) {
//                                  Log.d(TAG, "User password updated.");
//                              }
//                          }
//                      });
//          }
//      });


        return view;
    }
}