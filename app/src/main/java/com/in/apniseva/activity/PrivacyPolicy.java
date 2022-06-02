package com.in.apniseva.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.os.Bundle;
import android.webkit.WebView;

import com.in.apniseva.R;

public class PrivacyPolicy extends AppCompatActivity {

    WebView webView;
    private Boolean exit = false;
    private UiModeManager uiModeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String url = "https://apniseva.com/privacypolicies";
        WebView  view=(WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}