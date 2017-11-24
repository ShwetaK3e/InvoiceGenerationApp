package com.store.pawan.pawanstore.DAO;

import android.arch.lifecycle.ViewModel;

import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.internal.operators.completable.CompletableFromAction;

/**
 * Created by shwetakumar on 11/12/17.
 */

public class PaymentDetailsViewModel extends ViewModel{


    private final PaymentDetailsDataSource paymentDetailsDataSource;

    private Account mAccount;

    public PaymentDetailsViewModel( PaymentDetailsDataSource paymentDetailsDataSource) {
        this.paymentDetailsDataSource = paymentDetailsDataSource;

    }

    public Flowable<List<PaymentDetails>> getAllDeatails(int accountId){
        return paymentDetailsDataSource.getPaymentDetails(accountId);
    }

    public Completable updateUser(PaymentDetails paymentDetails){
        return new CompletableFromAction(()->{
            paymentDetailsDataSource.insertOrUpdateDetails(paymentDetails);
        });
    }

    public  Completable deleteUser(PaymentDetails paymentDetails){
        return  new CompletableFromAction(()->{
            paymentDetailsDataSource.deleteDetails(paymentDetails);
        }
        );
    }
}
