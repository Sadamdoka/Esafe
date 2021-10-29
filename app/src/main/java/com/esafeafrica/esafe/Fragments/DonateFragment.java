package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.esafeafrica.esafe.Adaptors.donateAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Model.Donate;
import com.esafeafrica.esafe.Model.DonateList;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.getIntents.sendEmail;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class DonateFragment extends Fragment {
    private RecyclerView recyclerView;
    private Button button;
    private ProgressSync progressSync;
    private ArrayList<Donate> donateArrayList;
    private ApiInterface apiInterface;
    private donateAdaptor adaptor;
    private Context context;

    public DonateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_donate, container, false);
        Init(view);
        return view;
    }
    private void Init(View view){
        button=view.findViewById(R.id.don_contact);
        recyclerView=view.findViewById(R.id.don_list);
        context=getActivity();
        loadDonate();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(getActivity(),"info@moh-rss.org");
            }
        });
    }

    private void loadDonate(){
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<DonateList> call=apiInterface.allDonate();
        call.enqueue(new Callback<DonateList>() {
            @Override
            public void onResponse(Call<DonateList> call, Response<DonateList> response) {
                if (response.isSuccessful()) {
                    donateArrayList = response.body().getDonate();
                    Log.d("Item Clicked", donateArrayList.get(1).getNames());
                    //progressSync.hideDialog();
                    adaptor = new donateAdaptor(getActivity(),donateArrayList, new donateAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Donate donate) {
                            Log.d("Item Clicked",donate.getNames());
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                } else {
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Events", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DonateList> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }
}
