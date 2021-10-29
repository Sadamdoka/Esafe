
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Emergency implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("bpic")
    @Expose
    private String bpic;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("emerid")
    @Expose
    private String emerid;
    @SerializedName("formerid")
    @Expose
    private String formerid;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("longi")
    @Expose
    private String longi;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("organ")
    @Expose
    private String organ;
    @SerializedName("topic")
    @Expose
    private String topic;
    @SerializedName("userid")
    @Expose
    private String userid;

    public Emergency() {
    }

    public Emergency(String event) {
        this.event = event;
    }

    public Emergency(String id, String emerid, String passport, String organ, String userid) {
        this.id = id;
        this.emerid = emerid;
        this.passport = passport;
        this.organ = organ;
        this.userid = userid;
    }

    public Emergency(String datereg, String id, String status, String bpic, String details, String emerid, String formerid, String event, String lati, String location, String longi, String name, String passport, String organ, String topic, String userid) {
        this.datereg = datereg;
        this.id = id;
        this.status = status;
        this.bpic = bpic;
        this.details = details;
        this.emerid = emerid;
        this.formerid = formerid;
        this.event = event;
        this.lati = lati;
        this.location = location;
        this.longi = longi;
        this.name = name;
        this.passport = passport;
        this.organ = organ;
        this.topic = topic;
        this.userid = userid;
    }

    protected Emergency(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        status = in.readString();
        bpic = in.readString();
        details = in.readString();
        emerid = in.readString();
        formerid = in.readString();
        event = in.readString();
        lati = in.readString();
        location = in.readString();
        longi = in.readString();
        name = in.readString();
        passport = in.readString();
        topic = in.readString();
        userid = in.readString();
    }

    public static final Creator<Emergency> CREATOR = new Creator<Emergency>() {
        @Override
        public Emergency createFromParcel(Parcel in) {
            return new Emergency(in);
        }

        @Override
        public Emergency[] newArray(int size) {
            return new Emergency[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(status);
        dest.writeString(bpic);
        dest.writeString(details);
        dest.writeString(emerid);
        dest.writeString(formerid);
        dest.writeString(event);
        dest.writeString(lati);
        dest.writeString(location);
        dest.writeString(longi);
        dest.writeString(name);
        dest.writeString(passport);
        dest.writeString(topic);
        dest.writeString(userid);
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBpic() {
        return bpic;
    }

    public void setBpic(String bpic) {
        this.bpic = bpic;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getEmerid() {
        return emerid;
    }

    public void setEmerid(String emerid) {
        this.emerid = emerid;
    }

    public String getFormerid() {
        return formerid;
    }

    public void setFormerid(String formerid) {
        this.formerid = formerid;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }
}