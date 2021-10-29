
package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserWorkerSingle {

    @SerializedName("user_worker")
    @Expose
    private UserWorker userWorker;

    public UserWorker getUserWorker() {
        return userWorker;
    }

    public void setUserWorker(UserWorker userWorker) {
        this.userWorker = userWorker;
    }

}
