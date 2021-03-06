package com.store.pawan.pawanstore.DAO;

import android.arch.lifecycle.ViewModel;

import com.store.pawan.pawanstore.entities.Account;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.completable.CompletableFromAction;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class AccountViewModel extends ViewModel {

    private final AccountDataSource accountDataSource;

    private Account mAccount;

    public AccountViewModel(AccountDataSource accountDataSource) {
        this.accountDataSource = accountDataSource;
    }



    public Flowable<List<Account>> getAllAccounts(int mode){
        return accountDataSource.getAccounts(mode);
    }

    public Completable updateUser(Account account){
        return new CompletableFromAction(()->{
            accountDataSource.insertOrUpdateAccount(account);
        });
    }

    public  Completable deleteUser(Account account){
        return  new CompletableFromAction(()->{
            accountDataSource.deleteAccount(account);
          }
        );
    }

}
