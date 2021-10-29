package com.esafeafrica.esafe.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Model.Cases;
import com.esafeafrica.esafe.Model.CasesSingle;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.ZeroReturn;


/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CasesFragment extends Fragment {

    SessionManager session;
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private TextView covidcase,recovery,death,active,last;
    public CasesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cases, container, false);
        Init(view);
        return view;
    }

    private void Init(View view){
        session=new SessionManager(getContext());
        covidcase=view.findViewById(R.id.covid_case);
        recovery=view.findViewById(R.id.covid_reco);
        death=view.findViewById(R.id.covid_dead);
        active=view.findViewById(R.id.covid_act);
        //last=view.findViewById(R.id.covid_last);
        getCases();

    }

    private void setValues(Cases cases){
        covidcase.setText(cases.getCase());
        recovery.setText(cases.getRecovery());
        death.setText(cases.getDead());
        active.setText(String.valueOf(Integer.parseInt(cases.getCase())-Integer.parseInt(cases.getRecovery())));
       // last.setText(cases.getLastUpdate());

    }

    private UserWorker setAccount(){
        UserWorker account=new UserWorker();
        if (session.isLoggedIn()) {
            HashMap<String, String> userDetails = session.getUserDetails();
            Log.d("User",userDetails.get(SessionManager.KEY_USER_NAME));
            account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));

        } else {
            Toast.makeText(getContext(),"Not User signed in", Toast.LENGTH_SHORT).show();
        }
        return account;
    }
    private void getCases(){
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<CasesSingle> singleCall= apiInterface.getCases("1");
        singleCall.enqueue(new Callback<CasesSingle>() {
            @Override
            public void onResponse(Call<CasesSingle> call, Response<CasesSingle> response) {
                if (response.isSuccessful()){
                    CasesSingle casesSingle=response.body();
                    if(casesSingle.getCases().getCase() != null){
                        setValues(casesSingle.getCases());
                    }else {
                        ZeroReturn(getContext(), getLayoutInflater());
                    }
                }else{
                    ServerError(getContext(), getLayoutInflater());
                }
            }

            @Override
            public void onFailure(Call<CasesSingle> call, Throwable t) {

            }
        });

    }
}
