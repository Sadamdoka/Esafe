package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Numbers implements Parcelable {

    public static final Creator<Numbers> CREATOR = new Creator<Numbers>() {
        @Override
        public Numbers createFromParcel(Parcel in) {
            return new Numbers(in);
        }

        @Override
        public Numbers[] newArray(int size) {
            return new Numbers[size];
        }
    };
    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("names")
    @Expose
    private String names;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("status")
    @Expose
    private String status;

    public Numbers() {
    }

    public Numbers(String datereg, String id, String email, String names, String phone, String status) {
        this.datereg = datereg;
        this.id = id;
        this.email = email;
        this.names = names;
        this.phone = phone;
        this.status = status;
    }

    protected Numbers(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        email = in.readString();
        names = in.readString();
        phone = in.readString();
        status = in.readString();
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(datereg);
        parcel.writeString(id);
        parcel.writeString(email);
        parcel.writeString(names);
        parcel.writeString(phone);
        parcel.writeString(status);
    }
}
