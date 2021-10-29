
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Organ implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("names")
    @Expose
    private String names;
    @SerializedName("organid")
    @Expose
    private String organid;
    @SerializedName("phone")
    @Expose
    private String phone;


    public Organ() {
    }

    public Organ(String names, String organid) {
        this.names = names;
        this.organid = organid;
    }

    public Organ(String datereg, String id, String status, String address, String email, String names, String organid, String phone) {
        this.datereg = datereg;
        this.id = id;
        this.status = status;
        this.address = address;
        this.email = email;
        this.names = names;
        this.organid = organid;
        this.phone = phone;
    }

    protected Organ(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        status = in.readString();
        address = in.readString();
        email = in.readString();
        names = in.readString();
        organid = in.readString();
        phone = in.readString();
    }

    public static final Creator<Organ> CREATOR = new Creator<Organ>() {
        @Override
        public Organ createFromParcel(Parcel in) {
            return new Organ(in);
        }

        @Override
        public Organ[] newArray(int size) {
            return new Organ[size];
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(address);
        dest.writeString(email);
        dest.writeString(names);
        dest.writeString(organid);
        dest.writeString(phone);
    }
}
