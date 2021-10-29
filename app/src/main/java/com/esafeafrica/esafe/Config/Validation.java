package com.esafeafrica.esafe.Config;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.AnyRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.esafeafrica.esafe.Fragments.CustomInfo;
import com.esafeafrica.esafe.Model.Amnesty;
import com.esafeafrica.esafe.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class Validation {

    public static boolean isFieldValid(String name) {
        return !TextUtils.isEmpty(name);
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isPhoneValid(String phone) {
        Pattern pattern = Patterns.PHONE;
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean matchPassword(String a, String b) {
        return a.matches(b);
    }

    public static double toDouble(String s) {
        double res = Double.valueOf(s);
        return res;
    }

    //Check password with minimum requirement here(it should be minimum 6 characters)
    public static boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }


    public static byte[] ConvertBytes(Resources res, @DrawableRes int resource) {
        Drawable drawable = res.getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public static String getUri(Context context,Resources res, @DrawableRes int resource){
        Drawable drawable = res.getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        String path= MediaStore.Images.Media.insertImage(context.getContentResolver(),bitmap,"Title",null);
        return path;
    }
    public static final Uri getUriToDrawable(@NonNull Context context,
                                             @AnyRes int drawableId) {
        Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
        return imageUri;
    }


    public static byte[] ConvertBitmap(ImageView imageView) {
        //Resources res=Resources.getSystem();
        //Drawable drawable=res.getDrawable(resource);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    public static Drawable getDrawable(Context context, Bitmap bitmap) {
        Drawable drawable = new BitmapDrawable(context.getResources(), bitmap);
        return drawable;
    }

    public static byte[] byteConvert(String bits) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = Base64.decode(bits, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bb = byteArrayOutputStream.toByteArray();
        return bb;
    }


    public static Bitmap ConvertImage(String bits) {
        // byte[] bytes = bits.getBytes();
        //decode base64 string to image
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] imageBytes = Base64.decode(bits, Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        return bmp;
    }

    public static Bitmap ConvertResizeImage(String bits) {
        // byte[] bytes = bits.getBytes();
        //decode base64 string to image
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        byte[] imageBytes = Base64.decode(bits, Base64.DEFAULT);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 3;
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytestream);
        return bitmap;
    }


    public static Bitmap getBitmapFromURL(final String src) {
        Bitmap bitmap;
        Log.d("Src",src);
        class MyAsync extends AsyncTask<Void,Void,Bitmap> {
            @Override
            protected Bitmap doInBackground(Void... voids) {
                try {
                    Log.e("src",src);
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                    Log.e("Bitmap","returned");
                    return myBitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("Exception",e.getMessage());
                    return null;
                }
            }
        }
        return null;
    }

    public static void getBitmapFromURLGlide(final Context context, final LinearLayout lay, String src) {
        final Bitmap bitmap;
        Glide.with(context)
                .asBitmap()
                .load(src)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        lay.setBackground(getDrawable(context, resource));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public static void getBitmapFromURLGlide(final Context context, final CircleImageView lay, String src) {
        final Bitmap bitmap;
        Glide.with(context)
                .asBitmap()
                .load(src)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        lay.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    public static Bitmap getBitmapFromURLMAP(final Context context, String src) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);

        Glide.with(context)
                .asBitmap()
                .load(src)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        //.setBackground(getDrawable(context, resource));
                        markerImage.setImageBitmap(resource);
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
                        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                        marker.buildDrawingCache();
                        resource = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

                        Canvas canvas = new Canvas(resource);
                        marker.draw(canvas);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        return null;
    }

    public static Bitmap createCustomMarker(Context context, Bitmap bitmap) {
        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageBitmap(bitmap);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);
        return bitmap;
    }

    public static void getBitmapFromURLGlide(final Context context, MarkerOptions markerOptions , Amnesty amnesty, GoogleMap map) {
        Log.d("Url Map",amnesty.getPic());
        Glide.with(context)
                .asBitmap()
                .load(amnesty.getPic())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Log.d("Bitmap","resoucre cogted");
                        final LatLng lng = new LatLng(toDouble(amnesty.getLatitude()), toDouble(amnesty.getLongitude()));//Lat & long needs to fixed at API Level
                        //MarkerOptions markerOptions=new MarkerOptions();
                        markerOptions.position(lng)
                                .title(amnesty.getName())
                                .icon(BitmapDescriptorFactory.fromBitmap(createCustomMarker(context,resource)));
                        //map.addMarker(markerOptions);
                        CustomInfo customInfo = new CustomInfo(context);
                        map.setInfoWindowAdapter(customInfo);
                        Marker m = map.addMarker(markerOptions);
                        m.setTag(amnesty);
                        //m.showInfoWindow();
                        //Marker m = map.addMarker(markerOptions);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    //Model Users
    /**
     public static void UserLog(User user) {
     Log.d("Name", user.getName());
     Log.d("Email", user.getEmail());
     Log.d("Phone", user.getUserid());
     //Log.d("Pass",client.getPassword());
     }*/
}
