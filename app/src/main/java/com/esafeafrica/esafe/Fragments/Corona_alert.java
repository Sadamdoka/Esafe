package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.MainActivity;
import com.esafeafrica.esafe.Model.Corona;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.R;
import com.google.android.material.textfield.TextInputLayout;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.DataError;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.General_Actions.sendNotfication;


public class Corona_alert  extends DialogFragment {
    MainActivity mainActivity;
    private Context context;
    private ApiInterface apiInterface;
    private TextView english,luganda,travel,ankore,swahili,comon,other,person;
    private Button alert;
    private Spinner place;
    private TextInputEditText names,tel;
    private TextInputLayout name_lay,tel_lay;
    private CheckBox me,fever,tired,cough,breath,sore,pain,nose,move;


    public Corona_alert() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.corona_alert, container, false);
        init(rootview);
        return rootview;
    }

    private void init(View v){
        alert=v.findViewById(R.id.vir_alert);
        names=v.findViewById(R.id.vir_name);
        tel=v.findViewById(R.id.vir_tel);
        me=v.findViewById(R.id.vir_me);
        fever=v.findViewById(R.id.vir_fever);
        tired=v.findViewById(R.id.vir_tired);
        cough=v.findViewById(R.id.vir_cough);
        breath=v.findViewById(R.id.vir_breath);
        sore=v.findViewById(R.id.vir_sore);
        pain=v.findViewById(R.id.vir_pain);
        nose=v.findViewById(R.id.vir_nose);
        english=v.findViewById(R.id.vir_eng);
        person=v.findViewById(R.id.vir_persona);
        comon=v.findViewById(R.id.vir_com);
        other=v.findViewById(R.id.vir_others);
        english=v.findViewById(R.id.vir_eng);
        swahili=v.findViewById(R.id.vir_swahili);
        luganda=v.findViewById(R.id.vir_lug);
        name_lay=v.findViewById(R.id.vir_name_lay);
        tel_lay=v.findViewById(R.id.vir_tel_lay);
        move=v.findViewById(R.id.vir_travel_recent);
        travel=v.findViewById(R.id.vir_travel);
        place=v.findViewById(R.id.vir_place);


        setDetails();
        loadSpinner(place);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(move.isChecked()) {
                    place.setVisibility(View.VISIBLE);
                }else{
                    place.setVisibility(View.GONE);
                }
            }
        });

        me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (me.isChecked()){
                    names.setText("");
                    tel.setText("");
                }else{
                    names.setText(getCorona().getName());
                    tel.setText(getCorona().getNumber());
                }
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("Person","Names of Suspect Case","Number","No me!","Have you travelled recently","Yes","Common Sypmtoms","Fever","Tiredness","Coughs","Other Symptoms","Breathing Issues","Sore Throat","Aches Pains","Runny Nose,Diarrhoea,Nausea","Alert Authorities");
            }
        });

        luganda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("Omuntu","Amanya go yo","Namba yo","Si nze!","Otambudeko ebweru we gwanga","Iye","Obubonero Obwabulijo","Omusuja","Obukowu","Ekifuba Ekikalu","Obubonero Obulala","Obuzibu Mu kusa","Amabwa mu bulago","Obulumi Mubiri","Senyiga ayika,Okudukana","Tegeza Gavumenti");
            }
        });

        swahili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setLanguage("Mtu","Jina la Muntu","Nambari","Si mimi!","Je! Umesafiri hivi karibuni","Indio","Dalili za Kawaida","Homa","Uchovu","Kikohoza Kavu","Dalili Nyingine","Maswala ya Kupumua","Kidonda cha Koo","Maafa ya Ache","Senyiga,Kuhara","Mamlaka ya Kuonya");
            }
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coronaAlert(setCorona());
            }
        });
    }


    private String getSpinner(CheckBox checkBox,Spinner spinner){
        String r="0";
        if(checkBox.isChecked()){
            return spinner.getSelectedItem().toString();
        }else{
            return r;
        }
    }
    private void setDetails(){
        names.setText(getCorona().getName());
        tel.setText(getCorona().getNumber());
       }

    private Corona setCorona(){
        String na="",te="";
        if(!names.getText().toString().equals("") && !tel.getText().toString().equals("")){
            na=names.getText().toString();
            te=tel.getText().toString();
        }else{
            na=getCorona().getName();
            te=getCorona().getNumber();
        }
        Corona corona=new Corona(null,null,check(breath),check(cough),check(fever),getCorona().getLati(),getCorona().getLongi(),na,check(nose),te,check(pain),check(sore),check(tired),check(move),getSpinner(move,place));
        return corona;
    }


    private void loadSpinner(Spinner spinner){
        List<String> items=new ArrayList<>();
        items.add("China");
        items.add("Italy");
        items.add("Spain");
        items.add("USA");
        items.add("UAE");
        items.add("Germany");
        items.add("U.K");
        items.add("Iran");
        items.add("Switzerland");
        items.add("Turkey");
        items.add("Belgium");
        items.add("Netherlands");
        items.add("South Korea");
        items.add("Austria");
        items.add("Canada");
        items.add("France");

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,items);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }
    private String check(CheckBox checkBox){
        String result="0";
        if(checkBox.isChecked()){
            result="1";
            return result;
        }
        return result;
    }

    private void setLanguage(String persona,String name,String no,String mes,String trav,String dec,String common,String fevers,String tireds,String coughs,String others,String breaths,String sores,String pains,String noses,String alerts){
        person.setText(persona);
        name_lay.setHint(name);
        tel_lay.setHint(no);
        me.setText(mes);
        travel.setText(trav);
        move.setText(dec);
        comon.setText(common);
        fever.setText(fevers);
        tired.setText(tireds);
        cough.setText(coughs);
        other.setText(others);
        breath.setText(breaths);
        sore.setText(sores);
        pain.setText(pains);
        nose.setText(noses);
        alert.setText(alerts);
    }



    private Corona getCorona() {
        Corona corona = new Corona();
        if (getArguments() != null) {
            corona = getArguments().getParcelable("corona");
            return corona;
        }
        return corona;
    }


    private void coronaAlert(Corona cor){
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, cor.getName());
        RequestBody latireq=RequestBody.create(MultipartBody.FORM,cor.getLati());
        RequestBody longreq=RequestBody.create(MultipartBody.FORM,cor.getLongi());
        RequestBody telreq=RequestBody.create(MultipartBody.FORM,cor.getNumber());
        RequestBody painreq=RequestBody.create(MultipartBody.FORM,cor.getPain());
        RequestBody travelreq=RequestBody.create(MultipartBody.FORM,cor.getTravel());
        RequestBody werereq=RequestBody.create(MultipartBody.FORM,cor.getWere());
        RequestBody fevreq=RequestBody.create(MultipartBody.FORM,cor.getFever());
        RequestBody tirereq=RequestBody.create(MultipartBody.FORM,cor.getTired());
        RequestBody coughreq=RequestBody.create(MultipartBody.FORM,cor.getCough());
        RequestBody breathreq=RequestBody.create(MultipartBody.FORM,cor.getBreath());
        RequestBody sorereq=RequestBody.create(MultipartBody.FORM,cor.getSore());
        RequestBody nosereq=RequestBody.create(MultipartBody.FORM,cor.getNose());
        Call<Feedback> call=apiInterface.createCorona(namereq,latireq,longreq,telreq,travelreq,werereq,painreq,fevreq,coughreq,sorereq,nosereq,tirereq,breathreq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        sendNotfication(getContext(), cor.getName(), "Alert Sent to the Authorities Thank you!");
                        //Toast.makeText(context, "Alert Sent", Toast.LENGTH_SHORT).show();
                        dismiss();
                        Log.d("Success", "Client Success");
                    } else {
                        Log.d("Error", feedback.getErrorMsg());
                        DataError(getActivity(), getLayoutInflater());
                    }
                } else {
                    Log.d("Error", "Error Server");
                    ServerError(getActivity(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }


}
