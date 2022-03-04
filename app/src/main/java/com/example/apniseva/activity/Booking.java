package com.example.apniseva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apniseva.R;
import com.example.apniseva.SharedPreference;
import com.example.apniseva.adapter.BookingAdapter;
import com.example.apniseva.modelclass.CartItem;

import java.util.ArrayList;

public class Booking extends AppCompatActivity {

    ArrayList<CartItem> servicesItem = new ArrayList<>();
    SharedPreference sharedPreference;
    BookingAdapter bookingAdapter;
    Button btn_payonvisit, btn_paynow,btn_AddAddress;
    RecyclerView recyclerBooking;
    TextView subTotalPrice, TotalPrice,edit_Address;
    String str_TotalPrice, str_name, str_mobileno, str_workingcity, str_address;

    String[] Working_city = {"--Select Working_city--", "Bhubaneswar", "Cuttack", "Puri"};
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
        TotalPrice.setText(str_TotalPrice +"("+"GST included"+")");

        servicesItem = sharedPreference.loadFavorites(Booking.this);

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

                    Toast.makeText(Booking.this, "Add PersonalDetails", Toast.LENGTH_SHORT).show();

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

        Spinner Workingcity = dialog.findViewById(R.id.Workingcity);
        EditText edit_fullname = dialog.findViewById(R.id.edit_fullname);
        EditText edit_MobileNumber = dialog.findViewById(R.id.edit_MobileNumber);
        EditText edit_CompleteAddress = dialog.findViewById(R.id.edit_CompleteAddress);
        Button btn_SaveAddress = dialog.findViewById(R.id.btn_SaveAddress);

        ArrayAdapter WorkingCityadapter = new ArrayAdapter(this, R.layout.spinneritem, Working_city);
        WorkingCityadapter.setDropDownViewResource(R.layout.spinnerdropdownitem);
        Workingcity.setAdapter(WorkingCityadapter);
        Workingcity.setSelection(-1, true);

        btn_SaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Workingcity.getSelectedItem().toString().equals("--Select Working_city--")) {

                    Toast.makeText(Booking.this, "Please Select Your City", Toast.LENGTH_SHORT).show();

                }else if(edit_fullname.getText().toString().trim().equals("")){

                    edit_fullname.setError("fiil the field");

                }else if(edit_MobileNumber.getText().toString().trim().equals("")){

                    edit_MobileNumber.setError("fiil the field");

                }else if(edit_CompleteAddress.getText().toString().trim().equals("")){

                    edit_CompleteAddress.setError("fiil the field");

                } else{

                    str_name = edit_fullname.getText().toString().trim();
                    str_mobileno = edit_MobileNumber.getText().toString().trim();
                    str_address = edit_CompleteAddress.getText().toString().trim();
                    str_workingcity =  Workingcity.getSelectedItem().toString();

                    edit_Address.setText(str_name+"\n"+str_mobileno+"\n"+str_workingcity+"\n"+str_address);

                    dialog.dismiss();

                }

            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

    }
}