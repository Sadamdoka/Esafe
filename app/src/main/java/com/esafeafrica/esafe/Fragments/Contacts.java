package com.esafeafrica.esafe.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Adaptors.contactAdaptor;
import com.esafeafrica.esafe.Api.ApiInterface;
import com.esafeafrica.esafe.Api.RetroClient;
import com.esafeafrica.esafe.Config.ProgressSync;
import com.esafeafrica.esafe.Model.Contact;
import com.esafeafrica.esafe.Model.ContactList;
import com.esafeafrica.esafe.Model.Feedback;
import com.esafeafrica.esafe.R;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.esafeafrica.esafe.Config.ErrorMgt.IncorrectDetails;
import static com.esafeafrica.esafe.Config.ErrorMgt.NoInternet;
import static com.esafeafrica.esafe.Config.ErrorMgt.ServerError;
import static com.esafeafrica.esafe.Config.ErrorMgt.SuccessAccount;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Contacts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contacts extends Fragment {
    private ProgressSync progressSync;
    private ApiInterface apiInterface;
    private RecyclerView recyclerView;
    private contactAdaptor adaptor;
    private ArrayList<Contact> contactArrayList;
    private Context context;
    private String id, userid, dat;
    private EditText name, mail, tel;
    private Button edit, add;

    public Contacts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Contacts.
     */
    // TODO: Rename and change types and number of parameters
    public static Contacts newInstance() {
        Contacts fragment = new Contacts();
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
        View rootview = inflater.inflate(R.layout.fragment_contacts, container, false);
        InitView(rootview);
        return rootview;
    }

    public void InitView(View view) {
        recyclerView = view.findViewById(R.id.contact_list);
        context = getActivity();
        progressSync = new ProgressSync(context);
        name = view.findViewById(R.id.edit_con_name);
        tel = view.findViewById(R.id.edit_con_tel);
        mail = view.findViewById(R.id.edit_con_mail);
        add = view.findViewById(R.id.btn_addcontact);
        edit = view.findViewById(R.id.btn_editcontact);
        //edit.setVisibility(View.INVISIBLE);
       // loadItems();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addContact(new Contact(null, null, mail.getText().toString(), name.getText().toString(), tel.getText().toString(), null));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editContact(new Contact(null, id, mail.getText().toString(), name.getText().toString(), tel.getText().toString(), userid));
            }
        });
    }


    private void setValues(Contact contact) {
        id = contact.getId();
        userid = contact.getUserid();
        dat = contact.getDatereg();
        name.setText(contact.getName());
        mail.setText(contact.getEmail());
        tel.setText(contact.getPhone());
        edit.setVisibility(View.VISIBLE);
    }

    private void loadItems() {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        Call<ContactList> call = apiInterface.allContacts();
        call.enqueue(new Callback<ContactList>() {
            @Override
            public void onResponse(Call<ContactList> call, Response<ContactList> response) {
                if (response.isSuccessful()) {
                    contactArrayList = response.body().getContact();
                    //progressSync.hideDialog();
                    adaptor = new contactAdaptor(getActivity(), contactArrayList, new contactAdaptor.OnItemClickListener() {
                        @Override
                        public void OnItemClick(Contact contact) {
                            setValues(contact);
                            //selectedContact(contact);
                            //ContentLoader(getActivity(),getLayoutInflater(),contact.getPhone(),contact.getEmail());
                            Log.d("Item Clicked", contact.getName());
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
            public void onFailure(Call<ContactList> call, Throwable t) {
                NoInternet(getActivity(), getLayoutInflater());
            }
        });
    }

    private void addContact(Contact contact) {
        progressSync.showDialog();
        apiInterface = RetroClient.getClient().create(ApiInterface.class);

        RequestBody userreq = RequestBody.create(MultipartBody.FORM, contact.getUserid());
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, contact.getName());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, contact.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, contact.getPhone());

        Call<Feedback> call = apiInterface.createContact(userreq, namereq, emailreq, phonereq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        progressSync.hideDialog();
                        //Log.d("Success", "Client Successs");
                        SuccessAccount(getContext(), getLayoutInflater(), contact.getEmail());
                    } else {
                        progressSync.hideDialog();
                        Log.d("Error", feedback.getErrorMsg());
                        IncorrectDetails(getContext(), getLayoutInflater());
                        //Toast.makeText(context, feedback.getErrorMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    progressSync.hideDialog();
                    Log.d("Error", "Error Server");
                    ServerError(getContext(), getLayoutInflater());
                    //Toast.makeText(context, "Server Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Feedback> call, Throwable t) {
                progressSync.hideDialog();
                NoInternet(getContext(), getLayoutInflater());
                t.printStackTrace();
            }
        });
    }

    private void editContact(Contact contact) {
        apiInterface = RetroClient.getClient().create(ApiInterface.class);
        RequestBody idreq = RequestBody.create(MultipartBody.FORM, contact.getId());
        RequestBody userreq = RequestBody.create(MultipartBody.FORM, contact.getUserid());
        RequestBody namereq = RequestBody.create(MultipartBody.FORM, contact.getName());
        RequestBody emailreq = RequestBody.create(MultipartBody.FORM, contact.getEmail());
        RequestBody phonereq = RequestBody.create(MultipartBody.FORM, contact.getPhone());

        Call<Feedback> call = apiInterface.updateContact(idreq, userreq, namereq, emailreq, phonereq);
        call.enqueue(new Callback<Feedback>() {
            @Override
            public void onResponse(Call<Feedback> call, Response<Feedback> response) {
                Feedback feedback = response.body();
                if (response.isSuccessful()) {
                    if (feedback.getStatus()) {
                        //Log.d("Success", "Client Successs");
                        SuccessAccount(getContext(), getLayoutInflater(), contact.getEmail());
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


    private void selectedContact(Contact contact) {
        Bundle bundle = new Bundle();
        // bundle.putParcelable("client", getClient());
        bundle.putParcelable("contact", contact);
        FragmentManager manager = getFragmentManager();
        contactSelected selected = new contactSelected();
        selected.setArguments(bundle);
        selected.show(manager, "contact");
    }

}
