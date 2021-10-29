package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Responder implements Parcelable {

    public static final Creator<Responder> CREATOR = new Creator<Responder>() {
        @Override
        public Responder createFromParcel(Parcel in) {
            return new Responder(in);
        }

        @Override
        public Responder[] newArray(int size) {
            return new Responder[size];
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
    @SerializedName("organid")
    @Expose
    private String organid;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("status")
    @Expose
    private String status;

    protected Responder(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        email = in.readString();
        names = in.readString();
        organid = in.readString();
        password = in.readString();
        phone = in.readString();
        pic = in.readString();
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

    public String getOrganid() {
        return organid;
    }

    public void setOrganid(String organid) {
        this.organid = organid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
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
        parcel.writeString(organid);
        parcel.writeString(password);
        parcel.writeString(phone);
        parcel.writeString(pic);
        parcel.writeString(status);
    }
}
