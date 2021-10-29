package com.esafeafrica.esafe.Fragments;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.esafeafrica.esafe.Model.Amnesty;
import com.esafeafrica.esafe.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.esafeafrica.esafe.Config.Validation.getBitmapFromURLGlide;


public class CustomInfo implements GoogleMap.InfoWindowAdapter {

    private final Context context;
    private CircleImageView circleImageView;
    private TextView name, number, email, addrres;
    //private MapBean mapBean;


    public CustomInfo(Context context) {
        this.context = context;
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.custome_info, null);
        circleImageView = view.findViewById(R.id.logo);
        name = view.findViewById(R.id.nam);
        //number = view.findViewById(R.id.num);
        //email = view.findViewById(R.id.mail);
        //addrres = view.findViewById(R.id.address);

        Amnesty amnesty = (Amnesty) marker.getTag();
        name.setText(amnesty.getName());
        getBitmapFromURLGlide(context,circleImageView,amnesty.getPic());
        //circleImageView.setImageBitmap(getBitmapFromURLMAP(context,amnesty.getPic()));
        //number.setText(amnesty.());
        //email.setText(amnesty.getEmail());
        //addrres.setText(owners.getAddress());
        return view;
    }
}
