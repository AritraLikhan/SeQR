package com.example.qrtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AboutApp extends AppCompatActivity {


    TextView gen,algo,scan,verify;
    LottieAnimationView lottiegen,lottiealgo,lottiescan,lottieverify;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        gen = findViewById(R.id.idGenInfo);
        algo = findViewById(R.id.idAlgoInfo);
        scan = findViewById(R.id.idScanInfo);
        verify = findViewById(R.id.idVerifiedInfo);
        extractData();


    }

    public void extractData()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
      //  String url = "https://api.myjson.online/v1/records/0838ac2f-4dd5-4ac4-b986-fc3f45ef711c";
        String url = "https://api.myjson.online/v1/records/a22a6d59-5c88-4b26-97ce-86862154e724";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Json Parsing

                jsonParse(response);

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(stringRequest);

    }

    public void jsonParse(String response)
    {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

//            ArrayList<String> name = new ArrayList<>();
//            ArrayList<String> roll = new ArrayList<>();
//            ArrayList<String> batch = new ArrayList<>();
//            ArrayList<String> home = new ArrayList<>();



                JSONObject jsonObject2 = jsonArray.getJSONObject(0);
                gen.setText(jsonObject2.getString("description"));
            JSONObject jsonObject3 = jsonArray.getJSONObject(1);
            algo.setText(jsonObject3.getString("description"));
            JSONObject jsonObject4 = jsonArray.getJSONObject(2);
            scan.setText(jsonObject4.getString("description"));
            JSONObject jsonObject5 = jsonArray.getJSONObject(3);
            verify.setText(jsonObject5.getString("description"));


               // String jName,jRoll,jBatch,jHome;
//                jName = jsonObject2.getString("name");
//                jRoll = String.valueOf(jsonObject2.getInt("roll"));
//                jBatch = String.valueOf(jsonObject2.getInt("batch"));
//                jHome = jsonObject2.getString("home");
//
////
//
//            ArrayAdapter arrayAdapter = new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,name);
//            ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,roll);
//            ArrayAdapter arrayAdapter3 = new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,batch);
//            ArrayAdapter arrayAdapter4 = new ArrayAdapter(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,home);
//            listView.setAdapter(arrayAdapter);
//             listView.setAdapter(arrayAdapter2);
//            listView.setAdapter(arrayAdapter3);
//            listView.setAdapter(arrayAdapter4);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //Toast.makeText(MainActivity.this, "Clicked on"+name.get(position), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(MainActivity.this,Show.class);
//                    intent.putExtra("Name",name.get(position));
//                    intent.putExtra("Roll",roll.get(position));
//                    intent.putExtra("Batch",batch.get(position));
//                    intent.putExtra("Home",home.get(position));
//                    startActivity(intent);
//                }
//            });




        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}