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


}
