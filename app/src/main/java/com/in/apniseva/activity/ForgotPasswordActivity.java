package com.in.apniseva.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    EditText edit_MobileNumber, edit_Password, edit_ConfirmPassword;
    String str_MobileNumber, str_Password, str_ConfirmPassword;
    Button btn_PasswordUpdate;
    AwesomeValidation awesomeValidation;
    boolean passwordVisiable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        edit_Password = findViewById(R.id.edit_Password);
        edit_ConfirmPassword = findViewById(R.id.edit_ConfirmPassword);
        btn_PasswordUpdate = findViewById(R.id.btn_PasswordUpdate);

        str_MobileNumber = getIntent().getStringExtra("mobileNo");
        edit_MobileNumber.setText(str_MobileNumber);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(ForgotPasswordActivity.this, R.id.edit_Password, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$", R.string.enterpassword);

        edit_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (edit_Password.getText().toString().trim().equals("")) {

                    edit_Password.setBackgroundTintList(ForgotPasswordActivity.this.getResources().getColorStateList(R.color.text));
                    Drawable img = edit_Password.getContext().getResources().getDrawable(R.drawable.password);
                    Drawable img1 = edit_Password.getContext().getResources().getDrawable(R.drawable.visibility1);
                    edit_Password.setCompoundDrawablesWithIntrinsicBounds(img, null, img1, null);
                    edit_Password.setCompoundDrawablePadding(25);


                } else {

                    edit_Password.setBackgroundTintList(ForgotPasswordActivity.this.getResources().getColorStateList(R.color.Blue));
                    edit_Password.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password1, 0, R.drawable.visibility, 0);
                    edit_Password.setCompoundDrawablePadding(25);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            // for show Password
                            edit_Password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisiable = false;

                        } else {

                            //set Drawable Image here
                            edit_Password.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
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

        edit_ConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (edit_ConfirmPassword.getText().toString().trim().equals("")) {

                    edit_ConfirmPassword.setBackgroundTintList(ForgotPasswordActivity.this.getResources().getColorStateList(R.color.text));
                    Drawable img = edit_ConfirmPassword.getContext().getResources().getDrawable(R.drawable.password);
                    Drawable img1 = edit_ConfirmPassword.getContext().getResources().getDrawable(R.drawable.visibility1);
                    edit_ConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(img, null, img1, null);
                    edit_ConfirmPassword.setCompoundDrawablePadding(25);


                } else {

                    edit_ConfirmPassword.setBackgroundTintList(ForgotPasswordActivity.this.getResources().getColorStateList(R.color.Blue));
                    edit_ConfirmPassword.setCompoundDrawablesWithIntrinsicBounds(R.drawable.password1, 0, R.drawable.visibility, 0);
                    edit_ConfirmPassword.setCompoundDrawablePadding(25);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_ConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int Right = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    if (event.getRawX() >= edit_ConfirmPassword.getRight() - edit_ConfirmPassword.getCompoundDrawables()[Right].getBounds().width()) {

                        int selection = edit_Password.getSelectionEnd();
                        if (passwordVisiable) {

                            //set Drawable Image here
                            edit_ConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            // for show Password
                            edit_ConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisiable = false;

                        } else {

                            //set Drawable Image here
                            edit_ConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.visibility, 0);
                            // for show Password
                            edit_ConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisiable = true;
                        }

                        edit_ConfirmPassword.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });

        btn_PasswordUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(awesomeValidation.validate()){

                   if(edit_Password.getText().toString().trim().equals(edit_ConfirmPassword.getText().toString().trim())){


                       str_Password = edit_Password.getText().toString().trim();
                       str_ConfirmPassword = edit_ConfirmPassword.getText().toString().trim();

                       passUpdate("update_pass",str_MobileNumber,str_Password);

                   }else{

                       edit_ConfirmPassword.setError("Password not Match");
                   }

               }else {

                   Toast.makeText(ForgotPasswordActivity.this, "Enter Valide Password", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    public void passUpdate(String type, String MobileNO, String Password) {

        ProgressDialog progressDialog = new ProgressDialog(ForgotPasswordActivity.this);
        progressDialog.setMessage("Password Update Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.forgotPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equals("OK")) {

                        String message = jsonObject.getString("msg");
                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgotPasswordActivity.this,LoginPage.class);
                        startActivity(intent);

                    } else if (status.equalsIgnoreCase("NOK")) {

                        String message = jsonObject.getString("msg");
                        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(ForgotPasswordActivity.this, data, Toast.LENGTH_SHORT).show();

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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("type",type);
                params.put("mobile",MobileNO);
                params.put("pass",Password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ForgotPasswordActivity.this);
        requestQueue.add(stringRequest);
    }
}