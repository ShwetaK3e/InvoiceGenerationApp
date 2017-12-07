package com.store.pawan.pawanstore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewBold;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.entities.Account;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cas on 26-07-2017.
 */

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {


    Context context;
    OnMyItemClickListener onMyItemClickListener;

    List<Account> accounts=new LinkedList<>();


    public AccountAdapter(Context context, List<Account> accounts, OnMyItemClickListener onMyItemClickListener){
        this.context=context;
        this.accounts=accounts;
        this.onMyItemClickListener=onMyItemClickListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_account_grid,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(Uri.parse("android.resource://com.store.pawan.pawanstore/drawable/"+ R.drawable.ic_guitar).toString()).into(holder.acc_img);
        holder.acc_name.setText(accounts.get(position).getName());
        holder.amount.setText("Rs. "+String.format("%.1f",accounts.get(position).getAmount()));
        holder.itemView.setOnClickListener(view -> onMyItemClickListener.onClick(position,null));



        holder.acc_del_layout.setOnClickListener(click->{
            onMyItemClickListener.onClick(position,"delete");
        });

        holder.acc_del.setOnClickListener(click->{
            onMyItemClickListener.onClick(position,"delete");
        });

        holder.acc_img_layout.setOnClickListener(click-> {
            onMyItemClickListener.onClick(position,"history");

        });

        holder.acc_img.setOnClickListener(click-> {
            onMyItemClickListener.onClick(position,"history");
        });
    }

    @Override
    public int getItemCount() {
        return accounts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout acc_img_layout;
        ImageView acc_img;
        TextView acc_name;
        TextView amount;
        LinearLayout acc_del_layout;
        ImageButton acc_del;



        public ViewHolder(View itemView) {
            super(itemView);
            acc_img_layout=itemView.findViewById(R.id.accnt_img_layout);
            acc_img=itemView.findViewById(R.id.acc_img);
            acc_name=(PStoreTextViewBold)itemView.findViewById(R.id.acc_name);
            amount=(PStoreTextViewBold)itemView.findViewById(R.id.amnt);
            acc_del_layout=itemView.findViewById(R.id.acc_del_layout);
            acc_del=itemView.findViewById(R.id.acc_del);
        }
    }

    public interface OnMyItemClickListener{
        void onClick(int pos, String action);
    }




}
