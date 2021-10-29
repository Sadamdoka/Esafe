
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Corona implements Parcelable {

    @SerializedName("datereg")
    @Expose
    private String datereg;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("breath")
    @Expose
    private String breath;
    @SerializedName("cough")
    @Expose
    private String cough;
    @SerializedName("fever")
    @Expose
    private String fever;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("longi")
    @Expose
    private String longi;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nose")
    @Expose
    private String nose;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("pain")
    @Expose
    private String pain;
    @SerializedName("sore")
    @Expose
    private String sore;
    @SerializedName("tired")
    @Expose
    private String tired;
    @SerializedName("travel")
    @Expose
    private String travel;
    @SerializedName("were")
    @Expose
    private String were;

    protected Corona(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        breath = in.readString();
        cough = in.readString();
        fever = in.readString();
        lati = in.readString();
        longi = in.readString();
        name = in.readString();
        nose = in.readString();
        number = in.readString();
        pain = in.readString();
        sore = in.readString();
        tired = in.readString();
        travel = in.readString();
        were = in.readString();
    }

    public static final Creator<Corona> CREATOR = new Creator<Corona>() {
        @Override
        public Corona createFromParcel(Parcel in) {
            return new Corona(in);
        }

        @Override
        public Corona[] newArray(int size) {
            return new Corona[size];
        }
    };

    public Corona() {
    }

    public Corona(String datereg, String id, String breath, String cough, String fever, String lati, String longi, String name, String nose, String number, String pain, String sore, String tired, String travel, String were) {
        this.datereg = datereg;
        this.id = id;
        this.breath = breath;
        this.cough = cough;
        this.fever = fever;
        this.lati = lati;
        this.longi = longi;
        this.name = name;
        this.nose = nose;
        this.number = number;
        this.pain = pain;
        this.sore = sore;
        this.tired = tired;
        this.travel = travel;
        this.were = were;
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

    public String getBreath() {
        return breath;
    }

    public void setBreath(String breath) {
        this.breath = breath;
    }

    public String getCough() {
        return cough;
    }

    public void setCough(String cough) {
        this.cough = cough;
    }

    public String getFever() {
        return fever;
    }

    public void setFever(String fever) {
        this.fever = fever;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
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

    public String getNose() {
        return nose;
    }

    public void setNose(String nose) {
        this.nose = nose;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPain() {
        return pain;
    }

    public void setPain(String pain) {
        this.pain = pain;
    }

    public String getSore() {
        return sore;
    }

    public void setSore(String sore) {
        this.sore = sore;
    }

    public String getTired() {
        return tired;
    }

    public void setTired(String tired) {
        this.tired = tired;
    }

    public String getTravel() {
        return travel;
    }

    public void setTravel(String travel) {
        this.travel = travel;
    }

    public String getWere() {
        return were;
    }

    public void setWere(String were) {
        this.were = were;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(datereg);
        dest.writeString(id);
        dest.writeString(breath);
        dest.writeString(cough);
        dest.writeString(fever);
        dest.writeString(lati);
        dest.writeString(longi);
        dest.writeString(name);
        dest.writeString(nose);
        dest.writeString(number);
        dest.writeString(pain);
        dest.writeString(sore);
        dest.writeString(tired);
        dest.writeString(travel);
        dest.writeString(were);
    }
}
