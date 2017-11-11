package com.store.pawan.pawanstore.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Dao
public interface PaymentDetailsDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addPaymentDetails(PaymentDetails paymentDetails);

    @Query("select * from paymentDetails where accountId= :accountId")
    Flowable<List<PaymentDetails>> getAllPaymentDetails(int accountId);


    @Delete
    void delete(PaymentDetails paymentDetails);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDetails(PaymentDetails paymentDetails);

}
