package com.in.apniseva;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.in.apniseva.activity.SplashScreen;

public class SessionManager {

    SharedPreferences sharedprefernce;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE=0;

    private static final String PREF_NAME = "sharedcheckLogin";
    private static final String User_OTP = "userotp";
    private static final String USER_EMAIL = "useremail";
    private static final String USER_MOBILENO = "usermobile";
    private static final String CATEGORY_iD = "categoryid";
    private static final String IS_LOGIN="islogin";
    private static final String IS_CATEGORYNAME="categoryname";
    private static final String IS_SUB_CATEGORYID="subcategoryid";
    private static final String IS_Token="token";


    public SessionManager(Context context){

        this.context =  context;
        sharedprefernce = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedprefernce.edit();
    }

    public void setUserOTP(String id ){

        editor.putString(User_OTP,id);
        editor.commit();

    }

    public String getUserOTP(){

        return  sharedprefernce.getString(User_OTP,"DEFAULT");
    }

    public void setUserToken(String token){

        editor.putString(IS_Token,token);
        editor.commit();

    }

    public String getUserToken(){

        return  sharedprefernce.getString(IS_Token,"DEFAULT");
    }

    public void setuserEmail(String email){

        editor.putString(USER_EMAIL,email);
        editor.commit();
    }

    public String getUserEmail(){

        return sharedprefernce.getString(USER_EMAIL,"DEFAULT");
    }

    public void setUserMobileNO(String mobileNO){

        editor.putString(USER_MOBILENO,mobileNO);
        editor.commit();
    }

    public String getUserMobileno(){

        return sharedprefernce.getString(USER_MOBILENO,"DEFAULT");

    }

    public void setCategoryID(String categoryid){

        editor.putString(CATEGORY_iD,categoryid);
        editor.commit();
    }

    public String getCategoryId(){

        return sharedprefernce.getString(CATEGORY_iD,"DEFAULT");

    }

    public void setCategoryName(String categoryName){

        editor.putString(IS_CATEGORYNAME,categoryName);
        editor.commit();
    }

    public String getCategoryName(){

        return sharedprefernce.getString(IS_CATEGORYNAME,"DEFAULT");
    }

    public void setSubcategoryID(String SubcategoryID){

        editor.putString(IS_SUB_CATEGORYID,SubcategoryID);
        editor.commit();
    }

    public String getSubcategoryId(){

        return sharedprefernce.getString(IS_SUB_CATEGORYID,"DEFAULT");
    }



    public Boolean isLogin(){
        return sharedprefernce.getBoolean(IS_LOGIN, false);

    }
    public void setLogin(){

        editor.putBoolean(IS_LOGIN, true);
        editor.commit();

    }

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, SplashScreen.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);





    }

}
