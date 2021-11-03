package com.example.shoppingcart;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class MobiusItem implements Serializable {
    String pi;
    String ri;
    int ty;
    String ct;
    String rn;
    String lt;
    String et;
    ArrayList<String> lbl = new ArrayList<>();
    String api;
    String aei;
    ArrayList<String> poa = new ArrayList<>();
    boolean rr;

    public MobiusItem(String pi, String ri, Integer ty, String ct, String rn, String lt, String et, ArrayList lbl){
        this.pi = pi;
        this.ri = ri;
        this.ty = ty;
        this.ct = ct;
        this.rn = rn;
        this.lt = lt;
        this.et = et;
        this.lbl = lbl;
    }
}




