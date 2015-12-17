package com.product.yao.myapp.cactivity.product;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.avos.avoscloud.AVObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.utils.MyUtils;
import com.product.yao.myapp.utils.ToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;

/**
 * Created by paichufang on 15-12-16.
 */
public class ProductDetailActivity extends BaseActivity{

    private Context mContext;
    private PullToRefreshScrollView scrollView;
    private LinearLayout innerLayout;
    private AVObject product;
    private Map productMap;
    private ImageLoader imageLoader;
    private JSONObject jsonObject;
    private JSONArray coverPhotoArray;
    private int deviceWidth;
    private int deviceHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cactivity_product_detail);
        mContext=this;
        deviceWidth= MyUtils.getDeviceScreenWidth(mContext);
        deviceHeight=MyUtils.getDeviceScreenHeight(mContext);
        imageLoader= ImageLoaderFactory.create(mContext);
        getData();
        initView();
    }

    private void getData(){
        String avStr= getIntent().getExtras().getString("product_object");
        if(avStr!=null){
            try {
                product= AVObject.parseAVObject(avStr);
                jsonObject=(JSONObject) JSONObject.parseObject(avStr);
                jsonObject= JSONObject.parseObject(jsonObject.getString("serverData"));
                coverPhotoArray= jsonObject.getJSONArray("photoCoverUrl");
            }catch (Exception e){
                ToastUtil.showshort(mContext,e.getMessage());
            }

        }
    }

    private LinearLayout productDetailLayout;
    @TargetApi(23)
    private void initView(){
        scrollView=(PullToRefreshScrollView)findViewById(R.id.scrollView);
        innerLayout=(LinearLayout)findViewById(R.id.inner_layout);
        productDetailLayout=(LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.item_detail_product, null);
        HorizontalScrollView horizontalScrollView=(HorizontalScrollView)productDetailLayout.findViewById(R.id.h_scrollview);
        LinearLayout topImageLayout=(LinearLayout)productDetailLayout.findViewById(R.id.top_img_layout);
        TextView productNameTV=(TextView)productDetailLayout.findViewById(R.id.product_name);
        TextView productIntroductionTV=(TextView)productDetailLayout.findViewById(R.id.product_introduction);
        LinearLayout productImageLayout=(LinearLayout)productDetailLayout.findViewById(R.id.product_img_layout);
        if(product!=null){
            String productName= product.getString("productName");
            String productIntroduction=product.getString("productDescription");
            productNameTV.setText(productName);
            productIntroductionTV.setText(productIntroduction);
            for(int i=0;i<coverPhotoArray.size();i++){
                LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(deviceWidth,deviceHeight/4);
                CubeImageView cubeImageView=new CubeImageView(mContext);
                cubeImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                cubeImageView.setLayoutParams(params);
                cubeImageView.loadImage(imageLoader,coverPhotoArray.get(i).toString());
                topImageLayout.addView(cubeImageView);
            }
            innerLayout.addView(productDetailLayout);
        }

        horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollX>deviceWidth/3){

                }
            }
        });
    }

}
