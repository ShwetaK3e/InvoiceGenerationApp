package com.store.pawan.pawanstore.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.store.pawan.pawanstore.entities.Account;

import java.util.List;

import rx.Observable;

/**
 * Created by shwetakumar on 9/26/17.
 */

@Dao
public interface AccountDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Observable<Void> addAccount(Account account);

    @Query("select * from account where mode= :mode")
    Observable<List<Account>> getAllAccounts(int mode);

    @Query("select paid_amount from account where id=:id")
    Observable<Float> getPaidAmount(int id);

    @Query("select amount from account where id=:id")
    Observable<Float> getAmount(int id);

    @Delete
    Observable<Void> delete(Account account);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Observable<Void> updateAccount(Account account);

}
