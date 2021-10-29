package com.esafeafrica.esafe.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;

import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.General_Actions.startWebView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link About#newInstance} factory method to
 * create an instance of this fragment.
 */
public class About extends Fragment {

    private WebView web;
    public About() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment About.
     */
    // TODO: Rename and change types and number of parameters
    public static About newInstance() {
        About fragment = new About();
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
        View v =inflater.inflate(R.layout.fragment_about, container, false);
        //Init(v);
        return v;
    }



    private void Init(View view){
        web=view.findViewById(R.id.webprevent);
        startWebView(getActivity(),"https://www.uaera.org/",web);
    }

}
