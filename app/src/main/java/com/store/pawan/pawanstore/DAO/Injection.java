package com.store.pawan.pawanstore.DAO;

import android.content.Context;

import com.store.pawan.pawanstore.Utility.LocalPStoreDataSource;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class Injection {

    public static AccountDataSource provideUserDataSource(Context context) {
        PStoreDataBase database = PStoreDataBase.getPStoreDatabaseInstance(context);
        return new LocalPStoreDataSource(database.AccountDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        AccountDataSource dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
