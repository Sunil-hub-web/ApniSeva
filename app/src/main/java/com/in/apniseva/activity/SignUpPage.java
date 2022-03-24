package com.in.apniseva.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpPage extends AppCompatActivity {

    Button btn_signup;
    TextView text_SignIn,TermsofService;
    EditText edit_fullname, edit_MobileNumber, edit_Email, edit_Password;
    String str_fullname, str_MobileNumber, str_Email, str_Password;
    boolean passwordVisiable;

    AwesomeValidation awesomeValidation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        Window window = SignUpPage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(SignUpPage.this, R.color.white));

        edit_Password = findViewById(R.id.edit_Password);
        edit_fullname = findViewById(R.id.edit_fullname);
        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        edit_Email = findViewById(R.id.edit_Email);
        text_SignIn = findViewById(R.id.text_SignIn);
        btn_signup = findViewById(R.id.btn_signup);
        TermsofService = findViewById(R.id.TermsofService);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(SignUpPage.this, R.id.edit_fullname, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.entername);
        awesomeValidation.addValidation(SignUpPage.this, R.id.edit_MobileNumber, "^[+]?[0-9]{10}$", R.string.entercontact);
        awesomeValidation.addValidation(SignUpPage.this, R.id.edit_Email, Patterns.EMAIL_ADDRESS, R.string.enteremail);
        awesomeValidation.addValidation(SignUpPage.this, R.id.edit_Password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", R.string.enterpassword);

        edit_Password.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (event.getRawX() >= edit_Password.getRight() - edit_Password.getCompoundDrawables()[Right].getBounds().width()) {

                        int selection = edit_Password.getSelectionEnd();
                        if (passwordVisiable) {

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password, 0, R.drawable.visibility, 0);
                            edit_Password.setCompoundDrawablePadding(30);
                            // for show Password
                            edit_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisiable = false;

                        } else {

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password, 0, R.drawable.visibility, 0);
                            edit_Password.setCompoundDrawablePadding(30);
                            // for show Password
                            edit_Password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisiable = true;
                        }

                        edit_Password.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                str_fullname = edit_fullname.getText().toString().trim();
                str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                str_Password = edit_Password.getText().toString().trim();
                str_Email = edit_Email.getText().toString().trim();


                if (awesomeValidation.validate()) {

                    //Toast.makeText(SignUpPage.this, "Success", Toast.LENGTH_SHORT).show();

                    userRegister(str_fullname, str_MobileNumber, str_Email, str_Password);

                } else {

                    Toast.makeText(SignUpPage.this, "Failure", Toast.LENGTH_SHORT).show();
                }
            }
        });

        text_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        TermsofService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUpPage.this, TermsofService.class);
                startActivity(intent);

            }
        });

    }

    public void userRegister(String userName, String mobileNo, String email, String password) {

        ProgressDialog progressDialog = new ProgressDialog(SignUpPage.this);
        progressDialog.setMessage("Register Please wait...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("name", userName);
            jsonObject.put("email", email);
            jsonObject.put("mobile", mobileNo);
            jsonObject.put("password", password);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userRegister, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();

                try {

                    String message = response.getString("status");

                    if (message.equals("NOK")) {

                        String message1 = response.getString("message");

                        Toast.makeText(SignUpPage.this, message1, Toast.LENGTH_SHORT).show();

                    }else if(message.equals("OK")){

                        Toast.makeText(SignUpPage.this, "User Register Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(SignUpPage.this, LoginPage.class);
                        startActivity(intent1);

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
                            Toast.makeText(SignUpPage.this, data, Toast.LENGTH_SHORT).show();

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
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(SignUpPage.this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}