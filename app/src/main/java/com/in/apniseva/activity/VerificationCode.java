package com.in.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SessionManager;
import com.in.apniseva.SharedPrefManager;
import com.in.apniseva.modelclass.Login_ModelClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerificationCode extends AppCompatActivity {

    TextView text_Timer,resend_OTP,resendtext,mobileNumber;
    Button btn_verifayOtp;
    SessionManager sessionManager;
    String userOTP,userMobileNo;
    OtpTextView otp_view;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code);

        Window window = VerificationCode.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(VerificationCode.this, R.color.white));

        text_Timer = findViewById(R.id.timer);
        resend_OTP = findViewById(R.id.resend_OTP);
        btn_verifayOtp = findViewById(R.id.btn_verifayOtp);
        resendtext = findViewById(R.id.resendtext);
        otp_view = findViewById(R.id.otp_view);
        mobileNumber = findViewById(R.id.mobileNumber);

        timer();

        sessionManager = new SessionManager(this);
        userOTP = sessionManager.getUserOTP();
        userMobileNo = sessionManager.getUserMobileno();

        mobileNumber.setText("+91 " +userMobileNo);

        //otp_view.setOTP(userOTP);

        resend_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text_Timer.setVisibility(View.VISIBLE);
                resendtext.setVisibility(View.VISIBLE);
                resend_OTP.setVisibility(View.GONE);
                timer();

                resendotp(userMobileNo);
            }
        });

        btn_verifayOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String getOTP = otp_view.getOTP();

                verifayOtp(userMobileNo,getOTP);

            }
        });

    }

    public void timer(){

        //Initialize time duration
        long duration = TimeUnit.MINUTES.toMillis(1);
        //Initialize countdown timer

        new CountDownTimer(duration, 5) {
            @Override
            public void onTick(long millisUntilFinished) {

                //When tick
                //Convert millisecond to minute and second

                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                text_Timer.setText(sDuration);

            }

            @Override
            public void onFinish() {

                text_Timer.setVisibility(View.GONE);
                resendtext.setVisibility(View.GONE);
                resend_OTP.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    public void verifayOtp(String mobileNo,String OTP){

        ProgressDialog progressDialog = new ProgressDialog(VerificationCode.this);
        progressDialog.setMessage("Verification Your Otp");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.verifyOtp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.d("Ranjeet_verfyOtp",response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("status");




                    if(message.equals("OK")){

                        JSONObject jsonobject1= jsonObject.getJSONObject("user_details");

                        String user_id = jsonobject1.getString("user_id");
                        String name = jsonobject1.getString("name");
                        String email = jsonobject1.getString("email");
                        String mobile = jsonobject1.getString("mobile");

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                user_id, mobile, email, name, "password"
                        );

                        SharedPrefManager.getInstance(VerificationCode.this).userLogin(login_modelClass);


                        Toast.makeText(VerificationCode.this, "Verification Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(VerificationCode.this,MainActivity.class);
                        startActivity(intent);

                    }else if(message.equals("NOK")){

                        String message1 = jsonObject.getString("message");

                        Toast.makeText(VerificationCode.this, message1, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();

                error.printStackTrace();
                Toast.makeText(VerificationCode.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("mobile",mobileNo);
                params.put("otp",OTP);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(VerificationCode.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    public void resendotp(String mobileNo){

        ProgressDialog progressDialog = new ProgressDialog(VerificationCode.this);
        progressDialog.setMessage("Otp Sent Please Wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.resendOtp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("status");

                    if(message.equals("OK")){

                        Toast.makeText(VerificationCode.this, "OTP Sent Successfully", Toast.LENGTH_SHORT).show();

                         userOTP = jsonObject.getString("otp");

                        //otp_view.setOTP(otp);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(VerificationCode.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("mobile",mobileNo);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(VerificationCode.this);
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }
}