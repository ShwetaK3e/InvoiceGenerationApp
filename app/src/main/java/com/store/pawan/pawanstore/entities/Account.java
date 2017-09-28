package com.store.pawan.pawanstore.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Entity
public class Account {

    @PrimaryKey(autoGenerate = true)
    public final int  id;

    public String name;

    public int mode;

    public  float amount;

    public float  paid_amount;


    public Account(int id, String name, int mode, float amount,float paid_amount) {
        this.id = id;
        this.name = name;
        this.mode = mode;
        this.amount = amount;
        this.paid_amount=paid_amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getPaid_amount() {
        return paid_amount;
    }

    public void setPaid_amount(float paid_amount) {
        this.paid_amount = paid_amount;
    }
}
