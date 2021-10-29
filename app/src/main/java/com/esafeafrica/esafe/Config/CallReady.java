package com.esafeafrica.esafe.Config;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.getIntents.callIntent;
import static com.esafeafrica.esafe.Config.getIntents.sendEmail;


public class CallReady {
    private static LinearLayout parent;
    private static Context context;
    private static ImageButton call, email;

    private static void InitViews(View view, String no, String mail) {
        context = view.getContext();
        parent = view.findViewById(R.id.parent_call);
        call = view.findViewById(R.id.img_call);
        email = view.findViewById(R.id.img_email);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent(context, no);
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(context, mail);
            }
        });
    }

    public static void ContentLoader(Context context, LayoutInflater inflater, String no, String mail) {
        View view = inflater.inflate(R.layout.custom_call, null);
        InitViews(view, no, mail);
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

}
