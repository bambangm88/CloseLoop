package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelWallet {


    @SerializedName("wallet")
    private DataBalance  wallet;

    public DataBalance getWallet() {
        return wallet;
    }

    public void setWallet(DataBalance wallet) {
        this.wallet = wallet;
    }








}