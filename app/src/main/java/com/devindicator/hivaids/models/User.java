package com.devindicator.hivaids.models;

/**
 * Created by bangujo on 05/03/2017.
 */

public class User {
    private int id;
    private String names;
    private String email;
    private boolean btn15and35;
    private boolean withHIV;
    private boolean kenyanResident;
    private boolean fearDisclosing;
    private boolean fearIsolation;
    private boolean fearGossip;
    private boolean preferHIVVendor;
    private boolean fearPositiveResult;

    public User() {
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isBtn15and35() {
        return btn15and35;
    }

    public void setBtn15and35(boolean btn15and35) {
        this.btn15and35 = btn15and35;
    }

    public boolean isKenyanResident() {
        return kenyanResident;
    }

    public void setKenyanResident(boolean kenyanResident) {
        this.kenyanResident = kenyanResident;
    }

    public boolean isWithHIV() {
        return withHIV;
    }

    public void setWithHIV(boolean withHIV) {
        this.withHIV = withHIV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFearDisclosing() {
        return fearDisclosing;
    }

    public void setFearDisclosing(boolean fearDisclosing) {
        this.fearDisclosing = fearDisclosing;
    }

    public boolean isFearIsolation() {
        return fearIsolation;
    }

    public void setFearIsolation(boolean fearIsolation) {
        this.fearIsolation = fearIsolation;
    }

    public boolean isFearGossip() {
        return fearGossip;
    }

    public void setFearGossip(boolean fearGossip) {
        this.fearGossip = fearGossip;
    }

    public boolean isPreferHIVVendor() {
        return preferHIVVendor;
    }

    public void setPreferHIVVendor(boolean preferHIVVendor) {
        this.preferHIVVendor = preferHIVVendor;
    }

    public boolean isFearPositiveResult() {
        return fearPositiveResult;
    }

    public void setFearPositiveResult(boolean fearPositiveResult) {
        this.fearPositiveResult = fearPositiveResult;
    }
}
