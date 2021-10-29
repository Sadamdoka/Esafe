package com.esafeafrica.esafe.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Holder.anounceHolder;
import com.esafeafrica.esafe.Model.Anounce;
import com.esafeafrica.esafe.R;

import java.util.List;

public class anounceAdaptor extends RecyclerView.Adapter<anounceHolder> {
    private final Context context;
    private final List<Anounce> anounces;
    private final OnItemClickListener listener;

    public anounceAdaptor(Context context, List<Anounce> anounces, OnItemClickListener listener) {
        this.context = context;
        this.anounces = anounces;
        this.listener = listener;
    }

    @NonNull
    @Override
    public anounceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anounce, parent, false);
        anounceHolder holder = new anounceHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull anounceHolder holder, int position) {
        holder.bind(anounces.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return anounces.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(Anounce anounce);
    }
}
