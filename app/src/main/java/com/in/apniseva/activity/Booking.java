package com.in.apniseva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SharedPreference;
import com.in.apniseva.adapter.BookingAdapter;
import com.in.apniseva.adapter.CategorySpinerAdapter;
import com.in.apniseva.modelclass.CartItem;
import com.in.apniseva.modelclass.CategoryDetails_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Booking extends AppCompatActivity {

    ArrayList<CartItem> servicesItem = new ArrayList<>();
    SharedPreference sharedPreference;
    BookingAdapter bookingAdapter;
    Button btn_payonvisit, btn_paynow,btn_AddAddress;
    RecyclerView recyclerBooking;
    TextView subTotalPrice, TotalPrice,edit_Address;
    String str_TotalPrice, str_name, str_mobileno, str_workingcity, str_address;
    Spinner Workingcity;

    //String[] Working_city = {"--Select Working_city--", "Bhubaneswar", "Cuttack", "Puri"};

    ArrayList<CategoryDetails_model> Working_city = new ArrayList<>();
    ImageView imageviewback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_order);

        subTotalPrice = findViewById(R.id.subTotalPrice);
        TotalPrice = findViewById(R.id.TotalPrice);
        recyclerBooking = findViewById(R.id.recyclerBooking);
        btn_payonvisit = findViewById(R.id.btn_payonvisit);
        //btn_paynow = findViewById(R.id.btn_paynow);
        btn_AddAddress = findViewById(R.id.btn_AddAddress);
        edit_Address = findViewById(R.id.edit_Address);
        imageviewback = findViewById(R.id.back);
        imageviewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sharedPreference = new SharedPreference();

        Intent intent = getIntent();
        str_TotalPrice = intent.getStringExtra("total_price");

        subTotalPrice.setText(str_TotalPrice);
        TotalPrice.setText(str_TotalPrice);

        servicesItem.clear();
        // servicesItem = sharedPreference.loadFavorites(Booking.this);

            SharedPreferences sharedPreferences = getSharedPreferences("shared preferences1", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("task list1", null);
            Type type = new TypeToken<ArrayList<CartItem>>() {}.getType();
            servicesItem = gson.fromJson(json, type);


        Log.d("arraylistfind",servicesItem.toString());

        if (servicesItem.size() != 0) {

            bookingAdapter = new BookingAdapter(Booking.this, servicesItem);
            recyclerBooking.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerBooking.setItemAnimator(new DefaultItemAnimator());
            recyclerBooking.setAdapter(bookingAdapter);

        } else {

            Toast.makeText(this, "No data Found", Toast.LENGTH_SHORT).show();
        }


        btn_payonvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edit_Address.getText().toString().trim().equals("")){

                    Toast.makeText(Booking.this, "  Add PersonalDetails  ", Toast.LENGTH_SHORT).show();

                }else{

                    Intent intent1 = new Intent(Booking.this, BillingDetails.class);
                    intent1.putExtra("name", str_name);
                    intent1.putExtra("mobileno", str_mobileno);
                    intent1.putExtra("address", str_address);
                    intent1.putExtra("workingcity",str_workingcity);
                    intent1.putExtra("subtotal", str_TotalPrice);
                    startActivity(intent1);
                }

            }
        });

        btn_AddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personaldetails();
            }
        });


    }

    public void personaldetails() {

        Dialog dialog = new Dialog(Booking.this);
        dialog.setContentView(R.layout.activity_booking);
        //dialog.setCancelable(false);

        Workingcity = dialog.findViewById(R.id.Workingcity);
        EditText edit_fullname = dialog.findViewById(R.id.edit_fullname);
        EditText edit_MobileNumber = dialog.findViewById(R.id.edit_MobileNumber);
        EditText edit_CompleteAddress = dialog.findViewById(R.id.edit_CompleteAddress);
        Button btn_SaveAddress = dialog.findViewById(R.id.btn_SaveAddress);

        /*ArrayAdapter WorkingCityadapter = new ArrayAdapter(this, R.layout.spinneritem, Working_city);
        WorkingCityadapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        Workingcity.setAdapter(WorkingCityadapter);
        Workingcity.setSelection(-1, true);*/

        getCity();

        Workingcity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategoryDetails_model cate_data = (CategoryDetails_model) parent.getSelectedItem();

                String city_Id = cate_data.getId();
                str_workingcity = cate_data.getName();
                Log.d("city_Id", str_workingcity);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Workingcity.getSelectedItem().toString().equals("--Select Working_city--")) {

                    Toast.makeText(Booking.this, "Please Select Your City", Toast.LENGTH_SHORT).show();

                }else if(edit_fullname.getText().toString().trim().equals("")){

                    Toast.makeText(Booking.this, "Fill The Fields", Toast.LENGTH_SHORT).show();

                }else if(edit_MobileNumber.getText().toString().trim().equals("")){

                    Toast.makeText(Booking.this, "Fill The Fields", Toast.LENGTH_SHORT).show();

                }else if(edit_CompleteAddress.getText().toString().trim().equals("")){

                    Toast.makeText(Booking.this, "Fill The Fields", Toast.LENGTH_SHORT).show();

                } else{

                    str_name = edit_fullname.getText().toString().trim();
                    str_mobileno = edit_MobileNumber.getText().toString().trim();
                    str_address = edit_CompleteAddress.getText().toString().trim();
                    //str_workingcity =  Workingcity.getSelectedItem().toString();

                    edit_Address.setText(str_name+"\n"+str_mobileno+"\n"+str_workingcity+"\n"+str_address);

                    dialog.dismiss();

                }

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }

    public void getCity(){

        Working_city.clear();

        ProgressDialog progressDialog = new ProgressDialog(Booking.this);
        progressDialog.setMessage("City Please wait...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, AppUrl.getmastercity, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if(status.equals("OK")){

                        String city = jsonObject.getString("city");
                        JSONArray jsonArray_city = new JSONArray(city);

                        for(int i=0; i<jsonArray_city.length(); i++){

                            JSONObject jsonObject_City = jsonArray_city.getJSONObject(i);

                            String id = jsonObject_City.getString("id");
                            String city_name = jsonObject_City.getString("city_name");

                            CategoryDetails_model categoryDetails_model = new CategoryDetails_model(

                                    city_name,id
                            );

                            Working_city.add(categoryDetails_model);

                        }

                        CategorySpinerAdapter Working_city_adapter = new CategorySpinerAdapter(Booking.this,R.layout.spinnerdropdownitem,Working_city);
                        Working_city_adapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
                        Workingcity.setAdapter(Working_city_adapter);
                        Workingcity.setSelection(-1,true);
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
                Toast.makeText(Booking.this, "API no response, Facing Technical issues, Try again!", Toast.LENGTH_SHORT).show();

            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(3000,1,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(Booking.this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(Booking.this,SubCategoryPriceDetails.class);
        startActivity(intent);
    }
}