package com.store.pawan.pawanstore.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Entity(tableName = "paymentDetails",
        foreignKeys = {
                @ForeignKey(
                        entity = Account.class,
                        parentColumns = "id",
                        childColumns = "accountId",
                        onDelete = ForeignKey.CASCADE
                )},
        indices = { @Index(value = "id")}
)

public class PaymentDetails {



    @PrimaryKey(autoGenerate = true)
    public  int  id ;

    public int accountId;

    public String date;

    public int mode; // 0 : lend  1: pay

    public  float amount;

    public String comment;




    public PaymentDetails(int accountId, String date, int mode, float amount, String comment) {
        this.accountId = accountId;
        this.date=date;
        this.mode = mode;
        this.amount = amount;
        this.comment=comment;
    }


    public int getId() {
        return id;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getDate() {
        return date;
    }

    public int getMode() {
        return mode;
    }

    public float getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }
}
