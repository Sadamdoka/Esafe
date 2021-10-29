package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Contact implements Parcelable {

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("userid")
    @Expose
    private String userid;

    public Contact() {
    }

    public Contact(String datereg, String id, String email, String name, String phone, String userid) {
        this.datereg = datereg;
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.userid = userid;
    }

    protected Contact(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        email = in.readString();
        name = in.readString();
        phone = in.readString();
        userid = in.readString();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
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
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(userid);
    }
}
