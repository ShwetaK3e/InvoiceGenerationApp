package com.store.pawan.pawanstore.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.store.pawan.pawanstore.R;

/**
 * Created by cas on 26-07-2017.
 */

public class InstrumentAdapter extends RecyclerView.Adapter<InstrumentAdapter.ViewHolder> {


    Context context;
    OnMyItemClickListener onMyItemClickListener;


    public InstrumentAdapter(Context context, OnMyItemClickListener onMyItemClickListener){
        this.context=context;
        this.onMyItemClickListener=onMyItemClickListener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.instrument_type_list_grid,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       /* Glide.with(context).load(Commons.itemImage_map.get(position)).into(holder.item_img);
        holder.item_name.setText(Commons.itemName_map.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyItemClickListener.onClick(position);
            }
        });

        holder.item_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMyItemClickListener.onClick(position);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return 0;
                //Commons.itemImage_map.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView item_img;
        TextView item_name;


        public ViewHolder(View itemView) {
            super(itemView);
           // item_img=(ImageView)itemView.findViewById(R.id.item_img);
           // item_name=(PStoreTextViewNormal)itemView.findViewById(R.id.item_name);

        }
    }

    public interface OnMyItemClickListener{
        void onClick(int pos);
    }


}
