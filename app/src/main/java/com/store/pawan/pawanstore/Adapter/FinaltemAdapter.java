package com.store.pawan.pawanstore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.store.pawan.pawanstore.CustomWidgets.PStoreTextViewBold;
import com.store.pawan.pawanstore.R;
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cas on 26-07-2017.
 */

public class FinaltemAdapter extends RecyclerView.Adapter<FinaltemAdapter.ViewHolder> {


    Context context;
    List<EntryItem> items=new LinkedList<>();


    public FinaltemAdapter(Context context, List<EntryItem> items){
        this.context=context;
        this.items=items;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.final_product_grid,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(Uri.parse("android.resource://com.store.pawan.pawanstore/drawable/"+ R.drawable.ic_guitar).toString()).into(holder.item_img);
        holder.item_name.setText("MUSIC");
        holder.quantity.setText(String.valueOf(items.get(position).getQty()));
        holder.price.setText("Rs. "+String.valueOf(items.get(position).getPrice()));

        double percentage=items.get(position).getTax()/100;
        double original_price=items.get(position).getPrice()-(percentage*items.get(position).getPrice());
        double cgst=(items.get(position).getPrice()-original_price)/2;


        original_price=Math.round(original_price);
        cgst=Math.ceil(cgst);

        holder.original_price.setText(String.valueOf(original_price));
        holder.cgst.setText(String.valueOf(cgst));
        holder.sgst.setText(String.valueOf(cgst));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item_name;
        TextView quantity;
        TextView price;
        TextView original_price;
        TextView cgst;
        TextView sgst;



        public ViewHolder(View itemView) {
            super(itemView);
            item_img=(ImageView)itemView.findViewById(R.id.item_img);
            item_name=(PStoreTextViewBold)itemView.findViewById(R.id.item_name);
            quantity=(PStoreTextViewBold)itemView.findViewById(R.id.item_qnty);
            price=(PStoreTextViewBold)itemView.findViewById(R.id.item_price);
            original_price=(PStoreTextViewBold)itemView.findViewById(R.id.original);
            cgst=(PStoreTextViewBold)itemView.findViewById(R.id.cgst);
            sgst=(PStoreTextViewBold)itemView.findViewById(R.id.sgst);

        }
    }




}
