
package com.esafeafrica.esafe.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CasesList {

    @SerializedName("cases")
    @Expose
    private ArrayList<Cases> cases = null;

    public ArrayList<Cases> getCases() {
        return cases;
    }

    public void setCases(ArrayList<Cases> cases) {
        this.cases = cases;
    }

}
