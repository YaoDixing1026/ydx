package com.product.yao.myapp.myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.yao.myapp.R;

/**
 * Created by paichufang on 15-12-11.
 */
public class MyScrollFooterView extends View {

    private LinearLayout view;
    private Context mContext;
    public MyScrollFooterView(Context context) {
        super(context);
        view=(LinearLayout) LayoutInflater.from(context).inflate(R.layout.scroll_footer,null);
        mContext=context;
    }


}
