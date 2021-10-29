package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.esafeafrica.esafe.Model.Contact;
import com.esafeafrica.esafe.Model.Numbers;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.getIntents.callIntent;
import static com.esafeafrica.esafe.Config.getIntents.sendEmail;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class contactSelected extends DialogFragment {
    private Button call, email;
    private Context context;

    public contactSelected() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment contactSelected.
     */
    // TODO: Rename and change types and number of parameters
    public static contactSelected newInstance() {
        contactSelected fragment = new contactSelected();
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
        View rootview = inflater.inflate(R.layout.fragment_contact__selected, container, false);
        InitViews(rootview);
        return rootview;
    }

    private void InitViews(View view) {
        context = view.getContext();
        call = view.findViewById(R.id.con_sel_call);
        email = view.findViewById(R.id.con_sel_email);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callIntent(context, getNumbers().getPhone());
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail(context, getNumbers().getEmail());
            }
        });

    }

    private String TelNo() {
        String no = "";
        if (getContact().getPhone() == null || getNumbers().getPhone() != null) {
            no = getNumbers().getPhone();
            return no;
        } else {
            no = getContact().getPhone();
            return no;
        }
    }

    private String EmailAdd() {
        String no = "info";
        if (getContact().getEmail() != null) {
            no = getContact().getEmail();
        } else {
            no = getNumbers().getEmail();
        }
        return no;
    }

    private Contact getContact() {
        Contact contact = new Contact();
        if (getArguments() != null) {
            contact = getArguments().getParcelable("contact");
        }
        return contact;
    }

    private Numbers getNumbers() {
        Numbers numbers = new Numbers();
        if (getArguments() != null) {
            numbers = getArguments().getParcelable("number");
        }
        return numbers;
    }


}
