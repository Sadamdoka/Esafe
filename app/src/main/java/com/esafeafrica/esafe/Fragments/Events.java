package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Adaptors.eventAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Model.Emergency;
import com.esafeafrica.esafe.Model.EmergencyList;
import com.esafeafrica.esafe.Model.EmergencySingle;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.EmptyList;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Events#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Events extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private eventAdaptor adaptor;
    private ArrayList<Emergency> emergencyArrayList;
    private Context context;
    SessionManager session;

    public Events() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Events.
     */
    // TODO: Rename and change types and number of parameters
    public static Events newInstance() {
        Events fragment = new Events();
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
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_events, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        context = getActivity();
        session=new SessionManager(context);
        recyclerView = view.findViewById(R.id.event_list);
        context = getActivity();
        session=new SessionManager(context);
        progressSync = new ProgressSync(context);
        loadItemsList(new Emergency(null,null,setAccount().getPassport(),null,setAccount().getUserid()));
    }

    private UserWorker setAccount(){
        UserWorker account=new UserWorker();
        if (session.isLoggedIn()) {
            HashMap<String, String> userDetails = session.getUserDetails();
            Log.d("User",userDetails.get(SessionManager.KEY_USER_NAME));
            account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));

            Log.d("User",userDetails.get(SessionManager.KEY_USER_USERID));
        } else {
            Toast.makeText(getContext(),"Not User signed in", Toast.LENGTH_SHORT).show();
        }
        return account;
    }

    private void loadItemsList(Emergency emergency) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<EmergencyList> emergencyListCall = apiInterface.getEmergencyList("0","null",emergency.getUserid(),emergency.getPassport(),"null");
        emergencyListCall.enqueue(new Callback<EmergencyList>() {
            @Override
            public void onResponse(Call<EmergencyList> call, Response<EmergencyList> response) {
                if (response.isSuccessful()) {
                    progressSync.hideDialog();
                    if(response.body() != null){
                    emergencyArrayList = response.body().getEmergency();
                    //progressSync.hideDialog();

                    adaptor = new eventAdaptor(getActivity(), emergencyArrayList, new eventAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Emergency emergency) {
                            Log.d("Item Clicked", emergency.getName());
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                }else {
                        recyclerView.setVisibility(View.GONE);
                        EmptyList(getContext(),getLayoutInflater());
                    }
                } else {
                    progressSync.hideDialog();
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Events", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EmergencyList> call, Throwable t) {
                loadSingleEmergency(new Emergency(null,null,setAccount().getPassport(),null,setAccount().getUserid()));
                progressSync.hideDialog();
                //t.printStackTrace();
                //Log.d("Error",t.toString());
                //NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }

    private void loadSingleEmergency(Emergency emergency){
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<EmergencySingle> call=apiInterface.getEmergencySingle("0","null",emergency.getUserid(),emergency.getPassport(),"null");
        call.enqueue(new Callback<EmergencySingle>() {
            @Override
            public void onResponse(Call<EmergencySingle> call, Response<EmergencySingle> response) {
                if (response.isSuccessful()) {
                    progressSync.hideDialog();
                    if(response.body() != null){
                    EmergencySingle emergencySingle=response.body();
                    emergencyArrayList=new ArrayList<Emergency>();
                    emergencyArrayList.add(emergencySingle.getEmergency());
                    adaptor = new eventAdaptor(getActivity(), emergencyArrayList, new eventAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Emergency emergency) {
                            Log.d("Item Clicked", emergency.getName());
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                } else {
                        recyclerView.setVisibility(View.GONE);
                        EmptyList(getContext(),getLayoutInflater());
                    }
                }else {
                    progressSync.hideDialog();
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Events", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<EmergencySingle> call, Throwable t) {
                //t.printStackTrace();
               // Log.d("Error",t.toString());
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }

}
