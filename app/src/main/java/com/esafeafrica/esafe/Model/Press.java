
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Press implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("date_address")
    @Expose
    private String dateAddress;
    @SerializedName("pic1")
    @Expose
    private String pic1;
    @SerializedName("pic2")
    @Expose
    private String pic2;
    @SerializedName("pic3")
    @Expose
    private String pic3;
    @SerializedName("pic4")
    @Expose
    private String pic4;
    @SerializedName("pic5")
    @Expose
    private String pic5;

    protected Press(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        address = in.readString();
        dateAddress = in.readString();
        pic1 = in.readString();
        pic2 = in.readString();
        pic3 = in.readString();
        pic4 = in.readString();
        pic5 = in.readString();
    }

    public static final Creator<Press> CREATOR = new Creator<Press>() {
        @Override
        public Press createFromParcel(Parcel in) {
            return new Press(in);
        }

        @Override
        public Press[] newArray(int size) {
            return new Press[size];
        }
    };

    public Press() {
    }

    public Press(String datereg, String id, String address, String dateAddress, String pic1, String pic2, String pic3, String pic4, String pic5) {
        this.datereg = datereg;
        this.id = id;
        this.address = address;
        this.dateAddress = dateAddress;
        this.pic1 = pic1;
        this.pic2 = pic2;
        this.pic3 = pic3;
        this.pic4 = pic4;
        this.pic5 = pic5;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateAddress() {
        return dateAddress;
    }

    public void setDateAddress(String dateAddress) {
        this.dateAddress = dateAddress;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(address);
        dest.writeString(dateAddress);
        dest.writeString(pic1);
        dest.writeString(pic2);
        dest.writeString(pic3);
        dest.writeString(pic4);
        dest.writeString(pic5);
    }
}
