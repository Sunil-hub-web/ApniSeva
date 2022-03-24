package com.in.apniseva.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.in.apniseva.R;

public class CheckInternetConnection extends AppCompatActivity {

    Button restartapp;
    RelativeLayout networkConnection;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet_connection);

        restartapp = findViewById(R.id.restartapp);
        networkConnection = findViewById(R.id.networkConnection);
        restartapp = findViewById(R.id.restartapp);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        restartapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkInternet();
            }
        });
    }

    public void checkInternet() {

        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()) {

            Toast.makeText(this, "Check Internet Connection", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(CheckInternetConnection.this, "Connected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(CheckInternetConnection.this, SplashScreen.class);
            startActivity(intent);

        }
    }
}