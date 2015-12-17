package com.product.yao.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by paichufang on 15-12-8.
 */
public class ProductListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> contents;
    public ProductListAdapter(Context mContext,List<String> contents){
        this.mContext=mContext;
        this.contents=contents;
    }

    @Override
    public int getCount() {
        return contents==null?0:contents.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView=new TextView(mContext);
        textView.setText(contents.get(position));
        return textView;
    }
}
