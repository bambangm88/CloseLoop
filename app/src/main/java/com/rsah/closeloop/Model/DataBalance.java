package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

public class DataBalance {

    @SerializedName("id")
    private int id;

    @SerializedName("walletName")
    private String walletName;

    @SerializedName("orgId")
    private int orgId;

    @SerializedName("walletAccountId")
    private int walletAccountId;

    @SerializedName("suspendAccountId")
    private int suspendAccountId;

    @SerializedName("cashBankId")
    private int cashBankId;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWalletName() {
        return walletName;
    }

    public void setWalletName(String walletName) {
        this.walletName = walletName;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public int getWalletAccountId() {
        return walletAccountId;
    }

    public void setWalletAccountId(int walletAccountId) {
        this.walletAccountId = walletAccountId;
    }

    public int getSuspendAccountId() {
        return suspendAccountId;
    }

    public void setSuspendAccountId(int suspendAccountId) {
        this.suspendAccountId = suspendAccountId;
    }

    public int getCashBankId() {
        return cashBankId;
    }

    public void setCashBankId(int cashBankId) {
        this.cashBankId = cashBankId;
    }



}