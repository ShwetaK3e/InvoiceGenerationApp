package com.store.pawan.pawanstore.DAO;

import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by shwetakumar on 11/12/17.
 */

public interface PaymentDetailsDataSource {

    Flowable<List<PaymentDetails>> getPaymentDetails(int accountId);

    void insertOrUpdateDetails(PaymentDetails paymentDetails);

    void deleteDetails(PaymentDetails paymentDetails);
}
