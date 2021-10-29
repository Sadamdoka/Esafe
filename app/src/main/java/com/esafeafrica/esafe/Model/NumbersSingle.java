package com.esafeafrica.esafe.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NumbersSingle {

    @SerializedName("numbers")
    @Expose
    private Numbers numbers;

    public Numbers getNumbers() {
        return numbers;
    }

    public void setNumbers(Numbers numbers) {
        this.numbers = numbers;
    }

}
