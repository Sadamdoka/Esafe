package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnounceSingle {

    @SerializedName("anounce")
    @Expose
    private Anounce anounce;

    public Anounce getAnounce() {
        return anounce;
    }

    public void setAnounce(Anounce anounce) {
        this.anounce = anounce;
    }

}
