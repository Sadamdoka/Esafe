package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UserWorkerList {

    @SerializedName("account")
    @Expose
    private ArrayList<UserWorker> userWorker = null;

    public ArrayList<UserWorker> getUserWorker() {
        return userWorker;
    }

    public void setUserWorker(ArrayList<UserWorker> userWorker) {
        this.userWorker = userWorker;
    }

}
