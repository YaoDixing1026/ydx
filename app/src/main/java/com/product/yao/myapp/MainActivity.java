package com.product.yao.myapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.fragment.ptf.ProductRightFragment;
import com.product.yao.myapp.fragment.ptf.ProductTypeFragment;
import com.product.yao.myapp.myview.ActivityBottomItemView;
import com.product.yao.myapp.myview.TitleBar;
import com.product.yao.myapp.utils.WHUtil;

import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;


public class MainActivity extends BaseActivity {
    private LinearLayout layout;
    private int deviceWidth;
    private int deviceHeight;
    private TitleBar titleBar;
    private final static String [] bottomText={"首页","分类","购物车","我的"};
    private ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageLoader= ImageLoaderFactory.create(this);
        initView();

    }

    private void initView(){
        //顶部导航
        titleBar=(TitleBar)findViewById(R.id.titlebar);
        layout=(LinearLayout)findViewById(R.id.activity_main_bottom);
        deviceHeight= WHUtil.getDeviceScreenHeight(this);
        deviceWidth=WHUtil.getDeviceScreenWidth(this);
        RelativeLayout.LayoutParams params=null;
        titleBar.setLayoutHeight(params, deviceHeight /12);
        titleBar.setBackgroundColor(Color.GREEN);
        titleBar.setBackImageResource(R.mipmap.previous_page);

        //底部导航
        for(int i=0;i<bottomText.length;i++) {
            ActivityBottomItemView view = new ActivityBottomItemView(this);
            view.setImageViewByUrl(imageLoader, "http://pic2.ooopic.com/01/28/96/31b1OOOPIC95.jpg");
            view.setText(bottomText[i], 0, 0, 0);
            view.setFatherGravity(Gravity.CENTER);
            view.setFatherWH(deviceWidth / 4, deviceWidth / 6);
            view.setImageViewWH(deviceWidth / 6 - WHUtil.dip2px(this, 20), deviceWidth / 6 - WHUtil.dip2px(this, 20));
            layout.addView(view);
            if(i==0){
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, com.product.yao.myapp.sactivity.MainActivity.class));
                    }
                });
            }
        }
        //content fragment
        initFragment();
    }


    private FragmentManager fragmentManager;
    private void initFragment(){
        ProductTypeFragment fragment=new ProductTypeFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragment, fragment).show(fragment).commit();


    }

}
