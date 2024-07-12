package com.example.qrtest;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetKeyNext#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetKeyNext extends Fragment {


    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<String> myKeyList = new ArrayList<>();
    DatabaseReference mRef;
    ListView myListView;
   // private static final String user;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String muid;


    public GetKeyNext() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetKeyNext.
     */
    // TODO: Rename and change types and number of parameters

    public static GetKeyNext newInstance(String curruid,String uid,String qrcodeText,String messageText) {
        GetKeyNext fragment = new GetKeyNext();
        Bundle args = new Bundle();
        args.putString("curruserId",curruid);
        args.putString("userId", uid);
        args.putString("signature", qrcodeText);
        args.putString("message", messageText);
        fragment.setArguments(args);
        return fragment;
    }


    public static GetKeyNext newInstance(String param1, String param2) {
        GetKeyNext fragment = new GetKeyNext();
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
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
             // muid = getArguments().getString(userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_get_key_next, container, false);

        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,myArrayList);
        final ArrayAdapter<String> myKeyAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,myKeyList);
        myListView = view.findViewById(R.id.idKeyList);
        // myListView.setAdapter(myArrayAdapter);
        myListView.setAdapter(myArrayAdapter);



        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Verify fragment = Verify.newInstance(getArguments().getString("curruserId"),myKeyList.get(i),getArguments().getString("signature"),getArguments().getString("message"));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });



        mRef = FirebaseDatabase.getInstance().getReference("users").child(getArguments().getString("userId")).child("Text Files");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot nameSnapshot: snapshot.getChildren())
                {
                    String fileName = nameSnapshot.child("fileName").getValue(String.class);
                    String publicKey = nameSnapshot.child("publicKey").getValue(String.class);
//                    String uid = nameSnapshot.getKey();
//                    myIdList.add(uid);
//                    String firstName = nameSnapshot.child("Account Info").child("firstName").getValue(String.class);
//                    String lastName = nameSnapshot.child("Account Info").child("lastName").getValue(String.class);
//                    String email = nameSnapshot.child("Account Info").child("email").getValue(String.class);
//                    myArrayList.add(firstName+" "+lastName+"\n"+email);
//                    myArrayAdapter.notifyDataSetChanged();
                    myKeyList.add(publicKey);
                    myArrayList.add(fileName);
                    myArrayAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        return view;
    }
}