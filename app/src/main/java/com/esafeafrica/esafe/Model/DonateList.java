
package com.esafeafrica.esafe.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonateList {

    @SerializedName("donate")
    @Expose
    private ArrayList<Donate> donate = null;

    public ArrayList<Donate> getDonate() {
        return donate;
    }

    public void setDonate(ArrayList<Donate> donate) {
        this.donate = donate;
    }

}
