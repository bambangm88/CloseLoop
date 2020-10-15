package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMutasi {




    @SerializedName("id")
    private String id;

    @SerializedName("account")
    private ModelWallet account;

    @SerializedName("trxDate")
    private String trxDate;

    @SerializedName("tagId")
    private String tagId;

    @SerializedName("amount")
    private String amount;

    @SerializedName("referenceId")
    private String referenceId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ModelWallet getAccount() {
        return account;
    }

    public void setAccount(ModelWallet account) {
        this.account = account;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }


}