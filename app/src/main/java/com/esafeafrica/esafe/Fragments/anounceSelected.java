package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.esafeafrica.esafe.Model.Anounce;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.Validation.ConvertImage;
import static com.esafeafrica.esafe.Config.Validation.getDrawable;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link anounceSelected#newInstance} factory method to
 * create an instance of this fragment.
 */
public class anounceSelected extends DialogFragment {
    private TextView nam, alert, det, dat;
    private LinearLayout img;
    private Context context;

    public anounceSelected() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment anounceSelected.
     */
    // TODO: Rename and change types and number of parameters
    public static anounceSelected newInstance() {
        anounceSelected fragment = new anounceSelected();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_anounce_selected, container, false);
        InitViews(rootview);
        return rootview;
    }

    private void InitViews(View view) {
        context = view.getContext();
        nam = view.findViewById(R.id.an_sel_nam);
        alert = view.findViewById(R.id.an_sel_alert);
        det = view.findViewById(R.id.an_sel_det);
        dat = view.findViewById(R.id.an_sel_date);
        img = view.findViewById(R.id.an_sel_img);
        setValues(getAnounce());
    }

    private void setValues(Anounce anounce) {
        img.setBackground(getDrawable(context, ConvertImage(anounce.getPic())));
        nam.setText(anounce.getName());
        alert.setText(anounce.getTitle());
        det.setText(anounce.getDetails());
        dat.setText(anounce.getDatereg());
    }

    private Anounce getAnounce() {
        Anounce anounce = new Anounce();
        if (getArguments() != null) {
            anounce = getArguments().getParcelable("anounce");
            return anounce;
        }
        return anounce;
    }

}
