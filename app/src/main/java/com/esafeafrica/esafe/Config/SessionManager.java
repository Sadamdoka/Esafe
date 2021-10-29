package com.esafeafrica.esafe.Config;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;


import com.esafeafrica.esafe.Login;

import java.util.HashMap;

public class SessionManager {
    // Sharedpref file name
    private static final String PREF_NAME = "Safe";
    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    // Shared Preferences
    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;
    //User Details.
    public static final String KEY_USER_ID = "_id";
    public static final String KEY_USER_USERID = "_userid";
    public static final String KEY_USER_NIN = "_nin";
    public static final String KEY_USER_NAME = "_name";
    public static final String KEY_USER_PASSPORT = "_passport";
    public static final String KEY_USER_ADDRESS = "_address";
    public static final String KEY_USER_EMAIL = "_email";
    public static final String KEY_USER_PHONE= "_phone";
    public static final String KEY_USER_GENDER = "_gender";
    public static final String KEY_USER_MARITAL = "_marital";
    public static final String KEY_USER_NATIONALITY = "_nationality";
    public static final String KEY_USER_DOB = "_dob";
    public static final String KEY_USER_POB = "_pob";
    public static final String KEY_USER_VILLAGE = "_village";
    public static final String KEY_USER_PARISH= "_parish";
    public static final String KEY_USER_SUBCOUNTY = "_subcounty";
    public static final String KEY_USER_DISTRICT = "_district";
    public static final String KEY_USER_JOBTYPE = "_jobtype";
    public static final String KEY_USER_LCOMPANY = "_lcompany";
    public static final String KEY_USER_FCOMPANY = "_fcompany";
    public static final String KEY_USER_LOCATION = "_loca";
    public static final String KEY_USER_LATI = "_lati";
    public static final String KEY_USER_LONGI = "_longi";
    public static final String KEY_USER_PASSPIC = "_passpic";
    public static final String KEY_USER_NPIC = "_npic";
    public static final String KEY_USER_PIC = "_pic";
    public static final String KEY_USER_FPIC = "_fpic";
    public static final String KEY_USER_CODE = "_code";
    public static final String KEY_USER_BANK = "_bank";
    public static final String KEY_USER_BANK_ACCOUNT = "_bank_account";
    public static final String KEY_USER_KIN = "_kin";
    public static final String KEY_USER_KIN_NUMBER = "_kin_no";
    public static final String KEY_USER_EX_PHONE = "_exphone";


    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(String _id,String _userid,String _nin,String _name,String _passport,String _address,String _email,String _phone,String _gender, String _marital ,String _nationality,String _dob,String _pob,String _village,String _parish,String _subcounty,String _district,String _jobtype,String _lcompnay,String _fcompany,String _location,String _lati,String _longi,String _ptpass,String _npic,String _pic,String _fpic,String _code,String _bank,String _bank_acc,String _kin,String _kin_no,String _ex_phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
//Storing  UserData
        editor.putString(KEY_USER_ID,_id);
        editor.putString(KEY_USER_USERID,_userid);
        editor.putString(KEY_USER_NIN,_nin);
        editor.putString(KEY_USER_NAME,_name);
        editor.putString(KEY_USER_PASSPORT,_passport);
        editor.putString(KEY_USER_ADDRESS,_address);
        editor.putString(KEY_USER_EMAIL,_email);
        editor.putString(KEY_USER_PHONE,_phone);
        editor.putString(KEY_USER_GENDER,_gender);
        editor.putString(KEY_USER_MARITAL,_marital);
        editor.putString(KEY_USER_NATIONALITY,_nationality);
        editor.putString(KEY_USER_DOB,_dob);
        editor.putString(KEY_USER_POB,_pob);
        editor.putString(KEY_USER_VILLAGE,_village);
        editor.putString(KEY_USER_PARISH,_parish);
        editor.putString(KEY_USER_SUBCOUNTY,_subcounty);
        editor.putString(KEY_USER_DISTRICT,_district);
        editor.putString(KEY_USER_JOBTYPE,_jobtype);
        editor.putString(KEY_USER_LCOMPANY,_lcompnay);
        editor.putString(KEY_USER_FCOMPANY,_fcompany);
        editor.putString(KEY_USER_LOCATION,_location);
        editor.putString(KEY_USER_LATI,_lati);
        editor.putString(KEY_USER_LONGI,_longi);
        editor.putString(KEY_USER_PASSPIC,_ptpass);
        editor.putString(KEY_USER_NPIC,_npic);
        editor.putString(KEY_USER_PIC,_pic);
        editor.putString(KEY_USER_FPIC,_fpic);
        editor.putString(KEY_USER_CODE,_code);
        editor.putString(KEY_USER_BANK,_bank);
        editor.putString(KEY_USER_BANK_ACCOUNT,_bank_acc);
        editor.putString(KEY_USER_KIN,_kin);
        editor.putString(KEY_USER_KIN_NUMBER,_kin_no);
        editor.putString(KEY_USER_EX_PHONE,_ex_phone);
        // commit changes
        editor.commit();
    }



    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));
        user.put(KEY_USER_USERID, pref.getString(KEY_USER_USERID, null));
        user.put(KEY_USER_NIN, pref.getString(KEY_USER_NIN, null));
        user.put(KEY_USER_NAME, pref.getString(KEY_USER_NAME, null));
        user.put(KEY_USER_PASSPORT, pref.getString(KEY_USER_PASSPORT, null));
        user.put(KEY_USER_ADDRESS, pref.getString(KEY_USER_ADDRESS, null));
        user.put(KEY_USER_EMAIL, pref.getString(KEY_USER_EMAIL, null));
        user.put(KEY_USER_PHONE, pref.getString(KEY_USER_PHONE, null));
        user.put(KEY_USER_GENDER, pref.getString(KEY_USER_GENDER, null));
        user.put(KEY_USER_MARITAL, pref.getString(KEY_USER_MARITAL, null));
        user.put(KEY_USER_NATIONALITY, pref.getString(KEY_USER_NATIONALITY, null));
        user.put(KEY_USER_DOB, pref.getString(KEY_USER_DOB, null));
        user.put(KEY_USER_POB, pref.getString(KEY_USER_POB, null));
        user.put(KEY_USER_VILLAGE, pref.getString(KEY_USER_VILLAGE, null));
        user.put(KEY_USER_PARISH, pref.getString(KEY_USER_PARISH, null));
        user.put(KEY_USER_SUBCOUNTY, pref.getString(KEY_USER_SUBCOUNTY, null));
        user.put(KEY_USER_DISTRICT, pref.getString(KEY_USER_DISTRICT, null));
        user.put(KEY_USER_JOBTYPE, pref.getString(KEY_USER_JOBTYPE, null));
        user.put(KEY_USER_LCOMPANY, pref.getString(KEY_USER_LCOMPANY, null));
        user.put(KEY_USER_FCOMPANY, pref.getString(KEY_USER_FCOMPANY, null));
        user.put(KEY_USER_LOCATION, pref.getString(KEY_USER_LOCATION, null));
        user.put(KEY_USER_LATI, pref.getString(KEY_USER_LATI, null));
        user.put(KEY_USER_LONGI, pref.getString(KEY_USER_LONGI, null));
        user.put(KEY_USER_PASSPIC, pref.getString(KEY_USER_PASSPIC, null));
        user.put(KEY_USER_NPIC, pref.getString(KEY_USER_NPIC, null));
        user.put(KEY_USER_PIC, pref.getString(KEY_USER_PIC, null));
        user.put(KEY_USER_FPIC, pref.getString(KEY_USER_FPIC, null));
        user.put(KEY_USER_CODE, pref.getString(KEY_USER_CODE, null));
        user.put(KEY_USER_BANK, pref.getString(KEY_USER_BANK, null));
        user.put(KEY_USER_BANK_ACCOUNT, pref.getString(KEY_USER_BANK_ACCOUNT, null));
        user.put(KEY_USER_KIN, pref.getString(KEY_USER_KIN, null));
        user.put(KEY_USER_KIN_NUMBER, pref.getString(KEY_USER_KIN_NUMBER, null));
        user.put(KEY_USER_EX_PHONE, pref.getString(KEY_USER_EX_PHONE, null));

        // return user
        return user;
    }


    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();


        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}
