package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AmnestyList {

    @SerializedName("amnesty")
    @Expose
    private ArrayList<Amnesty> amnesty = null;

    public ArrayList<Amnesty> getAmnesty() {
        return amnesty;
    }

    public void setAmnesty(ArrayList<Amnesty> amnesty) {
        this.amnesty = amnesty;
    }

}
