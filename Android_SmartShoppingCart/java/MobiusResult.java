package com.example.shoppingcart;

import com.google.gson.annotations.SerializedName;

    public class MobiusResult {
        @SerializedName("m2m:cin")
        private MobiusItem item;


    public MobiusItem getItem() {
        return item;
    }

    public void setItem(MobiusItem item) {
        this.item = item;
    }
}

