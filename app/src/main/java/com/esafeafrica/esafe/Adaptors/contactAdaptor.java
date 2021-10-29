package com.esafeafrica.esafe.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Holder.contactHolder;
import com.esafeafrica.esafe.Model.Contact;
import com.esafeafrica.esafe.R;

import java.util.List;

public class contactAdaptor extends RecyclerView.Adapter<contactHolder> {
    private final Context context;
    private final List<Contact> contactList;
    private final OnItemClickListener listener;

    public contactAdaptor(Context context, List<Contact> contactList, OnItemClickListener listener) {
        this.context = context;
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public contactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        contactHolder holder = new contactHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull contactHolder holder, int position) {
        holder.bind(contactList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Contact contact);
    }
}
