package com.example.qrtest;

import android.os.Build;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Verify#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Verify extends Fragment {
    TextView textView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Verify() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Verify.
     */
    // TODO: Rename and change types and number of parameters

    public static Verify newInstance(String curruid, String keyString,String qrcodeText,String messageText) {
        Verify fragment = new Verify();
        Bundle args = new Bundle();
        args.putString("curruserId",curruid);
        args.putString("publicKeyString", keyString);
        args.putString("signature", qrcodeText);
        args.putString("message", messageText);
        fragment.setArguments(args);
        return fragment;
    }


    public static Verify newInstance(String param1, String param2) {
        Verify fragment = new Verify();
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
                Profile fragment = Profile.newInstance(getArguments().getString("curruserId"));
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

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_verify, container, false);
        super.onViewCreated(view, savedInstanceState);
        byte[] publicKeyBytes = Base64.getDecoder().decode(getArguments().getString("publicKeyString"));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        try {
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Signature verifySignature = Signature.getInstance("SHA256withRSA");
            verifySignature.initVerify(publicKey);
            verifySignature.update(getArguments().getString("message").getBytes("UTF-8"));
            byte[] signatureBytes = Base64.getDecoder().decode(getArguments().getString("signature"));
           //Toast.makeText(requireContext(),getArguments().getString("signature"),Toast.LENGTH_LONG).show();
            boolean check =  verifySignature.verify(signatureBytes);
            textView = view.findViewById(R.id.idIsVerified);
            if(check)
            {
               //textView.setText("Verified");
               Verified fragment =Verified.newInstance(getArguments().getString("curruserId"));
//
//        // Replace the current fragment with the new instance
//
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }

            else
            {
                 //textView.setText(getArguments().getString("message"));
                NotVerified fragment =NotVerified.newInstance(getArguments().getString("curruserId"));
//
//        // Replace the current fragment with the new instance
//
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

               // textView.setText(getArguments().getString("signature")+"\n"+getArguments().getString("message")+"\n"+getArguments().getString("publicKeyString"));
            }
//            onBackPressed();

        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }


        return view;
    }

//    public void onBackPressed() {
//        // Create a new instance of the previous fragment
//       Profile fragment = Profile.newInstance(getArguments().getString("curruserId"));
//
//        // Replace the current fragment with the new instance
//
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(R.id.idFragContainer, fragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commit();
//        // If you want to call the super method (default back button behavior), do it last
//
//    }


    //public void onB
}