package com.esafeafrica.esafe.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.esafe.Adaptors.anounceAdaptor;
import com.esafeafrica.esafe.Model.Anounce;
import com.esafeafrica.esafe.R;

import static com.esafeafrica.esafe.Config.Validation.getBitmapFromURLGlide;


public class anounceHolder extends RecyclerView.ViewHolder {

    private final LinearLayout parent;
    private final LinearLayout mage;
    private final TextView name;
    private final TextView title;
    private final TextView detail;
    private final TextView dat;
    private final Context context;
    private String id, status;

    public anounceHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.anounce_parent);
        mage = itemView.findViewById(R.id.anounce_pic);
        name = itemView.findViewById(R.id.anounce_name);
        title = itemView.findViewById(R.id.anounce_title);
        detail = itemView.findViewById(R.id.anounce_detail);
        dat = itemView.findViewById(R.id.anounce_time);
    }

    public void bind(final Anounce anounce, final anounceAdaptor.OnItemClickListener listener) {
        id = anounce.getId();
        status = anounce.getStatus();
        name.setText(anounce.getName());
        title.setText(anounce.getTitle());
        detail.setText(anounce.getDetails());
        getBitmapFromURLGlide(context,mage,anounce.getPic());
        //mage.setBackground(getDrawable(context, ConvertImage(anounce.getPic())));
        dat.setText(anounce.getDatereg());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(anounce);
            }
        });
    }
}
