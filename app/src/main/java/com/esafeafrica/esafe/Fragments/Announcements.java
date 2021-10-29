package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.esafe.Adaptors.anounceAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Model.Anounce;
import com.esafeafrica.esafe.Model.AnounceList;
import com.esafeafrica.esafe.Model.AnounceSingle;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;

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
 * Use the {@link Announcements#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Announcements extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private anounceAdaptor adaptor;
    private ArrayList<Anounce> anounceArrayList;
    private Context context;

    public Announcements() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided pa
     *
     * @return A new instance of fragment Announcements.
     */
    // TODO: Rename and change types and number of parameters
    public static Announcements newInstance() {
        Announcements fragment = new Announcements();
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
        View rootview = inflater.inflate(R.layout.fragment_announcements, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        recyclerView = view.findViewById(R.id.anounce_list);
        context = getActivity();
        progressSync = new ProgressSync(context);
        loadItems();
    }

    private void loadItems() {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<AnounceList> call = apiInterface.allAnounce();
        call.enqueue(new Callback<AnounceList>() {
            @Override
            public void onResponse(Call<AnounceList> call, Response<AnounceList> response) {
                if (response.isSuccessful()) {
                    progressSync.hideDialog();
                    if(response.body() != null){
                    anounceArrayList = response.body().getAnounce();
                    //progressSync.hideDialog();
                    adaptor = new anounceAdaptor(getActivity(), anounceArrayList, new anounceAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Anounce anounce) {
                            selectedAnounce(anounce);
                            Log.d("Item Clicked", anounce.getName());
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                }else{
                        recyclerView.setVisibility(View.GONE);
                        EmptyList(getContext(),getLayoutInflater());
                    }} else {
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Shops", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AnounceList> call, Throwable t) {
                progressSync.hideDialog();
                loadAnounceSingle();
                //NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }
    private void loadAnounceSingle(){
        apiInterface=RetroClient.getClient().create(ApiInterface.class);
        Call<AnounceSingle> call=apiInterface.allAnounceSingle();
        call.enqueue(new Callback<AnounceSingle>() {
            @Override
            public void onResponse(Call<AnounceSingle> call, Response<AnounceSingle> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        AnounceSingle anounceSingle=response.body();
                        anounceArrayList=new ArrayList<Anounce>();
                        anounceArrayList.add(anounceSingle.getAnounce());
                        //anounceArrayList = response.body().getAnounce();
                        //progressSync.hideDialog();
                        adaptor = new anounceAdaptor(getActivity(), anounceArrayList, new anounceAdaptor.OnItemClickListener() {
                            @Override
                            public void OnItemClick(Anounce anounce) {
                                selectedAnounce(anounce);
                                Log.d("Item Clicked", anounce.getName());
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
                }else {
                    ServerError(getContext(), getLayoutInflater());
                    Log.d("Events", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<AnounceSingle> call, Throwable t) {

            }
        });
    }

    private void selectedAnounce(Anounce anounce) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("anounce", anounce);
        FragmentManager manager = getFragmentManager();
        anounceSelected selected = new anounceSelected();
        selected.setArguments(bundle);
        selected.show(manager, "anounce");
    }

}
