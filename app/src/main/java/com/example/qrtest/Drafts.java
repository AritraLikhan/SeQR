package com.example.qrtest;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Drafts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Drafts extends Fragment {

    ListView myListView;
    ArrayList<String>myArrayList = new ArrayList<>();
    ArrayList<String>myText = new ArrayList<>();
    DatabaseReference mRef;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Drafts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Drafts.
     */
    // TODO: Rename and change types and number of parameters
//    public static Drafts newInstance(String param1, String param2) {
//        Drafts fragment = new Drafts();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static Drafts newInstance(String uid)
    {
        Drafts fragment = new Drafts();
        Bundle args = new Bundle();
        args.putString("userId", uid);
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
               // Profile fragment = Profile.newInstance(getArguments().getString("userId"));
//
//        // Replace the current fragment with the new instance
//
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, new Profile(getArguments().getString("userId")));
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
        View view = inflater.inflate(R.layout.fragment_drafts, container, false);
        String userId = getArguments().getString("userId");
        Toast.makeText(requireContext(),userId,Toast.LENGTH_SHORT).show();

//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<String> Texts = new ArrayList<>();
//                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    // Assuming your user IDs are stored under a child node like "userId"
//                    String text = userSnapshot.child("Text Files").getValue(String.class);
//                    System.out.println(text);
//                    Texts.add(text);
//                }
//                // Create and set up your ListView with the user IDs
//                ListView listView = view.findViewById(R.id.idTextList);
//                ArrayAdapter adapter = new ArrayAdapter(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Texts);
//                listView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // Handle the error
//            }
//        });


      final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,myArrayList);
       myListView = view.findViewById(R.id.idTextList);
       myListView.setAdapter(myArrayAdapter);
       mRef = FirebaseDatabase.getInstance().getReference("users").child(userId).child("Text Files");
       mRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for(DataSnapshot textFileSnapshot: snapshot.getChildren())
               {
                   //String text = textFileSnapshot.getValue(String.class);
                   String textName = textFileSnapshot.getKey();
                   String text = textFileSnapshot.child("fileText").getValue(String.class);
                   myArrayList.add(textName);
                   myText.add(text);
                   myArrayAdapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Editor fragment = Editor.newInstance(getArguments().getString("userId"),myText.get(i));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });






        return view;

    }

    public void setupListView(List<String> userIds) {
        ListView listView = getView().findViewById(R.id.idTextList);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.list_content, userIds);
        listView.setAdapter(adapter);
    }



}