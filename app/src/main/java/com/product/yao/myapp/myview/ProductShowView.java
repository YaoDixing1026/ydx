package com.product.yao.myapp.myview;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.product.yao.myapp.R;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

/**
 * Created by paichufang on 15-12-9.
 */
public class ProductShowView extends LinearLayout {
    private Context context;
    private LinearLayout view;
    private LayoutInflater inflater;
    private LinearLayout leftLayout;
    private LinearLayout rightLayout;
    private LinearLayout leftImageLayout;
    private LinearLayout rightImageLayout;
    private CubeImageView leftImageView;
    private CubeImageView rightImageView;
    private TextView leftName;
    private TextView rightName;
    private TextView leftPrice;
    private TextView rightPrice;
    private onLeftClickListener leftListener;
    public interface onLeftClickListener{
       void onLeftClick();
    }
    private onRightClickListener rightListener;
    public interface onRightClickListener{
        void onRightClick();
    }
    public void setLeftClick(onLeftClickListener leftListener){
        this.leftListener=leftListener;
    }
    public void setRightClick(onRightClickListener rightListener){
        this.rightListener=rightListener;
    }

    public ProductShowView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context){
        this.context=context;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=(LinearLayout)inflater.inflate(R.layout.item_product_show, this);
        leftLayout=(LinearLayout)view.findViewById(R.id.item_product_left_layout);
        rightLayout=(LinearLayout)view.findViewById(R.id.item_product_right_layout);
        leftImageView=(CubeImageView)view.findViewById(R.id.left_image);
        rightImageView=(CubeImageView)view.findViewById(R.id.right_image);
        leftName=(TextView)view.findViewById(R.id.left_product_name);
        rightName=(TextView)view.findViewById(R.id.right_product_name);
        leftPrice=(TextView)view.findViewById(R.id.left_price);
        rightPrice=(TextView)view.findViewById(R.id.right_price);
        leftImageLayout=(LinearLayout)view.findViewById(R.id.left_image_layout);
        rightImageLayout=(LinearLayout)view.findViewById(R.id.right_image_layout);
        leftImageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                leftListener.onLeftClick();
            }
        });
        rightImageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rightListener.onRightClick();
            }
        });
    }
    public void setLeftAndRightImageLayoutWH(int width,int height){
        LinearLayout.LayoutParams params =new LinearLayout.LayoutParams(width,height);
        leftImageLayout.setLayoutParams(params);
        rightImageLayout.setLayoutParams(params);
    }
    public void setLeftLayoutVisiable(boolean flag){
        if(flag){
            leftLayout.setVisibility(VISIBLE);
        }else {
            leftLayout.setVisibility(GONE);
        }

    }
    public void setRightLayoutVisiable(boolean flag){
        if(flag){
            rightLayout.setVisibility(VISIBLE);
        }else {
            rightLayout.setVisibility(GONE);
        }

    }
    public void setLeftImage(ImageLoader imageLoader,String url){
        leftImageView.loadImage(imageLoader,url);
    }
    public void setRightImage(ImageLoader imageLoader,String url){
        rightImageView.loadImage(imageLoader,url);
    }
    public void setLeftProductName(String s,int size){
        leftName.setText(s);
        if(size==0){

        }else {
            leftName.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        }
    }
    public void setRightProductName(String s,int size){
        rightName.setText(s);
        if(size==0){

        }else {
            rightName.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        }
    }
    public void setLeftPrice(String s,int size){
        leftPrice.setText(s);
        if(size==0){

        }else {
            leftPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        }
    }
    public void setRightPrice(String s,int size){
        rightPrice.setText(s);
        if(size==0){

        }else {
            rightPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        }
    }
    public void setPriceColor(int color){
        leftPrice.setTextColor(color);
        rightPrice.setTextColor(color);
    }
}
