package com.esafeafrica.esafe.Config;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;


import com.esafeafrica.esafe.Country_picker.Utils;
import com.esafeafrica.esafe.R;

import java.io.IOException;
import java.util.Currency;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by sadam on 03/05/18.
 * Copyright 2018 Biashara
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class General_Actions {
    Context context;


    public static void sendNotfication(Context context, String title, String det) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_safe)
                .setContentTitle(title)
                .setPriority(Notification.PRIORITY_MAX)
                .setContentText(det);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //NotificationManager.notify();
        notificationManager.notify(001, builder.build());


    }

    public static String getAddress(Context context, String lati, String longi) throws IOException {

        List<Address> addresses;
        //Custom Maps
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        double lat = Double.valueOf(lati);
        double lon = Double.valueOf(longi);
        // LatLng latLng = new LatLng(lat, lon);
        addresses = geocoder.getFromLocation(lat, lon, 1);
        String address = addresses.get(0).getAddressLine(0);
        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String country = addresses.get(0).getCountryName();
        String postal = addresses.get(0).getPostalCode();
        String name = addresses.get(0).getFeatureName();

        String all = address + "," + city + "," + name;
        return all;
    }

    //Getting Country ISO code by region from phone
    public String getIso(Context context) {
        String country = Utils.getCountryRegionFromPhone(context);
        return country;
    }

    public Set<Currency> getAllCurrencies(Context context) {
        Set<Currency> toret = new HashSet<Currency>();
        Locale[] locales = Locale.getAvailableLocales();
        for (Locale loc : locales) {
            try {
                Currency currency = Currency.getInstance(loc);
                if (currency != null) {
                    toret.add(currency);
                }
            } catch (Exception e) {
                //Local Not found
                Toast.makeText(context, "Local Not found", Toast.LENGTH_SHORT).show();
            }
        }
        return toret;
    }


    public static void startWebView(Context context,String url,WebView web) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        web.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        //progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        web.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        web.loadUrl(url);


    }
}
