package com.esafeafrica.esafe.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.esafe.Adaptors.pressAdaptor;
import com.esafeafrica.esafe.Model.Press;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.Validation.getBitmapFromURLGlide;


public class pressHolder  extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView address;
    private final TextView dates;
    private final LinearLayout img;
    private String id, userid, dat;

    public pressHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        address= itemView.findViewById(R.id.item_pr_address);
        dates= itemView.findViewById(R.id.item_pr_date);
        img=itemView.findViewById(R.id.item_pr_img);
    }

    public void bind(final Press press, final pressAdaptor.OnItemClickListener listener) {
        id = press.getId();
        dat = press.getDatereg();
        address.setText(press.getAddress());
        dates.setText(press.getDateAddress());
        getBitmapFromURLGlide(context,img,press.getPic1());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(press);
            }
        });
    }
}
