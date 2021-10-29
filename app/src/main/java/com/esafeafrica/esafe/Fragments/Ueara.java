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
public class Ueara extends Fragment {


    private WebView web;
    public Ueara() {
        // Required empty public constructor
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
        View v= inflater.inflate(R.layout.fragment_ueara, container, false);
        Init(v);
        return v;
    }



    private void Init(View view){
        web=view.findViewById(R.id.webur);
        startWebView(getActivity(),"https://www.esafeafrica.com/",web);
    }
}