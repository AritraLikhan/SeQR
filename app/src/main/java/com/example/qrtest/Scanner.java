package com.example.qrtest;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.annotations.Nullable;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Scanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Scanner extends Fragment {

    EditText editText;
    Button getBtn;
    private TextView qrCodeValueTextView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Scanner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scanner.
     */
    // TODO: Rename and change types and number of parameters
    public static Scanner newInstance(String uid)
    {
       Scanner fragment = new Scanner();
        Bundle args = new Bundle();
        args.putString("userId", uid);
        fragment.setArguments(args);
        return fragment;
    }



    public static Scanner newInstance(String param1, String param2) {
        Scanner fragment = new Scanner();
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


        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_scanner, container, false);
            editText = view.findViewById(R.id.paragraphEditText);
            editText.setBackgroundResource(android.R.color.transparent);

             getBtn = view.findViewById(R.id.idGetKeyBtn);

            //qrCodeValueTextView = view.findViewById(R.id.qr_code_value);
            IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
            integrator.setPrompt("Scan a QR code");
            integrator.initiateScan();

            return view;
        }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // Handle scan failure
            } else {
                // Handle successful scan
                String qrCodeValue = result.getContents();

                  passData(qrCodeValue);
                //qrCodeValueTextView.setText(qrCodeValue);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    public void passData(String qrCodeValue)
    {
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                GetKey fragment = GetKey.newInstance(getArguments().getString("userId"),qrCodeValue,message);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();

            }
        });
    }

}




















