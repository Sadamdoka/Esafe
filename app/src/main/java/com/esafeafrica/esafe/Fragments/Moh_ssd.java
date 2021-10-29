package com.esafeafrica.esafe.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.General_Actions.startWebView;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class Moh_ssd extends Fragment {

    private WebView web;

    public Moh_ssd() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_moh_ssd, container, false);
        Init(view);
        return view;
    }
    private void Init(View view){
        web=view.findViewById(R.id.webmoh);
        //web.loadUrl("");
        startWebView(getActivity(),"https://health.go.ug",web);
    }
}
