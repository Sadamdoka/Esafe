
package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PressSingle {

    @SerializedName("press")
    @Expose
    private Press press;

    public Press getPress() {
        return press;
    }

    public void setPress(Press press) {
        this.press = press;
    }

}
