package com.store.pawan.pawanstore.DAO;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.store.pawan.pawanstore.entities.Account;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class ViewModelFactory implements ViewModelProvider.Factory{

    private final AccountDataSource accountDataSource;

    public ViewModelFactory(AccountDataSource accountDataSource) {
        this.accountDataSource = accountDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AccountViewModel.class)) {
            return (T) new AccountViewModel(accountDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
