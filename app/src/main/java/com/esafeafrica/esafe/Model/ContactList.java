package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactList {

    @SerializedName("contact")
    @Expose
    private ArrayList<Contact> contact = null;

    public ArrayList<Contact> getContact() {
        return contact;
    }

    public void setContact(ArrayList<Contact> contact) {
        this.contact = contact;
    }

}
