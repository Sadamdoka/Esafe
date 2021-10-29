
package com.esafeafrica.esafe.Model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrganList {

    @SerializedName("organ")
    @Expose
    private ArrayList<Organ> organ = null;

    public ArrayList<Organ> getOrgan() {
        return organ;
    }

    public void setOrgan(ArrayList<Organ> organ) {
        this.organ = organ;
    }

}
