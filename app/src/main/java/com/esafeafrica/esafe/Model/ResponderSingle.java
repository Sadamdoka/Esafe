package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponderSingle {

    @SerializedName("responder")
    @Expose
    private Responder responder;

    public Responder getResponder() {
        return responder;
    }

    public void setResponder(Responder responder) {
        this.responder = responder;
    }

}
