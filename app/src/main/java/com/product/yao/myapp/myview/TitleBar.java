package com.product.yao.myapp.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.product.yao.myapp.R;


/**
 * Created by ydx on 8/20/15.
 */
public class TitleBar extends LinearLayout implements OnClickListener {
    private Context mContext;
    private RelativeLayout layout;
    private LinearLayout mBack;
    private ImageView mBackImage;
    private TextView mTitle;
    private ImageView mMore;
    private TextView line;
    private onBtnClickListener mListener;
    private View mTitleBar;
    public interface onBtnClickListener {
         void backClick();
         void moreClick();
         void titleClick();
    }
    public void setOnBtnClickListener(onBtnClickListener mListener){
        this.mListener=mListener;
    }
    public TitleBar(Context mContext) {
        super(mContext);
        this.mContext=mContext;
        init();
    }
    public TitleBar(Context mContext, AttributeSet attrs) {
        super(mContext, attrs);
        this.mContext=mContext;
        init();

    }
    private void init(){
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTitleBar= inflater.inflate(R.layout.titlebar,this);
        layout=(RelativeLayout)mTitleBar.findViewById(R.id.layout);
        mBack=(LinearLayout)mTitleBar.findViewById(R.id.back);
        mBackImage=(ImageView)mTitleBar.findViewById(R.id.back_image);
        mTitle=(TextView)mTitleBar.findViewById(R.id.title);
        mMore=(ImageView)mTitleBar.findViewById(R.id.more);
        line=(TextView)mTitleBar.findViewById(R.id.line);
        mBack.setOnClickListener(this);
        mTitle.setOnClickListener(this);
        mMore.setOnClickListener(this);
    }
    public void setBackImageResource(int resource){
        mBackImage.setImageResource(resource);
    }
    public void setMoreImageResource(int resource){
        mMore.setImageResource(resource);
    }
    public void setMoreImageVisiable(boolean flag){
        if (flag){

        }
        else {
            mMore.setVisibility(INVISIBLE);
        }
    }
    public void setTitleText(String text){
        mTitle.setText(text);
    }
    public void setTitleTextColor(int color){
        mTitle.setTextColor(color);
    }
    public void setTitleSize(float size){
        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }//dp
    public void setLayoutBackground(int color){
        layout.setBackgroundColor(color);
    }
    public void setLayoutMargenTop(int margenHeight){
        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin=margenHeight;
        layout.setLayoutParams(layoutParams);
    }
    public void setLayoutHeight(int height){
        LayoutParams layoutParams=new LayoutParams(LayoutParams.MATCH_PARENT, height);
        mTitleBar.setLayoutParams(layoutParams);
    }
    public void setLayoutHeight(RelativeLayout.LayoutParams params,int height){
         params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
        mTitleBar.setLayoutParams(params);
    }
    public void setMoreVisible(boolean flag){
        if (flag){
            mMore.setVisibility(VISIBLE);
        }else {
            mMore.setVisibility(INVISIBLE);
        }
    }
    public void setLineVisible(boolean flag){
        if(flag){
            line.setVisibility(VISIBLE);
        }else {
            line.setVisibility(GONE);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                if(null!=mListener){
                    mListener.backClick();
                }
                break;
            case R.id.more:
                if(null!=mListener) {
                    mListener.moreClick();
                }
                break;
            case R.id.title:
                if(null!=mListener) {
                    mListener.titleClick();
                }
                break;
        }
    }
}
