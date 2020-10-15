package com.rsah.closeloop.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelInfran {

    @SerializedName("data")
    private String data;

    @SerializedName("hash")
    private String hash;

    public ModelInfran(String data , String hash) {
        this.data= data;
        this.hash= hash;
    }


}


