package com.example.qrtest;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GenerateNSave#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GenerateNSave extends Fragment {

    Button button;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GenerateNSave() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GenerateNSave.
     */
    // TODO: Rename and change types and number of parameters


    public static GenerateNSave newInstance(String uid,String userInput,String Text)
    {
        GenerateNSave fragment = new GenerateNSave ();
        Bundle args = new Bundle();
        args.putString("userId", uid);
        args.putString("userInput",userInput);
        args.putString("Text",Text);
        fragment.setArguments(args);
        return fragment;
    }


    public static GenerateNSave newInstance(String param1, String param2) {
        GenerateNSave fragment = new GenerateNSave();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_generate_n_save, container, false);

        ImageView imageView = view.findViewById(R.id.qr_code_image);
        button = view.findViewById(R.id.idSavePdfKeys);

        //SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
       // String savedUserInput = preferences.getString("userInputKey", ""); // Provide a default value if the key doesn't exist

// You can then use savedUserInput in your app as needed.

        String dataToSign = getArguments().getString("Text");
        String SS = null;

        try {
            // Create a KeyPairGenerator for generating a key pair (public and private keys)
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size can be adjusted as needed
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get the private key and public key from the key pair
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();

            // Create a Signature object and initialize it with the private key for signing
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);

            // Input message
            //String message = "This is the message to be signed.";

            // Convert the message to bytes and update the Signature object
            byte[] messageBytes = dataToSign.getBytes("UTF-8");
            signature.update(messageBytes);

            // Generate the digital signature
            byte[] digitalSignature = signature.sign();

            // Encode the digital signature as a Base64-encoded string
            SS = Base64.getEncoder().encodeToString(digitalSignature);

           // SS = signatureString;

            // Print the digital signature
            //System.out.println("Digital Signature: " + signatureString);

            try {
                // Bitmap qrCode = generateQRCode(SS);

                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                BitMatrix bitMatrix = qrCodeWriter.encode(SS, BarcodeFormat.QR_CODE, 512, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                    }
                }
                imageView.setImageBitmap(bitmap);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
//                        Bitmap bitmap1 = bitmapDrawable.getBitmap();
//                        FileOutputStream outputStream = null;
//                        File file = Environment.getExternalStorageDirectory();
//                        File dir = new File(file.getAbsolutePath());
//                        dir.mkdir();
//
//                        String filename = String.format("%d.png",System.currentTimeMillis());
//                        File outfile = new File(dir,filename);
//
//                        try {
//                            outputStream = new FileOutputStream(outfile);
//                        } catch (FileNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                        bitmap1.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//                        try {
//                            outputStream.flush();
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        try {
//                           outputStream.close();
//                        }
//                        catch (Exception e)
//                        {
//                             e.printStackTrace();
//                        }

                        String privateKeyString = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

                        String publicKeyString = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());

                        TextInfo textInfo = new TextInfo(getArguments().getString("userInput"),getArguments().getString("Text"),publicKeyString,privateKeyString);

                        DatabaseReference drf = FirebaseDatabase.getInstance().getReference("users").child(getArguments().getString("userId")).child("Text Files").child(getArguments().getString("userInput"));
                        drf.setValue(textInfo);

                    }


                });



            } catch (WriterException e) {
                e.printStackTrace();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        // Generate a QR code and set it to the ImageView.

        // Inflate the layout for this fragment
        return view;
    }
}