package com.store.pawan.pawanstore.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.store.pawan.pawanstore.entities.Account;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import rx.Observable;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Dao
public interface AccountDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAccount(Account account);

    @Query("select * from account where mode= :mode")
    Flowable<List<Account>> getAllAccounts(int mode);

    @Query("select paid_amount from account where id=:id")
    Flowable<Float> getPaidAmount(int id);

    @Query("select amount from account where id=:id")
    Flowable<Float> getAmount(int id);

    @Delete
    void delete(Account account);

    @Update
    void updateAccount(Account account);

}
