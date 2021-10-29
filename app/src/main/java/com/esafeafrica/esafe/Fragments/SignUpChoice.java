package com.esafeafrica.esafe.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esafeafrica.esafe.Adaptors.mainFragmentAdaptor;
import com.esafeafrica.esafe.Image_picker.Create_account;
import com.esafeafrica.esafe.Login;
import com.esafeafrica.esafe.Model.Emergency;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.getIntents.callIntent;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class SignUpChoice extends Fragment {

    private Button guest,contact,signup,emp;
    public SignUpChoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sign_up_choice, container, false);
        InitView(view);
        return view;
    }
    public  void InitView(View view){
        guest=view.findViewById(R.id.ch_btn_guest);
        contact=view.findViewById(R.id.ch_btn_contact);
        signup=view.findViewById(R.id.ch_btn_worker);
        emp=view.findViewById(R.id.ch_btn_employer);

        emp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertChooser(new Emergency("Employer"));
            }
        });

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callIntent(getActivity(),"+256751072507");
            }
        });

        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertChooser(new Emergency());
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSignUp();
            }
        });

    }

    private void signUp(){
        Signup sign=new Signup();
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.ch_parent, sign)
                .commit();

    }

    public void gotoSignUp() {
        Intent intent = new Intent(getContext(), Create_account.class);
       // intent.putExtra("account", account);
        startActivity(intent);
    }

    private void alertChooser(Emergency emergency) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("emergency", emergency);
        FragmentManager manager = getParentFragmentManager();
        alertChooser selected = new alertChooser();
        selected.setArguments(bundle);
        selected.show(manager, "emergency");
    }
}