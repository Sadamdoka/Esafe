package com.esafeafrica.esafe.Config;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.esafeafrica.esafe.R;

public class ProgressSync {
    private final Context context;
    private Dialog dialog;

    public ProgressSync(Context context) {
        this.context = context;
    }

    public ProgressSync(Context context, Dialog dialog) {
        this.context = context;
        this.dialog = dialog;
    }

    public void showDialog() {

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //...set cancelable false so that it's never get hidden
        dialog.setCancelable(false);
        //...that's the layout i told you will inflate later
        dialog.setContentView(R.layout.progress_sync);

        //...initialize the imageView form infalted layout
        ImageView gifImageView = dialog.findViewById(R.id.custom_loading_imageView);

        /*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        */

        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(gifImageView);


        //...now load that gif which we put inside the drawble folder here with the help of Glide

        /** Glide.with(context)
         .load(R.drawable.loading)
         .placeholder(R.drawable.loading)
         .centerCrop()
         .crossFade()
         .into(imageViewTarget);
         **/
        Glide.with(context)
                .load(R.drawable.load)
                .into(gifImageView);
        //...finaly show it
        dialog.show();
    }

    //..also create a method which will hide the dialog when some work is done
    public void hideDialog() {
        dialog.dismiss();
    }

}
