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
import com.store.pawan.pawanstore.model.EntryItem;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by cas on 26-07-2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {


    Context context;
    OnMyItemClickListener onMyItemClickListener;

    List<EntryItem> items=new LinkedList<>();


    public ItemAdapter(Context context,List<EntryItem> items, OnMyItemClickListener onMyItemClickListener){
        this.context=context;
        this.items=items;
        this.onMyItemClickListener=onMyItemClickListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.add_product_grid,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(Uri.parse("android.resource://com.store.pawan.pawanstore/drawable/"+ R.drawable.ic_guitar).toString()).into(holder.item_img);
        holder.item_name.setText("MUSIC");
        holder.quantity.setText(String.valueOf(items.get(position).getQty()));
        holder.price.setText("Rs. "+String.valueOf(items.get(position).getPrice()));
        holder.itemView.setOnClickListener(view -> onMyItemClickListener.onClick(position));

        holder.item_del_layout.setOnClickListener(click->{
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,items.size());
        });

        holder.item_del.setOnClickListener(click->{
            items.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,items.size());
        });
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
        LinearLayout item_del_layout;
        ImageButton item_del;



        public ViewHolder(View itemView) {
            super(itemView);
            item_img=(ImageView)itemView.findViewById(R.id.item_img);
            item_name=(PStoreTextViewBold)itemView.findViewById(R.id.item_name);
            quantity=(PStoreTextViewBold)itemView.findViewById(R.id.item_qnty);
            price=(PStoreTextViewBold)itemView.findViewById(R.id.item_price);
            item_del_layout=(LinearLayout)itemView.findViewById(R.id.item_del_layout);
            item_del=(ImageButton)itemView.findViewById(R.id.item_del);
        }
    }

    public interface OnMyItemClickListener{
        void onClick(int pos);
    }




}
