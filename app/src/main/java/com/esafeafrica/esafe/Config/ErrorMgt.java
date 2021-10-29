package com.esafeafrica.esafe.Config;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.AnyRes;
import androidx.annotation.NonNull;

import com.esafeafrica.esafe.R;


public class ErrorMgt {
    private static TextView error, tagerr;
    private static LinearLayout custom;
    private static ImageView mage;

    private static void InitView(View view) {
        error = view.findViewById(R.id.err);
        tagerr = view.findViewById(R.id.errtag);
        mage = view.findViewById(R.id.errimage);
        custom = view.findViewById(R.id.cust_lay);
    }

    public static void NoInternet(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Check your Internet Connection");
        tagerr.setText("Please contact Your Internet Provider!");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void Aboutapp(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        custom.setBackground(context.getResources().getDrawable(R.drawable.about_border));
        //custom.setBackground(R.drawable.side_nav_bar);
        error.setTextColor(context.getResources().getColor(R.color.white));
        tagerr.setTextColor(context.getResources().getColor(R.color.white));
        error.setText("Official  Ueara App");
        tagerr.setText("This App is Monitor & track labourers abroad! \n Version 1.0");
        mage.setImageResource(R.drawable.web);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void Developer(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Developed by Wakaima Labs");
        tagerr.setText("info@wakaimalabs.com\n+256 751 073507 / 785 438035");
        mage.setImageResource(R.drawable.waka);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void NotActive(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("No Active");
        tagerr.setText("Item not yet enabled.\n Thanks!");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public static void EmptyList(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("No Items");
        tagerr.setText("No items to show");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public static void ServerError(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Server Encountered Error");
        tagerr.setText("Please Contact Us or Try again");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void DataError(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Information Error");
        tagerr.setText("Please check your details");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public static void IncorrectDetails(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Wrong ID/Passport Details");
        tagerr.setText("Please check your details");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void SuccessAccount(Context context, LayoutInflater inflater, String email) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Account Created:" + email);
        tagerr.setText("Will be activated with 24 hours");
        mage.setImageResource(R.drawable.ic_account_circle_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ActivateEmail(Context context, LayoutInflater inflater, String email) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Check this email:" + email);
        tagerr.setText("Activation Link Sent");
        mage.setImageResource(R.drawable.ic_email_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void CheckerTerms(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("Must Agree to terms & Condition");
        tagerr.setText("Please see bottom left of screen");
        mage.setImageResource(R.drawable.ic_error_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void ZeroReturn(Context context, LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.custom_error, null);
        InitView(view);
        error.setText("No Results");
        tagerr.setText("There is no data to be shown");
        mage.setImageResource(R.drawable.ic_warning_black_24dp);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    public static String NullString(String x){
        if(x != null){
            return  x;
        }
        return "N/A";
    }

    public static boolean checkSpinner(Spinner spinner){
        return spinner.getSelectedItem() == null;
    }

    public static  boolean checkImage(Context context, ImageButton view){
        if(view.getDrawable() != null){
            return true;
        }
        Toast.makeText(context,"Select an Image",Toast.LENGTH_SHORT).show();
        return  false;
    }

    public static boolean checkUri(Uri uri){
        return uri != null;
    }



}
