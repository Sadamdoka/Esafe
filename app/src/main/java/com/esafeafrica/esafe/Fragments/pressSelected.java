package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.esafeafrica.esafe.Model.Press;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.Validation.ConvertImage;
import static com.esafeafrica.esafe.Config.Validation.getDrawable;


public class pressSelected extends DialogFragment {

    private LinearLayout linearLayout;
    private Context context;
    private TextView address,dates;
    private LinearLayout img1,img2,img3,img4,img5;

    public pressSelected() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.press_selected, container, false);
        InitViews(rootview);
        return rootview;
    }

    private void InitViews(View view) {
        context = view.getContext();
        address=view.findViewById(R.id.sel_press_address);
        dates=view.findViewById(R.id.sel_press_date);
        img1=view.findViewById(R.id.sel_press_img1);
        img2=view.findViewById(R.id.sel_press_img2);
        img3=view.findViewById(R.id.sel_press_img3);
        img4=view.findViewById(R.id.sel_press_img4);
        img5=view.findViewById(R.id.sel_press_img5);
        setValues(getPress());
    }
    private void setValues(Press press){
        address.setText(press.getAddress());
        dates.setText(press.getDateAddress());
        img1.setBackground(getDrawable(context, ConvertImage(press.getPic1())));
        img2.setBackground(getDrawable(context, ConvertImage(press.getPic2())));
        img3.setBackground(getDrawable(context, ConvertImage(press.getPic3())));
        img4.setBackground(getDrawable(context, ConvertImage(press.getPic4())));
        img5.setBackground(getDrawable(context, ConvertImage(press.getPic5())));

    }

    private  Press getPress(){
        Press press=new Press();
        if (getArguments() != null) {
            press = getArguments().getParcelable("press");
            return press;
        }
        return press;
    }
}
