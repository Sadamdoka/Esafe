
package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonateSingle {

    @SerializedName("donate")
    @Expose
    private Donate donate;

    public Donate getDonate() {
        return donate;
    }

    public void setDonate(Donate donate) {
        this.donate = donate;
    }

}
