package com.esafeafrica.esafe.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Holder.donateHolder;
import com.esafeafrica.esafe.Model.Donate;
import com.esafeafrica.esafe.R;

import java.util.List;

public class donateAdaptor  extends RecyclerView.Adapter<donateHolder>{

    private Context context;
    private final List<Donate> donates;
    private final OnItemClickListener listener;

    public donateAdaptor(List<Donate> donates, OnItemClickListener listener) {
        this.donates = donates;
        this.listener = listener;
    }

    public donateAdaptor(Context context, List<Donate> donates, OnItemClickListener listener) {
        this.context = context;
        this.donates = donates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public donateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donate, parent, false);
        donateHolder holder = new donateHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull donateHolder holder, int position) {
        holder.bind(donates.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return donates.size();
    }


    public interface OnItemClickListener {
        void OnItemClick(Donate donate);
    }
}
