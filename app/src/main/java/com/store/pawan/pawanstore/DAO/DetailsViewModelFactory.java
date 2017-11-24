package com.store.pawan.pawanstore.DAO;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class DetailsViewModelFactory implements ViewModelProvider.Factory{

    private final PaymentDetailsDataSource paymentDetailsDataSource;

    public DetailsViewModelFactory(  PaymentDetailsDataSource paymentDetailsDataSource) {
        this.paymentDetailsDataSource = paymentDetailsDataSource;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaymentDetailsViewModel.class)) {
            return (T) new PaymentDetailsViewModel(paymentDetailsDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
