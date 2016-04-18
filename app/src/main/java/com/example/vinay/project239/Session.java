package com.example.vinay.project239;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Vinay on 11/27/2015.
 */
public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String email) {
        prefs.edit().putString("email", email).commit();
        //prefsCommit();
    }

    public String getusename() {
        String usename = prefs.getString("email","email");
        return usename;
    }
}