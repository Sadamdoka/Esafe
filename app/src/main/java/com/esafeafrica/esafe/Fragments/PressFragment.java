package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.esafeafrica.esafe.Adaptors.pressAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Model.Press;
import com.esafeafrica.esafe.Model.PressList;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.General_Actions.startWebView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class PressFragment extends Fragment {

    private RecyclerView recyclerView;
    private ApiInterface apiInterface;
    private pressAdaptor adaptor;
    private ProgressSync progressSync;
    private Context context;
    private ArrayList<Press> pressArrayList;
    private WebView web;
    public PressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_press, container, false);
        Init(view);
        return view;
    }

    private void Init(View view){
        web=view.findViewById(R.id.webpress);
        context = getActivity();
        progressSync = new ProgressSync(context);
       // loadPress();
        //web.loadUrl("https://twitter.com/MinofHealthUG");
        startWebView(getActivity(),"https://www.yowerikmuseveni.com/more-guidelines-covid19-preventive-measures-and-need-shut-down",web);
    }

    private void loadPress(){
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<PressList> call=apiInterface.allPress();
        call.enqueue(new Callback<PressList>() {
            @Override
            public void onResponse(Call<PressList> call, Response<PressList> response) {
                if (response.isSuccessful()) {
                    progressSync.hideDialog();
                    pressArrayList = response.body().getPress();
                    //progressSync.hideDialog();
                    adaptor = new pressAdaptor(getActivity(), pressArrayList, new pressAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Press press) {
                            selectedPress(press);
                        }
                    });
                    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
                    recyclerView.setLayoutManager(layoutManager);
                    // recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adaptor);
                } else {
                    ServerError(getContext(), getLayoutInflater());
                    progressSync.hideDialog();
                    Log.d("press", response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<PressList> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getActivity(), getLayoutInflater());
            }
        });

    }

    private void selectedPress(Press press) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("press", press);
        FragmentManager manager = getFragmentManager();
        pressSelected selected = new pressSelected();
        selected.setArguments(bundle);
        selected.show(manager, "press");
    }
}
