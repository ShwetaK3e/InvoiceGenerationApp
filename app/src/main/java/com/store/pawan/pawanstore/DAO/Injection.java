package com.store.pawan.pawanstore.DAO;

import android.content.Context;

import com.store.pawan.pawanstore.Utility.LocalAccountDataSource;
import com.store.pawan.pawanstore.Utility.LocalDetailsDataSource;
import com.store.pawan.pawanstore.Utility.PStoreDataBase;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class Injection {

    public static AccountDataSource provideAccountDataSource(Context context) {
        PStoreDataBase database = PStoreDataBase.getPStoreDatabaseInstance(context);
        return new LocalAccountDataSource(database.AccountDao());
    }

    public static PaymentDetailsDataSource provideDetailsDataSource(Context context) {
        PStoreDataBase database = PStoreDataBase.getPStoreDatabaseInstance(context);
        return new LocalDetailsDataSource(database.PaymentDetailsDAO());
    }

    public static AccountViewModelFactory provideAccountViewModelFactory(Context context) {
        AccountDataSource dataSource = provideAccountDataSource(context);
        return new AccountViewModelFactory(dataSource);
    }

    public static DetailsViewModelFactory provideDetailsViewModelFactory(Context context) {
        PaymentDetailsDataSource dataSource = provideDetailsDataSource(context);
        return new DetailsViewModelFactory(dataSource);
    }
}
