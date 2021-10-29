package com.esafeafrica.esafe.Notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.esafeafrica.esafe.MainActivity;
import com.esafeafrica.esafe.Model.Notify;
import com.esafeafrica.esafe.R;
import com.esafeafrica.esafe.Welcome;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhi on 13 Nov 2017 013.
 */

public class NotificationUtils {
    private static final int NOTIFICATION_ID = 200;
    private static final String PUSH_NOTIFICATION = "pushNotification";
    private static final String CHANNEL_ID = "myChannel";
    private static final String CHANNEL_NAME = "myChannelName";
    private static final String URL = "url";
    private static final String ACTIVITY = "activity";

    Map<String, Class> activityMap = new HashMap<>();
    private final Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
        //Populate activity map
        activityMap.put("MainActivity", Welcome.class);
        activityMap.put("SecondActivity", MainActivity.class);
    }

    /**
     * Displays notification based on parameters
     *
     * @param notify
     * @param resultIntent
     */
    public void displayNotification(Notify notify, Intent resultIntent) {
        {
            String message = notify.getBody();
            String title = notify.getTitle();
            String action = "action";
            String destination = "action_destination";
            final int icon = R.mipmap.ic_safe;
            Uri sound = Uri.parse("android.resource://"
                    + mContext.getPackageName() + "/"
                    + R.raw.siren);
            PendingIntent resultPendingIntent;
            if (URL.equals(action)) {
                Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destination));

                resultPendingIntent = PendingIntent.getActivity(mContext, 0, notificationIntent, 0);
            } else if (ACTIVITY.equals(action) && activityMap.containsKey(destination)) {
                resultIntent = new Intent(mContext, activityMap.get(destination));

                resultPendingIntent =
                        PendingIntent.getActivity(
                                mContext,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        );
            } else {
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                resultPendingIntent =
                        PendingIntent.getActivity(
                                mContext,
                                0,
                                resultIntent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                        );
            }


            final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext, CHANNEL_ID);

            Notification notification;
            //If Bitmap is created from URL, show big icon
            //NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
            //bigPictureStyle.setBigContentTitle(title);
            //bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
            //bigPictureStyle.bigPicture(iconBitMap);
            notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSmallIcon(R.mipmap.ic_safe)
                    .setContentText(message)
                    .setSound(sound)
                    .build();

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

            //All notifications should go through NotificationChannel on Android 26 & above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                AudioAttributes attributes = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();

                NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT);
                channel.enableLights(true);
                channel.enableVibration(true);
                channel.setSound(sound, attributes);
                notificationManager.createNotificationChannel(channel);

                notificationManager.notify(NOTIFICATION_ID, notification);
            }
        }
    }

    /**
     * Downloads push notification image before displaying it in
     * the notification tray
     *
     * @param strURL : URL of the notification Image
     * @return : BitMap representation of notification Image
     */
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Playing notification sound
     */
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/siren.mp3");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
