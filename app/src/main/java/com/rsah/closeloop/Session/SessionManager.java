package com.rsah.closeloop.Session;

import android.content.Context;
import android.content.SharedPreferences;



import java.util.HashMap;

public class SessionManager {

    // Shared Preferences
    private SharedPreferences pref;

    // Editor for Shared preferences
    private SharedPreferences.Editor editor;

    // Context
    private Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    // Sharedpref file name
    private static final String PREF_NAME = "closeloop";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    public static final String KEY_OBJECT_SISWA = "siswax_";
    public static final String KEY_AMOUNT = "amountx_";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    /**
     * Create login session
     * */
    public void saveUserObjectSiswa( String user){ //storeObjectSiswa

        editor.putString(KEY_OBJECT_SISWA, user);

        editor.commit();
    }


    public void saveAmount( String amount){ //storeObjectSiswa

        editor.putString(KEY_AMOUNT, amount);

        editor.commit();
    }


    public String getInstanceSiswa() {
        return pref.getString(KEY_OBJECT_SISWA, null);
    }
    public String getAmount() {
        return pref.getString(KEY_AMOUNT, null);
    }


    /**
     * Hapus Data Session
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}