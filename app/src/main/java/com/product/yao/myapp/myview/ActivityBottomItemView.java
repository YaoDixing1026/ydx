package com.product.yao.myapp.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.product.yao.myapp.R;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;

/**
 * Created by ydx on 15-11-3.
 */
public class ActivityBottomItemView extends RelativeLayout {

    private Context context;
    private LayoutInflater inflater;
    private RelativeLayout view;
    private LinearLayout father;
    private CubeImageView imageView;
    private TextView textView;

    public ActivityBottomItemView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public ActivityBottomItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    public ActivityBottomItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }
    private void init(){
        inflater=LayoutInflater.from(context);
        view=(RelativeLayout)inflater.inflate(R.layout.activity_bottom_item,this);
        father=(LinearLayout)view.findViewById(R.id.bottom_father);
        imageView=(CubeImageView)view.findViewById(R.id.bottom_image);
        textView=(TextView)view.findViewById(R.id.bottom_text);
    }

    /**
     * 设置布局内部对齐方式
     * @param gravity 对齐方式
     */
    public void setFatherGravity(int gravity){
        father.setGravity(gravity);
    }

    public void setFatherWH(int width,int height){
        LayoutParams params=new LayoutParams(width,height);
        father.setLayoutParams(params);
    }
    /**
     * 通过id设置图片
     * @param id resId
     * @param scaleType scaleType
     */
    public void setImageView(int id,ImageView.ScaleType scaleType){
        imageView.setImageResource(id);
        imageView.setScaleType(scaleType);
    }
    /**
     * 通过id设置图片
     * @param id drawable
     */
    public void setImageView(int id){
        imageView.setImageResource(id);
    }

    /**
     * 通过bitmap设置图片
     * @param bitmap bitmap
     * @param scaleType scaleType
     */
    public void setImageView(Bitmap bitmap,ImageView.ScaleType scaleType){
        imageView.setImageBitmap(bitmap);
        imageView.setScaleType(scaleType);
    }
    public void setImageViewByUrl(ImageLoader imageLoader,String url){
        imageView.loadImage(imageLoader, url);
    }
    public void setImageViewWH(int width,int height){
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width,height);
        imageView.setLayoutParams(params);
    }
    /**
     * 通过bitmap设置图片
     * @param bitmap bitmap
     */
    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    /**
     * 设置TEXTVIEW
     * @param text text 不能为null
     * @param sizeType px dp or 0
     * @param textSize size or 0
     * @param textColor color or 0
     */
    public void setText(String text,int sizeType,int textSize,int textColor){
        textView.setText(text);
        if(sizeType!=0&&textSize!=0){
            textView.setTextSize(sizeType,textSize);
        }
        if(textColor!=0){
            textView.setTextColor(textColor);
        }
    }
}
