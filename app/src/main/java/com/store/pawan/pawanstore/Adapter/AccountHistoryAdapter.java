package com.store.pawan.pawanstore.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.Utility.Constants;
import com.store.pawan.pawanstore.entities.PaymentDetails;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cas on 26-07-2017.
 */

public class AccountHistoryAdapter extends RecyclerView.Adapter<AccountHistoryAdapter.ViewHolder> {


    Context context;
    List<PaymentDetails> payments=new LinkedList<>();


    public AccountHistoryAdapter(Context context, List<PaymentDetails> payments){
        this.context=context;
        this.payments=payments;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.accnt_history_grid,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PaymentDetails payment=payments.get(position);

        holder.payment_date.setText(payment.getDate());
        holder.payment_desc.setText(payment.getComment());
        holder.installment_amnt.setText("Rs. "+String.valueOf(payment.getAmount()));
        if(payment.getMode()== Constants.PaymentMode.PAID.getPayment_mode()){
            holder.installment_amnt.setTextColor(context.getResources().getColor(android.R.color.holo_green_light));
        }else{
            holder.installment_amnt.setTextColor(context.getResources().getColor(R.color.red));

        }

    }

    @Override
    public int getItemCount() {
        return payments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView installment_amnt;
        TextView payment_date;
        TextView payment_desc;




        public ViewHolder(View itemView) {
            super(itemView);
            installment_amnt=itemView.findViewById(R.id.payment_amnt);
            payment_date=itemView.findViewById(R.id.payment_date);
            payment_desc=itemView.findViewById(R.id.payment_desc);

        }
    }




}
