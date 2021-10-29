package com.esafeafrica.esafe.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.esafeafrica.esafe.Holder.amnestyNoHolder;
import com.esafeafrica.esafe.Model.Numbers;
import com.esafeafrica.esafe.R;

import java.util.List;

public class amnestyNoAdaptor extends RecyclerView.Adapter<amnestyNoHolder> {
    private final Context context;
    private final List<Numbers> numbersList;
    private final OnItemClickListener listener;

    public amnestyNoAdaptor(Context context, List<Numbers> numbersList, OnItemClickListener listener) {
        this.context = context;
        this.numbersList = numbersList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public amnestyNoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        amnestyNoHolder holder = new amnestyNoHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull amnestyNoHolder holder, int position) {
        holder.bind(numbersList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return numbersList.size();
    }


    public interface OnItemClickListener {
        void OnItemClick(Numbers numbers);
    }
}
