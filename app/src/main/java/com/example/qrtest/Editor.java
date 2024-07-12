package com.example.qrtest;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Editor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Editor extends Fragment {

   //String Text;
   EditText editText;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Editor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Editor.
     */
    // TODO: Rename and change types and number of parameters
//    public static Editor newInstance(String param1, String param2) {
//        Editor fragment = new Editor();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }


    public static Editor newInstance(String uid,String text)
    {
        Editor fragment = new Editor();
        Bundle args = new Bundle();
        args.putString("userId", uid);
        args.putString("text",text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                Profile fragment = Profile.newInstance(getArguments().getString("userId"));
//
//        // Replace the current fragment with the new instance
//
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editor, container, false);
        String userId = getArguments().getString("userId");
        Toast.makeText(requireContext(),getArguments().getString("text"),Toast.LENGTH_SHORT).show();

         editText = view.findViewById(R.id.paragraphEditText);
        editText.setBackgroundResource(android.R.color.transparent);
        editText.setText(getArguments().getString("text"));
      // String Text = editText.getText().toString();


        Button saveBtn = view.findViewById(R.id.idSaveBtn);
        Button savegenBtn = view.findViewById(R.id.idSaveGenBtn);// Use 'view' to find the CardView

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a FragmentTransaction to navigate to the target fragment
//                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.idFragContainer, new Settings());
//                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
//                transaction.commit();
                String Text = editText.getText().toString();
                saveText(userId,Text);
            }
        });


        savegenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Text = editText.getText().toString();
                saveGenText(userId,Text);
            }
        });

        return view;
    }

    public void saveText(String uid,String Text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), androidx.appcompat.R.style.AlertDialog_AppCompat);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoguebox, null);

        final EditText editTextInput = dialogView.findViewById(R.id.editTextInput);


        builder.setView(dialogView)
                .setTitle("Set a name");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String userInput = editTextInput.getText().toString();
//                        DatabaseReference drf = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Text Files");
//
//                        drf.child(userInput).setValue(Text, new DatabaseReference.CompletionListener() {
//                            @Override
//                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                                if (databaseError == null) {
//                                    // The data was successfully stored.
//                                    Toast.makeText(requireContext(),Text,Toast.LENGTH_SHORT).show();
//                                } else {
//                                    // Handle the error, if any.
//                                }
//                            }
//                        });


                        TextInfo textInfo = new TextInfo(userInput,Text,"","");

                        DatabaseReference drf = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Text Files").child(userInput);
                        drf.setValue(textInfo);

                        Drafts fragment = Drafts.newInstance(uid);

                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.idFragContainer, fragment);
                        transaction.addToBackStack(null);

                        // Commit the transaction
                        transaction.commit();


                       // NavDirections action = SourceFragmentDirections.actionSourceFragmentToDestinationFragment(uid);
                      //  NavHostFragment.findNavController(Editor.this).navigate(action);


                       // getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
                        // Handle the user's input (e.g., display it or use it as needed)
                       //Toast.makeText(requireContext(),userInput,Toast.LENGTH_SHORT).show();
  }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the cancel action (if needed)
                    }
                });

        builder.create().show();
    }


    public void saveGenText(String uid,String Text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext(), androidx.appcompat.R.style.AlertDialog_AppCompat);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialoguebox, null);

        final EditText editTextInput = dialogView.findViewById(R.id.editTextInput);


        builder.setView(dialogView)
                .setTitle("Set a name");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String userInput = editTextInput.getText().toString();


   //             DatabaseReference drf = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Text Files");

//                DatabaseReference drf = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Text Files");
//
//                drf.child(userInput).setValue(Text, new DatabaseReference.CompletionListener() {
//                    @Override
//                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                        if (databaseError == null) {
//                            // The data was successfully stored.
//                            Toast.makeText(requireContext(),Text,Toast.LENGTH_SHORT).show();
//                        } else {
//                            // Handle the error, if any.
//                        }
//                    }
//                });

                GenerateNSave fragment = GenerateNSave.newInstance(uid,userInput,Text);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();


                // NavDirections action = SourceFragmentDirections.actionSourceFragmentToDestinationFragment(uid);
                //  NavHostFragment.findNavController(Editor.this).navigate(action);


                // getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,fragment).commit();
                // Handle the user's input (e.g., display it or use it as needed)
                //Toast.makeText(requireContext(),userInput,Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Handle the cancel action (if needed)
            }
        });

        builder.create().show();
    }


    public void setupListView(List<String> Textfiles) {
        ListView listView = getView().findViewById(R.id.idTextList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1,Textfiles);
        listView.setAdapter(adapter);
    }


}