
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cases implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("case")
    @Expose
    private String _case;
    @SerializedName("dead")
    @Expose
    private String dead;
    @SerializedName("lastUpdate")
    @Expose
    private String lastUpdate;
    @SerializedName("recovery")
    @Expose
    private String recovery;

    protected Cases(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        _case = in.readString();
        dead = in.readString();
        lastUpdate = in.readString();
        recovery = in.readString();
    }

    public static final Creator<Cases> CREATOR = new Creator<Cases>() {
        @Override
        public Cases createFromParcel(Parcel in) {
            return new Cases(in);
        }

        @Override
        public Cases[] newArray(int size) {
            return new Cases[size];
        }
    };

    public String getDatereg() {
        return datereg;
    }

    public void setDatereg(String datereg) {
        this.datereg = datereg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCase() {
        return _case;
    }

    public void setCase(String _case) {
        this._case = _case;
    }

    public String getDead() {
        return dead;
    }

    public void setDead(String dead) {
        this.dead = dead;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getRecovery() {
        return recovery;
    }

    public void setRecovery(String recovery) {
        this.recovery = recovery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(_case);
        dest.writeString(dead);
        dest.writeString(lastUpdate);
        dest.writeString(recovery);
    }

    public Cases(String datereg, String id, String _case, String dead, String lastUpdate, String recovery) {
        this.datereg = datereg;
        this.id = id;
        this._case = _case;
        this.dead = dead;
        this.lastUpdate = lastUpdate;
        this.recovery = recovery;
    }
}
