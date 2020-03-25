package com.example.deliveryboy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionManager {

    private SharedPreferences sharedPreferences;


    public boolean card;

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean isCard() {
        return card;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public SessionManager(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    public void setTokens(String token){
        sharedPreferences.edit().putString("token",token).commit();
    }
    public String getTokens(){

        return  sharedPreferences.getString("token","");
    }
    public String getID() {
        String lat = sharedPreferences.getString("ID","");

        return lat;
    }

    public void setID(String ID) {
        sharedPreferences.edit().putString("ID",ID).commit();
    }

}
