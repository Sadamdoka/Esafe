package com.esafeafrica.esafe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;


import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.Notification.FCM_instance;

import java.util.HashMap;

public class Welcome extends AppCompatActivity {

    private static final String TAG = "Welcome";
    private static final int Splash_time_out = 500;
    FCM_instance fcm_instance;
    SessionManager session;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        InitViews();
    }

    public void InitViews() {
        fcm_instance = new FCM_instance();
        session = new SessionManager(getApplicationContext());
        fcm_instance.subscribeTopicBasic(getApplicationContext());
        fcm_instance.Subscribe(getApplicationContext());
        fcm_instance.getToken(getApplicationContext());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPref();
            }
        }, Splash_time_out);

    }

    public void SharedPref() {
        session.checkLogin();
        if (!session.isLoggedIn()) {
            gotoLogin();
        } else {
            HashMap<String, String> userDetails = session.getUserDetails();
            UserWorker account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));

            Log.d("JOB",account.getJobtype());
            //Log.d("email",user.getEmail());
            gotoMain(account);
        }

    }

    public void gotoLogin() {
        Intent intent = new Intent(Welcome.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void gotoMain(UserWorker userWorker) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
       // intent.putExtra("account", userWorker);
        startActivity(intent);
        finish();
    }


}
