package com.in.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForGotPassword extends AppCompatActivity {

    EditText edit_MobileNumber;
    Button btn_verifyOtp;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_got_password);

        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        btn_verifyOtp = findViewById(R.id.btn_verifyOtp);

        Window window = ForGotPassword.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(ForGotPassword.this, R.color.white));


        btn_verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_MobileNumber.getText().toString().trim().equals("")){

                    edit_MobileNumber.setError("Fill The Fields");

                }else if(edit_MobileNumber.getText().toString().trim().length() != 10){

                    edit_MobileNumber.setError("Enter Valide Mobile NO");

                }else{

                    String str_mobile = edit_MobileNumber.getText().toString().trim();

                    sendOtp("send_otp",str_mobile);
                }
            }
        });


    }

    public void sendOtp(String type,String mobileNo) {

        ProgressDialog progressDialog = new ProgressDialog(ForGotPassword.this);
        progressDialog.setMessage("OTP Send mobile No Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.forgotPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(ForGotPassword.this, msg, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(ForGotPassword.this,VerificationCode.class);
                        intent.putExtra("className","ForGotPassword");
                        intent.putExtra("mobileno",mobileNo);
                        startActivity(intent);

                    }else if(status.equalsIgnoreCase("NOK")){

                        String msg = jsonObject.getString("msg");
                        Toast.makeText(ForGotPassword.this, msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    Toast.makeText(getApplicationContext(), "Please check Internet Connection", Toast.LENGTH_SHORT).show();

                } else {

                    Log.d("successresponceVolley", "" + error.networkResponse);
                    NetworkResponse networkResponse = error.networkResponse;
                    if (networkResponse != null && networkResponse.data != null) {
                        try {
                            String jError = new String(networkResponse.data);
                            JSONObject jsonError = new JSONObject(jError);
//                            if (error.networkResponse.statusCode == 400) {
                            String data = jsonError.getString("message");
                            Toast.makeText(ForGotPassword.this, data, Toast.LENGTH_SHORT).show();

//                            } else if (error.networkResponse.statusCode == 404) {
//                                JSONArray data = jsonError.getJSONArray("msg");
//                                JSONObject jsonitemChild = data.getJSONObject(0);
//                                String ms = jsonitemChild.toString();
//                                Toast.makeText(RegisterActivity.this, ms, Toast.LENGTH_SHORT).show();
//
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("successresponceVolley", "" + e);
                        }
                    }
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("mobile",mobileNo);
                params.put("type",type);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ForGotPassword.this);
        requestQueue.add(stringRequest);


    }
}