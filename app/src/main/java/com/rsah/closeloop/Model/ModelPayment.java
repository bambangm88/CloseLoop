package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPayment {

    @SerializedName("amount")
    private String amount;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("merchantId")
    private String merchantId;

    @SerializedName("merchantReferenceNumber")
    private String merchantReferenceNumber;

    @SerializedName("settlementStatus")
    private String settlementStatus;

    @SerializedName("siswaId")
    private String siswaId;

    @SerializedName("trxDate")
    private String trxDate;

    @SerializedName("closeLoop")
    private ModelCloseLoop closeLoop;

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantReferenceNumber() {
        return merchantReferenceNumber;
    }

    public void setMerchantReferenceNumber(String merchantReferenceNumber) {
        this.merchantReferenceNumber = merchantReferenceNumber;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public String getSiswaId() {
        return siswaId;
    }

    public void setSiswaId(String siswaId) {
        this.siswaId = siswaId;
    }

    public String getTrxDate() {
        return trxDate;
    }

    public void setTrxDate(String trxDate) {
        this.trxDate = trxDate;
    }

    public ModelCloseLoop getCloseLoop() {
        return closeLoop;
    }

    public void setCloseLoop(ModelCloseLoop closeLoop) {
        this.closeLoop = closeLoop;
    }




    public ModelPayment(String amount , ModelCloseLoop closeLoop , String description , String id , String merchantId , String merchantReferenceNumber , String settlementStatus , String siswaId , String trxDate) {
        this.amount= amount;
        this.closeLoop= closeLoop;
        this.description= description;
        //this.id= id;
        this.merchantId= merchantId;
        this.merchantReferenceNumber= merchantReferenceNumber;
        this.settlementStatus= settlementStatus;
        this.siswaId= siswaId;
        this.trxDate= trxDate;
    }


}


