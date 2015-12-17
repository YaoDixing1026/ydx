package com.product.yao.myapp.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.product.yao.myapp.R;

/**
 * Created by paichufang on 15-12-8.
 */
public class MyScrollView extends ScrollView {

    private MyScrollFooterView footerView;
    private Context mContext;
    public MyScrollView(Context context) {
        super(context);
        mContext=context;
        init();
    }





    private void init(){

        footerView=new MyScrollFooterView(mContext);
    }
}
