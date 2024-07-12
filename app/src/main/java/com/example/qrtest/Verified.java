package com.example.qrtest;

import android.animation.ValueAnimator;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Verified#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Verified extends Fragment {

    TextView textView;
   LottieAnimationView lottie;
   Button backBtn;
    private int shortAnimationDuration;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Verified() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Verified.
     */
    // TODO: Rename and change types and number of parameters

    public static Verified newInstance(String uid) {
        Verified fragment = new Verified();
        Bundle args = new Bundle();
        args.putString("userId",uid);
        fragment.setArguments(args);
        return fragment;
    }


    public static Verified newInstance(String param1, String param2) {
        Verified fragment = new Verified();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // textView = findViewById(R.id.idVerified);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
       View view =  inflater.inflate(R.layout.fragment_verified, container, false);

        textView = view.findViewById(R.id.idVerified);
        backBtn = view.findViewById(R.id.idBackDash);
        textView.setAlpha(0f);
        textView.setVisibility(View.VISIBLE);

        textView.animate()
                .alpha(1f)
                .setDuration(1000)
                .setListener(null);


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile fragment =Profile.newInstance(getArguments().getString("userId"));
//
//        // Replace the current fragment with the new instance
//
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.idFragContainer, fragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });


//        textView = view.findViewById(R.id.idVerified);
//        //loadingView = findViewById(R.id.loading_spinner);
//
//        // Initially hide the content view.
//        textView.setVisibility(View.GONE);
//
//        // Retrieve and cache the system's default "short" animation time.
//        shortAnimationDuration = getResources().getInteger(
//                android.R.integer.config_shortAnimTime);


   //     lottie = view.findViewById(R.id.lottie);

   //     lottie.animate().translationX(2000).setDuration(2000).setStartDelay(2900);

//        LottieAnimationView animationView = view.findViewById(R.id.lottie);
//        animationView.setAnimation("Animation - 1699592017486.json");
//        animationView.playAnimation();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Profile fragment = Profile.newInstance(getArguments().getString("userId"));
//
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                transaction.replace(R.id.idFragContainer, fragment);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commit();
//            }
//        },5000);

//        ValueAnimator animator
//                = ValueAnimator.ofFloat(0f, 1f);
//        animator
//                .addUpdateListener(animation -> {
//                    lottie.setProgress((Float) animation.getAnimatedValue());
//                });
//        animator.start();





//        ImageView imageView = view.findViewById(R.id.idImage);
//
//        Glide.with(this)
//                .asGif()
//                .load("D:/Libraries/This_PC_Folders/ver.gif")
//                .diskCacheStrategy(DiskCacheStrategy.DATA)
//                .into(imageView);

        return view;
    }


}