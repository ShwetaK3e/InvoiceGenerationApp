package com.store.pawan.pawanstore.Utility;

import com.store.pawan.pawanstore.DAO.AccountDAO;
import com.store.pawan.pawanstore.DAO.AccountDataSource;
import com.store.pawan.pawanstore.DAO.PaymentDetailsDAO;
import com.store.pawan.pawanstore.DAO.PaymentDetailsDataSource;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shwetakumar on 10/17/17.
 */

public class LocalDetailsDataSource implements PaymentDetailsDataSource {

    private final PaymentDetailsDAO paymentDetailsDAO;

    public LocalDetailsDataSource( PaymentDetailsDAO paymentDetailsDAO) {
        this.paymentDetailsDAO=paymentDetailsDAO;
    }

    @Override
    public Flowable<List<PaymentDetails>> getPaymentDetails(int accountId) {
        return paymentDetailsDAO.getAllPaymentDetails(accountId);
    }

    @Override
    public void insertOrUpdateDetails(PaymentDetails paymentDetails) {
        paymentDetailsDAO.addPaymentDetails(paymentDetails);
    }

    @Override
    public void deleteDetails(PaymentDetails paymentDetails) {
         paymentDetailsDAO.delete(paymentDetails);
    }
}
