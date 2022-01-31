package com.example.apniseva.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apniseva.AppURL.AppUrl;
import com.example.apniseva.R;
import com.example.apniseva.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    ImageView img_back;
    EditText edit_userName,edit_EmailId,edit_MobileNo;
    String str_userName,str_EmailId,str_MobileNo,userid;
    Button btn_Update;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        img_back = findViewById(R.id.img_back);
        edit_userName = findViewById(R.id.edit_userName);
        edit_EmailId = findViewById(R.id.edit_EmailId);
        edit_MobileNo = findViewById(R.id.edit_MobileNo);
        btn_Update = findViewById(R.id.btn_Update);

        userid = SharedPrefManager.getInstance(EditProfile.this).getUser().getUserid();
    try {
    Log.d("Ranjeet_userId", userid);
}catch (Exception e){
    Log.d("Ranjeet_userId", e.toString());

}

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditProfile.this,UserDetails.class);
                startActivity(intent);
            }
        });

        viewProfile(userid);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_userName = edit_userName.getText().toString().trim();
                str_EmailId = edit_EmailId.getText().toString().trim();
                str_MobileNo = edit_MobileNo.getText().toString().trim();

                updateProfile("1",userid,str_userName,str_EmailId,str_MobileNo);

            }
        });
    }

    public void viewProfile(String userId){

        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setMessage("Retrive User Details Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.viewUserProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("status");

                    if(message.equals("ok")){

                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        String mobile = jsonObject.getString("mobile");

                        edit_userName.setText(name);
                        edit_EmailId.setText(email);
                        edit_MobileNo.setText("+91 " + mobile);

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

                Toast.makeText(EditProfile.this, "Your user id not found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("user_id",userId);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        requestQueue.add(stringRequest);

    }

    public void updateProfile(String image,String userid,String name,String email,String mobileno){

        ProgressDialog progressDialog = new ProgressDialog(EditProfile.this);
        progressDialog.setMessage("Update User Details Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.ProfileUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.equals("ok")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(EditProfile.this, message, Toast.LENGTH_SHORT).show();

                        viewProfile(userid);
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

                Toast.makeText(EditProfile.this, "Your user id not found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String,String> params = new HashMap<>();
//                params.put("Image",image);
                params.put("user_id",userid);
                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobileno);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(EditProfile.this);
        requestQueue.add(stringRequest);

    }
}
