package com.product.yao.myapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.product.yao.myapp.R;

import java.util.List;

/**
 * Created by paichufang on 15-11-7.
 */
public class PTFLeftAdapter extends BaseAdapter{
    private List<AVObject> avObjects;
    private Context context;
    public PTFLeftAdapter(List<AVObject> avObjects,Context context){
        this.avObjects=avObjects;
        this.context=context;
    }
    @Override
    public int getCount() {
        return avObjects.size();
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
        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(context).inflate(R.layout.item_ptf_left_type,null,false);
            viewHolder.content=(TextView)convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.content.setText(avObjects.get(position).getString("firstTypeName"));
        return convertView;
    }
    class  ViewHolder{
        TextView content;
    }
}
