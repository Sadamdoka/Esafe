package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponderList {

    @SerializedName("responder")
    @Expose
    private ArrayList<Responder> responder = null;

    public ArrayList<Responder> getResponder() {
        return responder;
    }

    public void setResponder(ArrayList<Responder> responder) {
        this.responder = responder;
    }

}
