package com.esafeafrica.esafe.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.esafeafrica.esafe.R;

public class FCM_instance {

    private static final String TAG = "MyFirebaseIdService";

    public void subscribeTopicBasic(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = context.getString(R.string.default_notification_channel_id);
            String channelName = context.getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        // if (context.getExtras() != null) {
        //    for (String key : context.getIntent().getExtras().keySet()) {
        //       Object value = context.getIntent().getExtras().get(key);
        //      Log.d(TAG, "Key: " + key + " Value: " + value);
        // }
        //}
        // [END handle_data_extras]

    }

    public void Subscribe(Context context) {
        Log.d(TAG, "Subscribing to weather topic");
        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic("news")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = context.getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = context.getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                        // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END subscribe_topics]
    }

    public void getToken(Context context) {
        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        // Log and toast
                        String msg = context.getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }

}
