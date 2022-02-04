package com.example.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apniseva.AppURL.AppUrl;
import com.example.apniseva.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class SplashScreen extends AppCompatActivity {

    Handler handler;
    ImageView splashScreen;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash_screen);

        splashScreen = findViewById(R.id.splashScreen);

        Window window = SplashScreen.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SplashScreen.this, R.color.white));

        checkInternet();

    }

    public void timer(){

        handler = new Handler();

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(SplashScreen.this, LoginPage.class);
                startActivity(i);
                finish();
            }
        }, 5000);
    }

    public void getsplashscreenImage(){

        ProgressDialog progressDialog = new ProgressDialog(SplashScreen.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.splashScreen, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("status");

                    if(message.equals("OK")){

                        String image = jsonObject.getString("image");

                        Picasso.with(SplashScreen.this).load(image)
                                .into(splashScreen);
                        timer();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace ();
                Toast.makeText(SplashScreen.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
        requestQueue.add(stringRequest);
        

    }

    public void checkInternet() {

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SplashScreen.this, CheckInternetConnection.class);
            startActivity(intent);

        } else {

            //Toast.makeText(SplashScreen.this, "Connected", Toast.LENGTH_SHORT).show();

            getsplashscreenImage();

        }
    }
}