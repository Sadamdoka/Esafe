package com.esafeafrica.esafe.Holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.esafe.Adaptors.donateAdaptor;
import com.esafeafrica.esafe.Model.Donate;
import com.esafeafrica.esafe.R;


public class donateHolder extends RecyclerView.ViewHolder{
    private final TextView names;
    private final TextView number;
    private final TextView banker;
    private Context context;
    private String id, userid, dat;

    public donateHolder(@NonNull View itemView) {
        super(itemView);
        names=itemView.findViewById(R.id.don_item_name);
        number=itemView.findViewById(R.id.don_item_account);
        banker=itemView.findViewById(R.id.don_item_bank);
    }

    public void bind(final Donate donate, final donateAdaptor.OnItemClickListener listener) {
        id=donate.getId();
        names.setText(donate.getNames());
        number.setText(donate.getNumber());
        banker.setText(donate.getBanker());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnItemClick(donate);
            }
        });
    }

}
