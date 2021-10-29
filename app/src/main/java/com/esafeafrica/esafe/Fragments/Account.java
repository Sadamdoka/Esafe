package com.esafeafrica.esafe.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.esafeafrica.esafe.Adaptors.mainFragmentAdaptor;
import com.esafeafrica.esafe.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class Account extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    public Account() {
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
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        InitViews(root);
        return root;
    }

    public void InitViews(View item) {
        ViewPager viewPager = item.findViewById(R.id.accountpager);
        mainFragmentAdaptor pagerAdapter = new mainFragmentAdaptor(getParentFragmentManager());
        //pagerAdapter.addFragmet(new MgAccount());
        pagerAdapter.addFragmet(new Contacts());
        pagerAdapter.addFragmet(new AmnestyNo());
        viewPager.setAdapter(pagerAdapter);
    }


}
