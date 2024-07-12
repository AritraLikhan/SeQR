package com.example.qrtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profile extends Fragment {
    CardView cdGen,cdScan, cdPro, cdDft;

    private  CardView cd;
    private EditText et;

   // View view;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String id;

    public Profile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profile.
     */
    // TODO: Rename and change types and number of parameters

    public Profile(String id)
    {
        this.id = id;
    }

    public static Profile newInstance(String uid)
    {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString("userId", uid);
        fragment.setArguments(args);
        return fragment;
    }
    public static Profile newInstance(String param1, String param2) {
        Profile fragment = new Profile();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
                setEnabled(true);
                // Handle the back button event
                requireActivity().moveTaskToBack(true);
            }
        };
       // requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


//        cd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.idFragContainer,new Trash()).commit();
//            }
//        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Enabled by default, and causes the callback to be added to the
                // OnBackPressedDispatcher's list.
                setEnabled(true);
                // Handle the back button event
                requireActivity().moveTaskToBack(true);
            }
        });

//        CardView cardView = view.findViewById(R.id.idHelp); // Use 'view' to find the CardView
//
//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Create a FragmentTransaction to navigate to the target fragment
//                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.idFragContainer, new Drafts());
//                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
//                transaction.commit();
//            }
//        });

        cdGen = view.findViewById(R.id.idGenerateQRR);
       // String userId = getArguments().getString("userId");

                try {
                    cdGen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Create a FragmentTransaction to navigate to the target fragment
                            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                            Editor fragment = Editor.newInstance(id,"");
                            transaction.replace(R.id.idFragContainer, fragment);
                            transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                            transaction.commit();
                        }
                    });
                }
                catch (Exception e)
                {
                }

        cdPro = view.findViewById(R.id.idProfile);
        // String userId = getArguments().getString("userId");

        try {
            cdPro.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create a FragmentTransaction to navigate to the target fragment
                    FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                   Profile1 fragment = Profile1.newInstance(id);
                    transaction.replace(R.id.idFragContainer, fragment);
                    transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                    transaction.commit();
                }
            });
        }
        catch (Exception e)
        {
        }

        cdScan = view.findViewById(R.id.idScanQRR);

        cdScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a FragmentTransaction to navigate to the target fragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                Scanner fragment = Scanner.newInstance(id);
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();
            }
        });
        cdDft = view.findViewById(R.id.idDrafts);

        cdDft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a FragmentTransaction to navigate to the target fragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                Drafts fragment = Drafts.newInstance(id);
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null); // Optional: Add the transaction to the back stack
                transaction.commit();
            }
        });


        return view;
    }

}