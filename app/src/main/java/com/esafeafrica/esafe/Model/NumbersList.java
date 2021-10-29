package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NumbersList {

    @SerializedName("numbers")
    @Expose
    private ArrayList<Numbers> numbers = null;

    public ArrayList<Numbers> getNumbers() {
        return numbers;
    }

    public void setNumbers(ArrayList<Numbers> numbers) {
        this.numbers = numbers;
    }

}
