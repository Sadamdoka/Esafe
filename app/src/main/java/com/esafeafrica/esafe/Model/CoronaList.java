
package com.esafeafrica.esafe.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoronaList {

    @SerializedName("corona")
    @Expose
    private ArrayList<Corona> corona = null;

    public ArrayList<Corona> getCorona() {
        return corona;
    }

    public void setCorona(ArrayList<Corona> corona) {
        this.corona = corona;
    }

}
