
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donate implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("banker")
    @Expose
    private String banker;
    @SerializedName("names")
    @Expose
    private String names;
    @SerializedName("number")
    @Expose
    private String number;


    public Donate() {
    }

    public Donate(String datereg, String id, String banker, String names, String number) {
        this.datereg = datereg;
        this.id = id;
        this.banker = banker;
        this.names = names;
        this.number = number;
    }

    protected Donate(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        banker = in.readString();
        names = in.readString();
        number = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(banker);
        dest.writeString(names);
        dest.writeString(number);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Donate> CREATOR = new Creator<Donate>() {
        @Override
        public Donate createFromParcel(Parcel in) {
            return new Donate(in);
        }

        @Override
        public Donate[] newArray(int size) {
            return new Donate[size];
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

    public String getBanker() {
        return banker;
    }

    public void setBanker(String banker) {
        this.banker = banker;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
