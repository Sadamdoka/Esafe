package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class EmergencyList {

    @SerializedName("emergency")
    @Expose
    private ArrayList<Emergency> emergency = null;

    public ArrayList<Emergency> getEmergency() {
        return emergency;
    }

    public void setEmergency(ArrayList<Emergency> emergency) {
        this.emergency = emergency;
    }

}
