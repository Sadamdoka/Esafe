package com.esafeafrica.esafe.Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageViewHolder extends RecyclerView.ViewHolder {

    TextView messageTextView;
    ImageView messageImageView;
    TextView messengerTextView;
    CircleImageView messengerImageView;

    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
    }

}
