
package com.esafeafrica.esafe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserWorker implements Parcelable {

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
    @SerializedName("bank_account")
    @Expose
    private String bankAccount;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("ex_phone")
    @Expose
    private String exPhone;
    @SerializedName("fcompany")
    @Expose
    private String fcompany;
    @SerializedName("fpic")
    @Expose
    private String fpic;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("jobtype")
    @Expose
    private String jobtype;
    @SerializedName("kin_name")
    @Expose
    private String kinName;
    @SerializedName("kin_phone")
    @Expose
    private String kinPhone;
    @SerializedName("lati")
    @Expose
    private String lati;
    @SerializedName("lcompany")
    @Expose
    private String lcompany;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("longi")
    @Expose
    private String longi;
    @SerializedName("marital")
    @Expose
    private String marital;
    @SerializedName("names")
    @Expose
    private String names;
    @SerializedName("nationality")
    @Expose
    private String nationality;
    @SerializedName("nin")
    @Expose
    private String nin;
    @SerializedName("npic")
    @Expose
    private String npic;
    @SerializedName("parish")
    @Expose
    private String parish;
    @SerializedName("passport")
    @Expose
    private String passport;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("pic")
    @Expose
    private String pic;
    @SerializedName("pob")
    @Expose
    private String pob;
    @SerializedName("ptpic")
    @Expose
    private String ptpic;
    @SerializedName("subcounty")
    @Expose
    private String subcounty;
    @SerializedName("userid")
    @Expose
    private String userid;
    @SerializedName("village")
    @Expose
    private String village;

    protected UserWorker(Parcel in) {
        datereg = in.readString();
        id = in.readString();
        status = in.readString();
        address = in.readString();
        bankAccount = in.readString();
        bankName = in.readString();
        district = in.readString();
        dob = in.readString();
        email = in.readString();
        exPhone = in.readString();
        fcompany = in.readString();
        fpic = in.readString();
        gender = in.readString();
        jobtype = in.readString();
        kinName = in.readString();
        kinPhone = in.readString();
        lati = in.readString();
        lcompany = in.readString();
        location = in.readString();
        longi = in.readString();
        marital = in.readString();
        names = in.readString();
        nationality = in.readString();
        nin = in.readString();
        npic = in.readString();
        parish = in.readString();
        passport = in.readString();
        phone = in.readString();
        pic = in.readString();
        pob = in.readString();
        ptpic = in.readString();
        subcounty = in.readString();
        userid = in.readString();
        village = in.readString();
    }

    public static final Creator<UserWorker> CREATOR = new Creator<UserWorker>() {
        @Override
        public UserWorker createFromParcel(Parcel in) {
            return new UserWorker(in);
        }

        @Override
        public UserWorker[] newArray(int size) {
            return new UserWorker[size];
        }
    };


    public UserWorker() {
    }
    public UserWorker(String lati, String location, String longi, String nin) {
        this.lati = lati;
        this.location = location;
        this.longi = longi;
        this.nin = nin;
    }
    public UserWorker(String id, String email, String nin, String passport, String userid) {
        this.id = id;
        this.email = email;
        this.nin = nin;
        this.passport = passport;
        this.userid = userid;
    }



    public UserWorker(String address, String dob, String email, String gender, String jobtype, String lati, String lcompany, String location, String longi, String names, String passport, String phone, String pic) {
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.gender = gender;
        this.jobtype = jobtype;
        this.lati = lati;
        this.lcompany = lcompany;
        this.location = location;
        this.longi = longi;
        this.names = names;
        this.passport = passport;
        this.phone = phone;
        this.pic = pic;
    }

    public UserWorker(String datereg, String id, String status, String address, String bankAccount, String bankName, String code, String district, String dob, String email, String exPhone, String fcompany, String fpic, String gender, String jobtype, String kinName, String kinPhone, String lati, String lcompany, String location, String longi, String marital, String names, String nationality, String nin, String npic, String parish, String passport, String phone, String pic, String pob, String ptpic, String subcounty, String userid, String village) {
        this.datereg = datereg;
        this.id = id;
        this.status = status;
        this.address = address;
        this.bankAccount = bankAccount;
        this.bankName = bankName;
        this.code = code;
        this.district = district;
        this.dob = dob;
        this.email = email;
        this.exPhone = exPhone;
        this.fcompany = fcompany;
        this.fpic = fpic;
        this.gender = gender;
        this.jobtype = jobtype;
        this.kinName = kinName;
        this.kinPhone = kinPhone;
        this.lati = lati;
        this.lcompany = lcompany;
        this.location = location;
        this.longi = longi;
        this.marital = marital;
        this.names = names;
        this.nationality = nationality;
        this.nin = nin;
        this.npic = npic;
        this.parish = parish;
        this.passport = passport;
        this.phone = phone;
        this.pic = pic;
        this.pob = pob;
        this.ptpic = ptpic;
        this.subcounty = subcounty;
        this.userid = userid;
        this.village = village;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getExPhone() {
        return exPhone;
    }

    public void setExPhone(String exPhone) {
        this.exPhone = exPhone;
    }

    public String getFcompany() {
        return fcompany;
    }

    public void setFcompany(String fcompany) {
        this.fcompany = fcompany;
    }

    public String getFpic() {
        return fpic;
    }

    public void setFpic(String fpic) {
        this.fpic = fpic;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getJobtype() {
        return jobtype;
    }

    public void setJobtype(String jobtype) {
        this.jobtype = jobtype;
    }

    public String getKinName() {
        return kinName;
    }

    public void setKinName(String kinName) {
        this.kinName = kinName;
    }

    public String getKinPhone() {
        return kinPhone;
    }

    public void setKinPhone(String kinPhone) {
        this.kinPhone = kinPhone;
    }

    public String getLati() {
        return lati;
    }

    public void setLati(String lati) {
        this.lati = lati;
    }

    public String getLcompany() {
        return lcompany;
    }

    public void setLcompany(String lcompany) {
        this.lcompany = lcompany;
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

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNin() {
        return nin;
    }

    public void setNin(String nin) {
        this.nin = nin;
    }

    public String getNpic() {
        return npic;
    }

    public void setNpic(String npic) {
        this.npic = npic;
    }

    public String getParish() {
        return parish;
    }

    public void setParish(String parish) {
        this.parish = parish;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
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

    public String getPob() {
        return pob;
    }

    public void setPob(String pob) {
        this.pob = pob;
    }

    public String getPtpic() {
        return ptpic;
    }

    public void setPtpic(String ptpic) {
        this.ptpic = ptpic;
    }

    public String getSubcounty() {
        return subcounty;
    }

    public void setSubcounty(String subcounty) {
        this.subcounty = subcounty;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
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
        dest.writeString(bankAccount);
        dest.writeString(bankName);
        dest.writeString(district);
        dest.writeString(dob);
        dest.writeString(email);
        dest.writeString(exPhone);
        dest.writeString(fcompany);
        dest.writeString(fpic);
        dest.writeString(gender);
        dest.writeString(jobtype);
        dest.writeString(kinName);
        dest.writeString(kinPhone);
        dest.writeString(lati);
        dest.writeString(lcompany);
        dest.writeString(location);
        dest.writeString(longi);
        dest.writeString(marital);
        dest.writeString(names);
        dest.writeString(nationality);
        dest.writeString(nin);
        dest.writeString(npic);
        dest.writeString(parish);
        dest.writeString(passport);
        dest.writeString(phone);
        dest.writeString(pic);
        dest.writeString(pob);
        dest.writeString(ptpic);
        dest.writeString(subcounty);
        dest.writeString(userid);
        dest.writeString(village);
    }
}
