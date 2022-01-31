package com.example.apniseva.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.apniseva.R;
import com.example.apniseva.SessionManager;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class UserDetails extends AppCompatActivity {

    ImageView img_close;
    TextView text_UserProfile, text_BookingDetails, text_PaymentOption,text_Logout;
    ShapeableImageView profile_image;
    public static final int IMAGE_CODE = 1;
    Uri imageUri;
    String profile_photo;

    SessionManager sessionManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        sessionManager = new SessionManager(UserDetails.this);

        Window window = UserDetails.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(UserDetails.this, R.color.Blue));

        img_close = findViewById(R.id.img_close);
        text_UserProfile = findViewById(R.id.text_UserProfile);
        text_BookingDetails = findViewById(R.id.text_BookingDetails);
        //text_PaymentOption = findViewById(R.id.text_PaymentOption);
        profile_image = findViewById(R.id.profile_image);
        text_Logout = findViewById(R.id.text_Logout);

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_CODE);
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, MainActivity.class);
                startActivity(intent);
            }
        });

        text_UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, EditProfile.class);
                intent.putExtra("UserProfile", "UserProfile");
                startActivity(intent);
            }
        });

        /*text_PaymentOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, PaymentOption.class);
                startActivity(intent);
            }
        });*/

        text_BookingDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetails.this, BookingDetails.class);
                intent.putExtra("UserProfile", "BookingDetails");
                startActivity(intent);
            }
        });

        text_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              sessionManager.logoutUser();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {

            imageUri = data.getData();
            profile_image.setImageURI(imageUri);

            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(UserDetails.this.getContentResolver(), imageUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap = Bitmap.createScaledBitmap(bitmap, 500, 750, true);
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos); //bm is the bitmap object
                byte[] img = baos.toByteArray();
                profile_photo = Base64.encodeToString(img, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {

    }
}