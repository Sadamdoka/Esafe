
package com.esafeafrica.esafe.Model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PressList {

    @SerializedName("press")
    @Expose
    private ArrayList<Press> press = null;

    public ArrayList<Press> getPress() {
        return press;
    }

    public void setPress(ArrayList<Press> press) {
        this.press = press;
    }

}
