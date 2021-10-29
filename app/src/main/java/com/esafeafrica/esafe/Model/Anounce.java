package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Anounce implements Parcelable {

    public static final Creator<Anounce> CREATOR = new Creator<Anounce>() {
        @Override
        public Anounce createFromParcel(Parcel in) {
            return new Anounce(in);
        }

        @Override
        public Anounce[] newArray(int size) {
            return new Anounce[size];
        }
    };
    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;

    public Anounce() {
    }

    public Anounce(String datereg, String id, String details, String name, String pic, String status, String title) {
        this.datereg = datereg;
        this.id = id;
        this.details = details;
        this.name = name;
        this.pic = pic;
        this.status = status;
        this.title = title;
    }

    protected Anounce(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        details = in.readString();
        name = in.readString();
        pic = in.readString();
        status = in.readString();
        title = in.readString();
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(datereg);
        parcel.writeString(id);
        parcel.writeString(details);
        parcel.writeString(name);
        parcel.writeString(pic);
        parcel.writeString(status);
        parcel.writeString(title);
    }
}
