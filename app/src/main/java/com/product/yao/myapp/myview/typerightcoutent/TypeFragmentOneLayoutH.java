package com.product.yao.myapp.myview.typerightcoutent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.product.yao.myapp.R;

/**
 * Created by paichufang on 15-11-9.
 */
public class TypeFragmentOneLayoutH extends LinearLayout {

    public TypeFragmentOneLayoutH(Context context) {
        super(context);
        init(context);
    }

    public TypeFragmentOneLayoutH(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TypeFragmentOneLayoutH(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private Context context;
    private View view;
    private LinearLayout llH;
    private void init(Context context){
        this.context=context;
        view= LayoutInflater.from(context).inflate(R.layout.item_type_h,this);
        llH=(LinearLayout)view.findViewById(R.id.item_layout);
    }
    public void addItem(View view){
        llH.addView(view);
    }
    public void setLayoutHW(int width,int height){
        LayoutParams params=new LayoutParams(width,height);
        llH.setLayoutParams(params);
    }
    public void setLayoutPadding(int left,int top,int right,int bottom){
        llH.setPadding(left,top,right,bottom);
    }

}
