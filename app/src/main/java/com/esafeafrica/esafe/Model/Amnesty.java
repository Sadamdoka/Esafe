package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Amnesty implements Parcelable {

    public static final Creator<Amnesty> CREATOR = new Creator<Amnesty>() {
        @Override
        public Amnesty createFromParcel(Parcel in) {
            return new Amnesty(in);
        }

        @Override
        public Amnesty[] newArray(int size) {
            return new Amnesty[size];
        }
    };
    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("status")
    @Expose
    private String status;

    public Amnesty() {
    }

    public Amnesty(String datereg, String id, String latitude, String longitude, String name, String pic, String status) {
        this.datereg = datereg;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.pic = pic;
        this.status = status;
    }

    protected Amnesty(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        name = in.readString();
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(datereg);
        parcel.writeString(id);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(name);
        parcel.writeString(pic);
        parcel.writeString(status);
    }
}
