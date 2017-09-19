package com.store.pawan.pawanstore.model;

/**
 * Created by shwetakumar on 8/5/17.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntryItem implements Parcelable
{

    @SerializedName("itemImageUri")
    @Expose
    private String itemImageUri;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("tax")
    @Expose
    private Double tax;

    public final static Parcelable.Creator<EntryItem> CREATOR = new Creator<EntryItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public EntryItem createFromParcel(Parcel in) {
            EntryItem instance = new EntryItem();
            instance.itemImageUri = ((String) in.readValue((String.class.getClassLoader())));
            instance.itemName = ((String) in.readValue((String.class.getClassLoader())));
            instance.qty = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.price = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.tax = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public EntryItem[] newArray(int size) {
            return (new EntryItem[size]);
        }

    };

    public String getItemImageUri() {
        return itemImageUri;
    }

    public void setItemImageUri(String itemImageUri) {
        this.itemImageUri = itemImageUri;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQty() {
        if(this.qty==null){
            return 0;
        }
        return qty;
    }

    public void setQty(Integer qty) {
            this.qty = qty;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(itemImageUri);
        dest.writeValue(itemName);
        dest.writeValue(qty);
        dest.writeValue(price);
        dest.writeValue(tax);
    }

    public int describeContents() {
        return 0;
    }

}