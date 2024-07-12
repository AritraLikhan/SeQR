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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GetKey#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GetKey extends Fragment {
    ArrayList<String> myArrayList = new ArrayList<>();
    ArrayList<String> myIdList = new ArrayList<>();
    DatabaseReference mRef;
    ListView myListView;
    TextView textView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types of parameters
//    private String qrcodeText;
//    private String messageText;

    public GetKey() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GetKey.
     */
    // TODO: Rename and change types and number of parameters


    public static GetKey newInstance(String uid,String qrcodeValue, String message) {
        GetKey fragment = new GetKey();
        Bundle args = new Bundle();
        args.putString("curruserId",uid);
        args.putString("qrcodeText",qrcodeValue);
        args.putString("messageText", message);
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
        View view = inflater.inflate(R.layout.fragment_get_key, container, false);
        textView = view.findViewById(R.id.idDetect);
        textView.setText(getArguments().getString("messageText"));
       Toast.makeText(requireContext(),getArguments().getString("messageText"),Toast.LENGTH_SHORT).show();
        final ArrayAdapter<String> myArrayAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,myArrayList);
        final ArrayAdapter<String> myIdAdapter = new ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,myIdList);
        myListView = view.findViewById(R.id.idNameList);
       // myListView.setAdapter(myArrayAdapter);
        myListView.setAdapter(myArrayAdapter);



        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GetKeyNext fragment = GetKeyNext.newInstance(getArguments().getString("curruserId"),myIdList.get(i),getArguments().getString("qrcodeText"),getArguments().getString("messageText"));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });



        mRef = FirebaseDatabase.getInstance().getReference("users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                for(DataSnapshot nameSnapshot: snapshot.getChildren())
                {

                    String uid = nameSnapshot.getKey();
                    myIdList.add(uid);
                    String firstName = nameSnapshot.child("Account Info").child("firstName").getValue(String.class);
                    String lastName = nameSnapshot.child("Account Info").child("lastName").getValue(String.class);
                    String email = nameSnapshot.child("Account Info").child("email").getValue(String.class);
                    myArrayList.add(firstName+" "+lastName+"\n"+email);
                    myArrayAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return  view;
    }
}