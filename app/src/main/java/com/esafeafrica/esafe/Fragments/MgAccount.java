package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.SessionManager;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.Model.UserWorker;
import com.esafeafrica.esafe.R;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.IncorrectDetails;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.SuccessAccount;
import static com.esafeafrica.esafe.Config.Validation.ConvertImage;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MgAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MgAccount extends Fragment {
    private ApiInterface apiInterface;
    private EditText userid, name, mail, tel, passport, nin;
    private ImageView code;
    private Button edit;
    private ImageButton enable;
    private Context context;
    private String id;

    SessionManager session;

    public MgAccount() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MgAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static MgAccount newInstance() {
        MgAccount fragment = new MgAccount();
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
        View rootview = inflater.inflate(R.layout.fragment_mg_account, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        context = view.getContext();
        session=new SessionManager(context);
        userid = view.findViewById(R.id.edit_acc_id);
        name = view.findViewById(R.id.edit_acc_name);
        mail = view.findViewById(R.id.edit_acc_email);
        tel = view.findViewById(R.id.edit_acc_tel);
        nin = view.findViewById(R.id.edit_acc_nin);
        code = view.findViewById(R.id.edit_qrcode);
        passport = view.findViewById(R.id.edit_acc_passport);
        edit = view.findViewById(R.id.btn_editaccount);
        enable = view.findViewById(R.id.img_btn_edit_acc);
        setValues();
        enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //enableStaff();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editAccount(new Account());
            }
        });

    }
    private void setValues(){
        userid.setText(setAccount().getUserid());
        name.setText(setAccount().getNames());
        mail.setText(setAccount().getEmail());
        tel.setText(setAccount().getPhone());
        passport.setText(setAccount().getPassport());
        nin.setText(setAccount().getNin());
        code.setImageBitmap(ConvertImage(setAccount().getCode()));
    }
    private UserWorker setAccount(){
        UserWorker account=new UserWorker();
        if (session.isLoggedIn()) {
            HashMap<String, String> userDetails = session.getUserDetails();
            Log.d("User",userDetails.get(SessionManager.KEY_USER_NAME));
           // account=new Account(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_LATI),userDetails.get(SessionManager.KEY_USER_LCOMPANY),userDetails.get(SessionManager.KEY_USER_LOCATION),userDetails.get(SessionManager.KEY_USER_LONGI),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_PHONE),null,userDetails.get(SessionManager.KEY_USER_USERID));
             account=new UserWorker(null,userDetails.get(SessionManager.KEY_USER_ID),null,userDetails.get(SessionManager.KEY_USER_ADDRESS),userDetails.get(SessionManager.KEY_USER_BANK),userDetails.get(SessionManager.KEY_USER_BANK_ACCOUNT),userDetails.get(SessionManager.KEY_USER_CODE),userDetails.get(SessionManager.KEY_USER_DISTRICT),userDetails.get(SessionManager.KEY_USER_DOB),userDetails.get(SessionManager.KEY_USER_EMAIL),userDetails.get(SessionManager.KEY_USER_EX_PHONE),userDetails.get(SessionManager.KEY_USER_FCOMPANY),userDetails.get(SessionManager.KEY_USER_FPIC),userDetails.get(SessionManager.KEY_USER_GENDER),userDetails.get(SessionManager.KEY_USER_JOBTYPE),userDetails.get(SessionManager.KEY_USER_KIN),userDetails.get(SessionManager.KEY_USER_KIN_NUMBER),null,userDetails.get(SessionManager.KEY_USER_LCOMPANY),null,null,userDetails.get(SessionManager.KEY_USER_MARITAL),userDetails.get(SessionManager.KEY_USER_NAME),userDetails.get(SessionManager.KEY_USER_NATIONALITY),userDetails.get(SessionManager.KEY_USER_NIN),userDetails.get(SessionManager.KEY_USER_NPIC),userDetails.get(SessionManager.KEY_USER_PARISH),userDetails.get(SessionManager.KEY_USER_PASSPORT),userDetails.get(SessionManager.KEY_USER_PHONE),userDetails.get(SessionManager.KEY_USER_PIC),userDetails.get(SessionManager.KEY_USER_POB),userDetails.get(SessionManager.KEY_USER_PASSPIC),userDetails.get(SessionManager.KEY_USER_SUBCOUNTY),userDetails.get(SessionManager.KEY_USER_USERID),userDetails.get(SessionManager.KEY_USER_VILLAGE));

        } else {
            Toast.makeText(getContext(),"Not User signed in", Toast.LENGTH_SHORT).show();
        }
        return account;
    }





    private void editAccount(UserWorker account) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody idreq = RequestBody.create(MultipartBody.FORM, account.getId());
        RequestBody userreq = RequestBody.create(MultipartBody.FORM, account.getUserid());
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, account.getNames());
        RequestBody ppreq = RequestBody.create(MultipartBody.FORM, account.getPassport());
        RequestBody ppicreq = RequestBody.create(MultipartBody.FORM, account.getPtpic());
        RequestBody lcreq = RequestBody.create(MultipartBody.FORM, account.getLcompany());
        RequestBody fcreq = RequestBody.create(MultipartBody.FORM, account.getFcompany());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, account.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, account.getPhone());
        //RequestBody passreq = RequestBody.create(MultipartBody.FORM, account.getPassword());
        RequestBody locareq = RequestBody.create(MultipartBody.FORM, account.getLocation());
        RequestBody latireq = RequestBody.create(MultipartBody.FORM, account.getLati());
        RequestBody longreq = RequestBody.create(MultipartBody.FORM, account.getLongi());
        Call<Feedback> call = apiInterface.updateAccount(idreq,userreq, namereq, ppreq,ppicreq,lcreq,fcreq,emailreq, phonereq, null,null,locareq,latireq,longreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        //Log.d("Success", "Client Successs");
                        SuccessAccount(getContext(), getLayoutInflater(), account.getEmail());
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

}
