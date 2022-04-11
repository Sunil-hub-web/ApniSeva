package com.in.apniseva.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.facebook.AccessTokenTracker;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoginStatusCallback;
import com.facebook.Profile;
import com.facebook.internal.ImageRequest;
import com.in.apniseva.AppURL.AppUrl;
import com.in.apniseva.R;
import com.in.apniseva.SessionManager;
import com.in.apniseva.SharedPrefManager;
import com.in.apniseva.modelclass.Login_ModelClass;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.regex.Pattern;


public class LoginPage extends AppCompatActivity {

    EditText edit_MobileNumber, edit_Password;
    String str_MobileNumber, str_Password;
    boolean passwordVisiable;
    TextView text_signUp, text_viaotp, text_ForgotPassword;
    Button btn_signin;
    LoginButton rel_fbLogin;

    private static final String EMAIL = "email";
    private static final String TAG = "LoginPage";
    private static final int RC_SIGN_IN = 100;

    AwesomeValidation awesomeValidation;

    SessionManager sessionManager;
    CallbackManager callbackManager;

    GoogleSignInClient mGoogleSignInClient;
    RelativeLayout googlesignInButton, facebooksignInButton;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);

        //logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));

        sessionManager = new SessionManager(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        Window window = LoginPage.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(LoginPage.this, R.color.white));

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        edit_Password = findViewById(R.id.edit_Password);
        edit_MobileNumber = findViewById(R.id.edit_MobileNumber);
        text_signUp = findViewById(R.id.text_signUp);
        btn_signin = findViewById(R.id.btn_signin);
        text_viaotp = findViewById(R.id.text_viaotp);
        text_ForgotPassword = findViewById(R.id.text_ForgotPassword);

        // Set the dimensions of the sign-in button.
        googlesignInButton = findViewById(R.id.signInButton);
        facebooksignInButton = findViewById(R.id.facebooksignInButton);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);

        callbackManager = CallbackManager.Factory.create();


        facebooksignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logInWithReadPermissions(LoginPage.this, Arrays.asList("email", "public_profile"));

                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                // App code

                                AccessToken accessToken = AccessToken.getCurrentAccessToken();
                                boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

                                if(isLoggedIn == true){

                                    Profile profile = Profile.getCurrentProfile();
                                    String id = profile.getName();
                                    setFacebookData(loginResult);

                                }else{

                                    Toast.makeText(LoginPage.this, "Logout", Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onCancel() {
                                // App code

                                Toast.makeText(LoginPage.this, "failed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(FacebookException exception) {
                                // App code

                                Toast.makeText(LoginPage.this, "" + exception, Toast.LENGTH_SHORT).show();

                                Log.d("sunilException", exception.toString());

                            }
                        });


            }
        });

        googlesignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signIn();

                //signOut();
            }
        });

        awesomeValidation.addValidation(LoginPage.this, R.id.edit_MobileNumber, "^[0-9]{10}$", R.string.entercontact);

        edit_MobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (edit_MobileNumber.getText().toString().trim().equals("")) {

                    edit_MobileNumber.setBackgroundTintList(LoginPage.this.getResources().getColorStateList(R.color.text));
                    Drawable img = edit_MobileNumber.getContext().getResources().getDrawable(R.drawable.group85);
                    edit_MobileNumber.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    edit_MobileNumber.setCompoundDrawablePadding(25);


                } else {

                    edit_MobileNumber.setBackgroundTintList(LoginPage.this.getResources().getColorStateList(R.color.Blue));
                    edit_MobileNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.group1102, 0, 0, 0);
                    edit_MobileNumber.setCompoundDrawablePadding(25);

                    if (edit_MobileNumber.getText().toString().trim().length() >= 10) {

                        edit_MobileNumber.setCompoundDrawablesWithIntrinsicBounds(R.drawable.group1102, 0, R.drawable.che, 0);
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edit_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (edit_Password.getText().toString().trim().equals("")) {

                    edit_Password.setBackgroundTintList(LoginPage.this.getResources().getColorStateList(R.color.text));
                    Drawable img = edit_Password.getContext().getResources().getDrawable(R.drawable.password);
                    Drawable img1 = edit_Password.getContext().getResources().getDrawable(R.drawable.visibility1);
                    edit_Password.setCompoundDrawablesWithIntrinsicBounds(img, null, img1, null);
                    edit_Password.setCompoundDrawablePadding(25);


                } else {

                    edit_Password.setBackgroundTintList(LoginPage.this.getResources().getColorStateList(R.color.Blue));
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

        text_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_MobileNumber.getText().toString().trim().equals("")) {

                    edit_MobileNumber.setError("Fill The Details");

                } else if (edit_Password.getText().toString().trim().equals("")) {

                    edit_Password.setError("Fill The Details");

                } else {

                    str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                    str_Password = edit_Password.getText().toString().trim();

                    if (str_MobileNumber.matches("^[0-9]*$")) {

                        if (awesomeValidation.validate()) {

                            userlogin(str_MobileNumber, str_Password);
                        } else {

                            edit_MobileNumber.setError("Enter Valid Mobile No");
                        }
                    } else {

                        if (isValidEmail(str_MobileNumber)) {

                            userlogin(str_MobileNumber, str_Password);
                        } else {

                            edit_MobileNumber.setError("Enter Valid EmailId");
                        }
                    }
                }
                //userlogin(str_MobileNumber, str_Password);
            }
        });

        text_viaotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edit_MobileNumber.getText().toString().trim().equals("")) {

                    edit_MobileNumber.setError("Fill The Field");

                } else {

                    str_MobileNumber = edit_MobileNumber.getText().toString().trim();
                    loginviaOTP(str_MobileNumber);

                }


               /* Intent intent = new Intent(LoginPage.this,VerificationCode.class);
                startActivity(intent);*/
            }
        });

        text_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginPage.this, ForGotPassword.class);
                startActivity(intent);
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(LoginPage.this, "Your Account Logout", Toast.LENGTH_SHORT).show();
                        // ...
                    }
                });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void setFacebookData(final LoginResult loginResult) {

        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Response", response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String profileURL = "";

                            edit_MobileNumber.setText(email);

                            userSociallogin(email);

                            if (Profile.getCurrentProfile() != null) {
                                profileURL = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), 400, 400).toString();
                            }

                            //TODO put your code here
                        } catch (JSONException e) {
                            Toast.makeText(LoginPage.this, "error_occurred_try_again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name");
        request.setParameters(parameters);
        request.executeAsync();
    }

    /*public void googlesignin() {

        // Configure sign-in to request the user's ID, email address, and basic
         // profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    *//*    // Check for existing Google Sign In account, if the user is already signed in
         // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);*//*
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    AccessTokenTracker t = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            if (currentAccessToken == null) {

                Toast.makeText(LoginPage.this, "Logout", Toast.LENGTH_SHORT).show();

            } else {

                loaduserProfile(currentAccessToken);
            }
        }
    };

    private void loaduserProfile(AccessToken currentAccessToken) {

        GraphRequest graphRequest = GraphRequest.newMeRequest(currentAccessToken, (object, response) -> {

            if (object != null) {

                try {

                    String email = object.getString("email");
                    edit_MobileNumber.setText(email);

                } catch (JSONException ex) {

                    ex.printStackTrace();

                }
            }
        });
        Bundle parameter = new Bundle();
        parameter.putString("field", "email");
        graphRequest.setParameters(parameter);
        graphRequest.executeAsync();

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(LoginPage.this);
            if (acct != null) {

                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                userSociallogin(personEmail);

            }

            // Signed in successfully, show authenticated UI.
            //updateUI(account);


        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            //updateUI(null);
        }
    }

    public void userlogin(String mobileNo, String password) {

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("User Login Please wait...");
        progressDialog.show();


        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("mobile", mobileNo);
            jsonObject.put("password", password);
            jsonObject.put("type", "manual");

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userLogin, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.d("Ranjeet_login_response", response.toString());

                try {
                    String message = response.getString("status");

                    if (message.equals("NOK")) {

                        String message1 = response.getString("message");
                        Toast.makeText(LoginPage.this, message1, Toast.LENGTH_SHORT).show();

                    } else if (message.equals("OK")) {

                        String user_details = response.getString("user_details");

                        JSONObject jsonObject_userdetails = new JSONObject(user_details);

                        String user_id = jsonObject_userdetails.getString("user_id");
                        String name = jsonObject_userdetails.getString("name");
                        String email = jsonObject_userdetails.getString("email");
                        String mobile = jsonObject_userdetails.getString("mobile");
                        String password = edit_Password.getText().toString().trim();

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                user_id, mobile, email, name, password
                        );

                        SharedPrefManager.getInstance(LoginPage.this).userLogin(login_modelClass);

                        Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(LoginPage.this, MainActivity.class);
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
                            Toast.makeText(LoginPage.this, data, Toast.LENGTH_SHORT).show();

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
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void loginviaOTP(String mobileNo) {

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("Processing...");
        progressDialog.show();

        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("mobile", mobileNo);

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userLoginViaOtp, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.d("Ranjeet_login_responseO", response.toString());

                String message;
                try {
                    message = response.getString("status");
//                    String message1 = response.getString("message");

                    Log.d("Ranjeet_message", message);

                    if (message.equals("NOK")) {

                        Toast.makeText(LoginPage.this, "User does not exists", Toast.LENGTH_SHORT).show();

                    } else if (message.equals("OK")) {

                        String OTP = response.getString("otp");

                        sessionManager.setUserOTP(OTP);
                        sessionManager.setUserMobileNO(mobileNo);

                        Intent intent = new Intent(LoginPage.this, VerificationCode.class);
                        startActivity(intent);

                        Toast.makeText(LoginPage.this, "Otp Send Successfully", Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Ranjeet_Login_otp", e.toString());

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
                Toast.makeText(LoginPage.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void userSociallogin(String email) {

        ProgressDialog progressDialog = new ProgressDialog(LoginPage.this);
        progressDialog.setMessage("User Login Please wait...");
        progressDialog.show();


        JSONObject jsonObject = new JSONObject();

        try {

            jsonObject.put("email", email);
            jsonObject.put("type", "social_login");

        } catch (Exception e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppUrl.userLogin, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                progressDialog.dismiss();
                Log.d("Ranjeet_login_response", response.toString());

                try {
                    String message = response.getString("status");

                    if (message.equals("NOK")) {

                        String message1 = response.getString("message");
                        Toast.makeText(LoginPage.this, message1, Toast.LENGTH_SHORT).show();

                    } else if (message.equals("OK")) {

                        String user_details = response.getString("user_details");

                        JSONObject jsonObject_userdetails = new JSONObject(user_details);

                        String user_id = jsonObject_userdetails.getString("user_id");
                        String name = jsonObject_userdetails.getString("name");
                        String email = jsonObject_userdetails.getString("email");
                        String mobile = jsonObject_userdetails.getString("mobile");

                        edit_MobileNumber.setText(email);

                        sessionManager.setuserEmail(email);
                        sessionManager.setLogin();

                        edit_Password.setText("");

                        Login_ModelClass login_modelClass = new Login_ModelClass(
                                user_id, mobile, email, name, ""
                        );

                        SharedPrefManager.getInstance(LoginPage.this).userLogin(login_modelClass);

                        Toast.makeText(LoginPage.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(LoginPage.this, MainActivity.class);
                        startActivity(intent);

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

                Toast.makeText(LoginPage.this, "" + error, Toast.LENGTH_SHORT).show();

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(LoginPage.this);
        requestQueue.add(jsonObjectRequest);
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    private boolean isValidMobile(String email) {
        Pattern pattern = Patterns.PHONE;
        return pattern.matcher(email).matches();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String email = sessionManager.getUserEmail();

        if (SharedPrefManager.getInstance(LoginPage.this).isLoggedIn()) {

            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);

        } else if (sessionManager.isLogin()) {

            Intent intent = new Intent(LoginPage.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}