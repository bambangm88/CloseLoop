package com.rsah.closeloop.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseDataBalance {

    @SerializedName("id")
    private int id;

    @SerializedName("personId")
    private int personId;

    @SerializedName("balance")
    private String balance;

    @SerializedName("error")
    private String error;

    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }









}






