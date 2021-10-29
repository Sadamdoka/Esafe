package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnounceList {

    @SerializedName("anounce")
    @Expose
    private ArrayList<Anounce> anounce = null;

    public ArrayList<Anounce> getAnounce() {
        return anounce;
    }

    public void setAnounce(ArrayList<Anounce> anounce) {
        this.anounce = anounce;
    }

}
