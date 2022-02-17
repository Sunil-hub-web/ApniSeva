package com.example.apniseva.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.apniseva.AppURL.AppUrl;
import com.example.apniseva.R;
import com.example.apniseva.SharedPrefManager;
import com.example.apniseva.SharedPreference;
import com.example.apniseva.adapter.AddressDetailsAdapter;
import com.example.apniseva.adapter.OrderDetailsAdapter;
import com.example.apniseva.modelclass.AddressDetails_ModelClass;
import com.example.apniseva.modelclass.CartItem;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BillingDetails extends AppCompatActivity implements PaymentResultListener {

    RecyclerView orderDetailsRecycler,addressRecycler;
    OrderDetailsAdapter orderDetailsAdapter;
    AddressDetailsAdapter addressDetailsAdapter;
    ArrayList<CartItem> order = new ArrayList<>();
    ArrayList<AddressDetails_ModelClass> address = new ArrayList<>();
    LinearLayoutManager linearLayoutManager,linearLayoutManager1;
    ImageView imageBack;

    Button btn_paynow;
    String str_TotalPrice,str_name,str_mobileno,str_workingcity,str_address,userid,services_Id,booking_id;
    SharedPreference sharedPreference;

    TextView subTotalPrice,TotalPrice,CompleteAddress,name,mobileno,btn_payvisit;

    ArrayList<String> servicesId = new ArrayList<>();

    int amount;

    private static final String TAG = BillingDetails.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billingdetails);

        orderDetailsRecycler = findViewById(R.id.orderDetailsRecycler);
       // addressRecycler = findViewById(R.id.addressRecycler);
        btn_paynow = findViewById(R.id.btn_paynow);
        btn_payvisit = findViewById(R.id.btn_payvisit);
        subTotalPrice = findViewById(R.id.subTotalPrice);
        TotalPrice = findViewById(R.id.TotalPrice);
        CompleteAddress = findViewById(R.id.CompleteAddress);
        name = findViewById(R.id.name);
        mobileno = findViewById(R.id.mobileno);
        imageBack = findViewById(R.id.back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Window window = BillingDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(BillingDetails.this, R.color.white));

        SharedPreferences sharedPreferences_services = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences_services.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        servicesId = gson.fromJson(json, type);

        Log.d("servicesId1",servicesId.toString());

        StringBuffer sb = new StringBuffer();

        for (String s : servicesId) {

            sb.append(s);
            sb.append(" ");
        }

        services_Id = sb.toString();

        // remove last character (,)
        services_Id = services_Id.substring(0, services_Id.length() -1);

        Log.d("servicesId12",services_Id);

        sharedPreference = new SharedPreference();

        order = sharedPreference.loadFavorites(BillingDetails.this);

        Intent intent = getIntent();

        str_name = intent.getStringExtra("name");
        str_mobileno = intent.getStringExtra("mobileno");
        str_address = intent.getStringExtra("address");
        str_workingcity = intent.getStringExtra("workingcity");
        str_TotalPrice = intent.getStringExtra("subtotal");

        name.setText(str_name);
        TotalPrice.setText(str_TotalPrice +"("+"gst inclucded"+")");
        subTotalPrice.setText(str_TotalPrice);
        mobileno.setText(str_mobileno);
        CompleteAddress.setText(str_address);

        userid = SharedPrefManager.getInstance(BillingDetails.this).getUser().getUserid();

        linearLayoutManager = new LinearLayoutManager(BillingDetails.this,LinearLayoutManager.VERTICAL,false);
        orderDetailsAdapter = new OrderDetailsAdapter(BillingDetails.this,order);
        orderDetailsRecycler.setLayoutManager(linearLayoutManager);
        orderDetailsRecycler.setHasFixedSize(true);
        orderDetailsRecycler.setAdapter(orderDetailsAdapter);


/*
        linearLayoutManager1 = new LinearLayoutManager(BillingDetails.this,LinearLayoutManager.HORIZONTAL,false);
        addressDetailsAdapter = new AddressDetailsAdapter(BillingDetails.this,address);
        addressRecycler.setLayoutManager(linearLayoutManager1);
        addressRecycler.setHasFixedSize(true);

        addressRecycler.setAdapter(addressDetailsAdapter);*/

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billingDetails(userid,str_TotalPrice,str_TotalPrice,str_name,str_mobileno,str_address,str_address,"0","0","PayNow");

            }
        });

        btn_payvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                billingDetails(userid,str_TotalPrice,str_TotalPrice,str_name,str_mobileno,str_address,str_address,"0","0","PayVisit");

            }
        });
         amount = Math.round(Float.parseFloat(str_TotalPrice) * 100);

    }

   /* public void billingDetails(String userid,String total,String subTotal,String name,String mobileNo,
                               String address,String userAddress,String CGST,String IGST){


        ProgressDialog progressDialog = new ProgressDialog(BillingDetails.this);
        progressDialog.setMessage("Update User Details Please wait...");
        progressDialog.show();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppUrl.viewUserProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("true")){

                        String message = jsonObject.getString("message");

                        Toast.makeText(BillingDetails.this, message, Toast.LENGTH_SHORT).show();

                        String booking_id = jsonObject.getString("booking_id");

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

                Toast.makeText(BillingDetails.this, "Your user id not found", Toast.LENGTH_SHORT).show();

            }
        }){

            @Override
            protected Map<String, Object> getParams() throws AuthFailureError {

                Map<String,Object> params = new HashMap<>();

                params.put("user_id",userid);
                params.put("product[0]",servicesId);
                params.put("total",total);
                params.put("subtotal",subTotal);
                params.put("name",name);
                params.put("mobile",mobileNo);
                params.put("address",address);
                params.put("useraddress",userAddress);
                params.put("cgst",CGST);
                params.put("igst",IGST);

                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BillingDetails.this);
        requestQueue.add(stringRequest);

    }*/

    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */
        final Activity activity = this;

        final Checkout co = new Checkout();
        co.setKeyID("rzp_test_zaz75RSgcXbsfA");

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "team.apniseva@gmail.com");
            preFill.put("contact", str_mobileno);

            options.put("prefill", preFill);

            co.open(activity, options);

        } catch (Exception e) {

            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void billingDetails(String userid,String total,String subTotal,String name,String mobileNo,
                               String address,String userAddress,String CGST,String IGST,String paymentmethod) {

        ProgressDialog dialog = new ProgressDialog(BillingDetails.this);
        dialog.setMessage("Book Now Please wait...");
        dialog.show();

        JSONObject jsonObject = new JSONObject();

        try{

            jsonObject.put("user_id",userid);
            jsonObject.put("product",services_Id);
            jsonObject.put("total",total);
            jsonObject.put("subtotal",subTotal);
            jsonObject.put("name",name);
            jsonObject.put("mobile",mobileNo);
            jsonObject.put("address",address);
            jsonObject.put("useraddress",userAddress);
            jsonObject.put("cgst","0");
            jsonObject.put("igst","0");

            Log.d("Ranjeet_input",userid+total+subTotal+name+mobileNo+address+services_Id);
            Log.d("input",services_Id);

        }catch(Exception e){

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userBooking, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                dialog.dismiss();

                try {

                    String success = response.getString("success");

                    if(success.equals("true")){

                        String message = response.getString("message");

                        Toast.makeText(BillingDetails.this, message, Toast.LENGTH_SHORT).show();

                        booking_id = response.getString("booking_id");

                        Intent intent = new Intent(BillingDetails.this,PaymentSuccessFully.class);
                        intent.putExtra("paymentmethod",paymentmethod);
                        intent.putExtra("booking_id",booking_id);
                        startActivity(intent);

                        if(paymentmethod.equals("PayNow")){

                            startPayment();

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Ranjeet",e.toString());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                dialog.dismiss();

                error.printStackTrace();

                Toast.makeText(BillingDetails.this, ""+error, Toast.LENGTH_SHORT).show();

                Log.d("error",error.toString());

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000,3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(BillingDetails.this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {

        try {
            
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(BillingDetails.this,PaymentSuccessFully.class);
            intent.putExtra("paymentmethod","PayNow");
            intent.putExtra("booking_id",booking_id);
            startActivity(intent);
            finish();

        } catch (Exception e) {

            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }


    }

    @Override
    public void onPaymentError(int code , String response) {

        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }

        Toast.makeText(this, response, Toast.LENGTH_LONG).show();

    }
}
