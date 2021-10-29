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

import com.esafeafrica.esafe.Adaptors.amnestyNoAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Model.Anounce;
import com.esafeafrica.esafe.Model.AnounceSingle;
import com.esafeafrica.esafe.Model.Numbers;
import com.esafeafrica.esafe.Model.NumbersList;
import com.esafeafrica.esafe.Model.NumbersSingle;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AmnestyNo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AmnestyNo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match

    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private amnestyNoAdaptor adaptor;
    private ArrayList<Numbers> numbers;
    private Context context;

    public AmnestyNo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AmnestyNo.
     */
    // TODO: Rename and change types and number of parameters
    public static AmnestyNo newInstance() {
        AmnestyNo fragment = new AmnestyNo();
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
        View root = inflater.inflate(R.layout.fragment_amnesty, container, false);

        InitViews(root);
        return root;
    }

    public void InitViews(View item) {
        recyclerView = item.findViewById(R.id.amnesty_list);
        context = getActivity();
        progressSync = new ProgressSync(context);
        loadNumbers();
    }

    private void loadNumbers() {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<NumbersList> call = apiInterface.allNumbers();
        call.enqueue(new Callback<NumbersList>() {
            @Override
            public void onResponse(Call<NumbersList> call, Response<NumbersList> response) {
                if (response.isSuccessful()) {
                    numbers = response.body().getNumbers();
                    //progressSync.hideDialog();
                    adaptor = new amnestyNoAdaptor(getContext(), numbers, new amnestyNoAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Numbers numbers) {
                            selectedNumbers(numbers);
                            Log.d("Item Clicked", numbers.getNames());
                            //ContentLoader(getActivity(),getLayoutInflater(),numbers.getPhone(),numbers.getEmail());
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
            public void onFailure(Call<NumbersList> call, Throwable t) {
                loadSingleNumber();
            }
        });
    }

    private void loadSingleNumber() {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<NumbersSingle> call = apiInterface.allNumber();
        call.enqueue(new Callback<NumbersSingle>() {
            @Override
            public void onResponse(Call<NumbersSingle> call, Response<NumbersSingle> response) {
                if (response.isSuccessful()) {
                    NumbersSingle numbersSingle=response.body();
                    numbers=new ArrayList<Numbers>();
                    numbers.add(numbersSingle.getNumbers());
                    //progressSync.hideDialog();
                    adaptor = new amnestyNoAdaptor(getContext(), numbers, new amnestyNoAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Numbers numbers) {
                            selectedNumbers(numbers);
                            Log.d("Item Clicked", numbers.getNames());
                            //ContentLoader(getActivity(),getLayoutInflater(),numbers.getPhone(),numbers.getEmail());
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
            public void onFailure(Call<NumbersSingle> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }

    private void selectedNumbers(Numbers numbers) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("number", numbers);
        FragmentManager manager = getFragmentManager();
        contactSelected selected = new contactSelected();
        selected.setArguments(bundle);
        selected.show(manager, "number");
    }
}
