package com.store.pawan.pawanstore.DAO;

import com.store.pawan.pawanstore.DAO.AccountDAO;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shwetakumar on 10/17/17.
 */

public interface AccountDataSource {

    Flowable<List<Account>> getAccounts(int mode);

    void insertAccount(Account account);

    void updateAccount(Account account);

    void deleteAccount(Account account);



}
