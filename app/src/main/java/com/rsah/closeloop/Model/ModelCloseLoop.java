package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

public class ModelCloseLoop {

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private String id;

    @SerializedName("payableId")
    private String payableId;


    @SerializedName("tenantId")
    private String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public String getPayableId() {
        return payableId;
    }

    public void setPayableId(String payableId) {
        this.payableId = payableId;
    }








}


