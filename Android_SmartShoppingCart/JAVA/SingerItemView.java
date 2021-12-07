package com.example.shoppingcart;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingerItemView extends LinearLayout {
    TextView textView;
    TextView textView2;
    public SingerItemView(Context context) {
        super(context);
        init(context);
    }

    public SingerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private  void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.item_custom,this,true);

        textView = (TextView)findViewById(R.id.Product_name);
        textView2 = (TextView)findViewById(R.id.Price);
    }

    public  void setName(String name){
        textView.setText(name);
    }
    public void setPrice(String price){
        textView2.setText(price);
    }
}
