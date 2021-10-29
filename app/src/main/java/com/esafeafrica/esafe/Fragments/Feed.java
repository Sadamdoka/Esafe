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
public class Feed extends Fragment {

    private WebView web;
    public Feed() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feed, container, false);
        Init(view);
        return view;
    }

    private void Init(View view){
         web=view.findViewById(R.id.webfeed);
        startWebView(getActivity(),"https://twitter.com/esafeafrica",web);
    }



    /**
     // Open previous opened link from history on webview when back button pressed

     //@Override
     // Detect when the back button is pressed
     public void onBackPressed() {
     if(web.canGoBack()) {
     web.goBack();
     } else {
     // Let the system handle the back button
     super.onBackPressed();
     }
     }
     */
}
