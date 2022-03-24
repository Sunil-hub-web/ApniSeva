package com.in.apniseva.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.in.apniseva.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Service extends AppCompatActivity {

    public static TextView textName;
    String name;
    BottomNavigationView bottomNavigation;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acservice_repair);

        Window window = Service.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(Service.this, R.color.white));

        /*textName = findViewById(R.id.textName);
        bottomNavigation = findViewById(R.id.bottomNavigation);

        textName.setText("AC Service & Repair");

        getSupportFragmentManager().beginTransaction().replace(R.id.fram,new Ac_Service_Repair_1()).commit();

        Intent intent = getIntent();
        name = intent.getStringExtra("UserProfile");

        if(name != null){

            if (name.equals("UserProfile")) {

                textName.setText("EditProfile");

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                EditProfile editProfile = new EditProfile();
                ft.replace(R.id.fram, editProfile, "testID");
                ft.commit();

            } else if (name.equals("BookingDetails")) {

                textName.setText("Booking");

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                BookingDetails bookingDetails = new BookingDetails();
                ft.replace(R.id.fram, bookingDetails, "testID1");
                ft.commit();

            }else if(name.equals("AcServices")){

                textName.setText("AC Service & Repair");

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Ac_Service_Repair_1 ac_service_repair_1 = new Ac_Service_Repair_1();
                ft.replace(R.id.fram, ac_service_repair_1, "testID2");
                ft.commit();

            }

        }*//*else{

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Ac_Service_Repair_1 ac_service_repair_1 = new Ac_Service_Repair_1();
            ft.replace(R.id.fram, ac_service_repair_1, "testID2");
            ft.commit();
        }*//*

        bottomNavigation.setSelectedItemId(R.id.home);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedFragment = null;

                switch (item.getItemId()){

                    case R.id.userprofile:

                        startActivity(new Intent(getApplicationContext(), UserDetails.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:

                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;


                }

                //getSupportFragmentManager().beginTransaction().replace(R.id.fram,selectedFragment).commit();

                return true;
            }
        });*/

    }
}