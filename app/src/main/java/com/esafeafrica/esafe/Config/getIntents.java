package com.esafeafrica.esafe.Config;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;


public class getIntents {


    public static void callIntent(Context context, String phone) {
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        context.startActivity(callIntent);
    }

    public static void sendSMS(Context context, String phone, String msg) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse(phone));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "01234");
        smsIntent.putExtra("sms_body", "Test ");

        try {
            context.startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "SMS failed, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendEmail(Context context, String email) {
        Log.i("Send email", "");
        String[] TO = {"sadamdoka@gmail.com"};
        String[] CC = {"sadamdoka@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse(email));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "FeedBack");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public static void sendEmailVolu(Context context, String email) {
        Log.i("Send email", "");
        String[] TO = {"sadamdoka@gmail.com"};
        String[] CC = {"sadamdoka@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse(email));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I WANT TO VOLUNTEER");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here,Attach your bio-data");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void gotoLink(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

}
