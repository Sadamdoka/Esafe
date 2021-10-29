package com.esafeafrica.esafe.Holder;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esafeafrica.esafe.Adaptors.contactAdaptor;
import com.esafeafrica.esafe.Model.Contact;
import com.esafeafrica.esafe.R;


public class contactHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final LinearLayout parent;
    private final TextView name;
    private final TextView phone;
    private final TextView email;
    private String id, userid, dat;

    public contactHolder(@NonNull View itemView) {
        super(itemView);
        context = itemView.getContext();
        parent = itemView.findViewById(R.id.contact_parent);
        name = itemView.findViewById(R.id.contact_name);
        phone = itemView.findViewById(R.id.contact_tel);
        email = itemView.findViewById(R.id.contact_email);
    }

    public void bind(final Contact contact, final contactAdaptor.OnItemClickListener listener) {
        id = contact.getId();
        userid = contact.getUserid();
        dat = contact.getDatereg();
        name.setText(contact.getName());
        email.setText(contact.getEmail());
        phone.setText(contact.getPhone());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnItemClick(contact);
            }
        });
    }
}
