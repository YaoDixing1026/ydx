package com.product.yao.myapp.myview.typerightcoutent;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.product.yao.myapp.R;

/**
 * Created by paichufang on 15-11-9.
 */
public class TypeFragmentRightContent extends LinearLayout {


    public TypeFragmentRightContent(Context context) {
        super(context);
        init(context);
    }

    public TypeFragmentRightContent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TypeFragmentRightContent(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private View view;
    private Context context;
    private RelativeLayout rlTitle;
    private TextView leftText;
    private TextView more;
    private LinearLayout oneItem;
    private void init(Context context){
        this.context=context;
        view= LayoutInflater.from(context).inflate(R.layout.item_type_content,this);
        leftText=(TextView)view.findViewById(R.id.second_type_name);
        more=(TextView)view.findViewById(R.id.item_more);
        oneItem=(LinearLayout)view.findViewById(R.id.content);
    }

    /**
     * set left title
     * @param text text
     * @param type size type
     * @param size size
     * @param color color
     */
    public void setLeftText(String text,int type,int size,int color){
        leftText.setText(text);
        if(type!=0&&size!=0) {
            leftText.setTextSize(type, size);
        }
        if(color!=0) {
            leftText.setTextColor(color);
        }
    }
    /**
     * set more title
     * @param text text
     * @param type size type
     * @param size size
     * @param color color
     */
    public void setRightText(String text,int type,int size,int color){
        more.setText(text);
        if(type!=0&&size!=0) {
            more.setTextSize(type, size);
        }
        if(color!=0) {
            more.setTextColor(color);
        }
    }

    /**
     * set more background
     * @param res res id
     */
    public void setMoreBackground(int res){
        more.setBackgroundResource(res);
    }

    /**
     *  add a line content
     * @param view
     */
    public void addContent(View view){
        oneItem.addView(view);
    }
}
