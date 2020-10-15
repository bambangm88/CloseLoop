package com.rsah.closeloop.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelReport {

    @SerializedName("content")
    private List<ModelPayment> content;


    @SerializedName("totalElements")
    private String totalElements;


    public List<ModelPayment> getContent() {
        return content;
    }

    public void setContent(List<ModelPayment> content) {
        this.content = content;
    }

    public String getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(String totalElements) {
        this.totalElements = totalElements;
    }


}