package com.product.yao.myapp.cactivity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.product.yao.myapp.R;
import com.product.yao.myapp.adapter.ProductListAdapter;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.myview.ProductShowView;
import com.product.yao.myapp.myview.TitleBar;
import com.product.yao.myapp.sactivity.read.ReadProduct;
import com.product.yao.myapp.utils.MyUtils;
import com.product.yao.myapp.utils.ToastUtil;
import com.product.yao.myapp.utils.WHUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.image.CubeImageView;
import in.srain.cube.image.ImageLoader;
import in.srain.cube.image.ImageLoaderFactory;

/**
 * Created by paichufang on 15-12-8.
 */
public class ProductListActivity extends BaseActivity{
    private LayoutInflater inflater;
    private ScrollView view;
    private TitleBar titleBar;
    private int deviceHeight;
    private int deviceWidth;
    private Context mContext;
    private ImageLoader imageLoader;
    private String thirdTypeId;
    private ReadProduct readProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        view=(ScrollView)inflater.inflate(R.layout.cactivity_product_list, null);
        thirdTypeId=getIntent().getExtras().getString("objectId");
        readProduct=new ReadProduct(handler);
        mContext=this;
        imageLoader= ImageLoaderFactory.create(mContext);
        setContentView(view);
        initView();
        getProductList();
    }
    private CubeImageView adImage;
    private LinearLayout toolsLayout;
    private TextView synthesizeText;
    private TextView salesText;
    private TextView priceText;
    private TextView selectText;
    private LinearLayout synthesizeLayout;
    private LinearLayout salesLayout;
    private LinearLayout priceLayout;
    private LinearLayout selectLayout;
    private LinearLayout content;
    private void initView(){
        //顶部导航
        titleBar=(TitleBar)findViewById(R.id.title_bar);
        deviceHeight= WHUtil.getDeviceScreenHeight(this);
        deviceWidth=WHUtil.getDeviceScreenWidth(this);
        titleBar.setLayoutHeight(deviceHeight / 12);
        titleBar.setBackgroundColor(Color.GREEN);
        titleBar.setBackImageResource(R.mipmap.previous_page);

        adImage=(CubeImageView)findViewById(R.id.ad_image);
        toolsLayout=(LinearLayout)findViewById(R.id.tool_layout);

        synthesizeText=(TextView)findViewById(R.id.synthesize_text);
        salesText=(TextView)findViewById(R.id.sales_text);
        priceText=(TextView)findViewById(R.id.price_text);
        selectText=(TextView)findViewById(R.id.select_text);

        synthesizeLayout=(LinearLayout)findViewById(R.id.synthesize_layout);
        salesLayout=(LinearLayout)findViewById(R.id.sales_layout);
        priceLayout=(LinearLayout)findViewById(R.id.price_layout);
        selectLayout=(LinearLayout)findViewById(R.id.select_layout);

        content=(LinearLayout)findViewById(R.id.content);

    }


    private int page;
    private  static final int SIZE=10;
    private List<AVObject> productList;
    private void getProductList(){
        readProduct.getProductListByThirdId(thirdTypeId,page,SIZE);
    }

    private   Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x44:
                    productList=(List<AVObject>)msg.obj;

                    int mod=productList.size()%2;
                    int a=productList.size()/2;
                    int count=0;
                    if(mod==0){
                        for(int i=0;i<a;i++){
                            final   AVObject leftAvobj=productList.get(count);
                            final  AVObject rightAvobj=productList.get(count+1);
                            ProductShowView productShowView=new ProductShowView(mContext);
                            productShowView.setLeftAndRightImageLayoutWH(deviceWidth/2,
                                    deviceWidth/2-WHUtil.dip2px(mContext,40));
                            productShowView.setLeftClick(new ProductShowView.onLeftClickListener() {
                                @Override
                                public void onLeftClick() {
                                    Bundle bundle =new Bundle();
                                    bundle.putString("product_object",leftAvobj.toString());
                                    startActivity(new Intent(ProductListActivity.this, ProductDetailActivity.class)
                                    .putExtras(bundle));
                                }
                            });
                            productShowView.setRightClick(new ProductShowView.onRightClickListener() {
                                @Override
                                public void onRightClick() {

                                }
                            });
                            String url=leftAvobj.getString("thumbUrl");
                            int pos1=url.indexOf("/w");
                            int pos2=url.indexOf("/format");
                            String url1=url.substring(0, pos1);
                            String url2=url.substring(pos2,url.length());
                            url=url1+"/w/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/h/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/q/"+100+url2;
                            ToastUtil.showshort(mContext, url);
                            String rurl=rightAvobj.getString("thumbUrl");
                            int rpos1=rurl.indexOf("/w");
                            int rpos2=rurl.indexOf("/format");
                            String rurl1=rurl.substring(0, rpos1);
                            String rurl2=rurl.substring(rpos2,rurl.length());
                            rurl=rurl1+"/w/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/h/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/q/"+100+rurl2;
                            productShowView.setLeftImage(imageLoader,url);
                            productShowView.setLeftProductName(leftAvobj.getString("productName"),18);
                            productShowView.setLeftPrice(leftAvobj.getInt("productOnSalePrice")+"",18);

                            productShowView.setRightImage(imageLoader, rurl);
                            productShowView.setRightProductName(rightAvobj.getString("productName"), 18);
                            productShowView.setRightPrice(rightAvobj.getInt("productOnSalePrice") + "", 18);

                            content.addView(productShowView);
                        }
                    }else {
                        if(a>1) {
                            for (int i = 0; i < a; i++) {
                                final AVObject leftAvobj = productList.get(count);
                                final AVObject rightAvobj = productList.get(count + 1);
                                ProductShowView productShowView = new ProductShowView(mContext);
                                productShowView.setLeftAndRightImageLayoutWH(deviceWidth / 2,
                                        deviceWidth / 2 - WHUtil.dip2px(mContext, 40));
                                productShowView.setLeftClick(new ProductShowView.onLeftClickListener() {
                                    @Override
                                    public void onLeftClick() {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("product_object", leftAvobj.toString());
                                        startActivity(new Intent(ProductListActivity.this, ProductDetailActivity.class)
                                                .putExtras(bundle));
                                    }
                                });
                                productShowView.setRightClick(new ProductShowView.onRightClickListener() {
                                    @Override
                                    public void onRightClick() {

                                    }
                                });
                                String url = leftAvobj.getString("thumbUrl");
                                int pos1 = url.indexOf("/w");
                                int pos2 = url.indexOf("/format");
                                String url1 = url.substring(0, pos1);
                                String url2 = url.substring(pos2, url.length());
                                url = url1 + "/w/" + (deviceWidth / 2 - WHUtil.dip2px(mContext, 80))
                                        + "/h/" + (deviceWidth / 2 - WHUtil.dip2px(mContext, 80))
                                        + "/q/" + 100 + url2;
                                ToastUtil.showshort(mContext, url);
                                String rurl = rightAvobj.getString("thumbUrl");
                                int rpos1 = rurl.indexOf("/w");
                                int rpos2 = rurl.indexOf("/format");
                                String rurl1 = rurl.substring(0, rpos1);
                                String rurl2 = rurl.substring(rpos2, rurl.length());
                                rurl = rurl1 + "/w/" + (deviceWidth / 2 - WHUtil.dip2px(mContext, 80))
                                        + "/h/" + (deviceWidth / 2 - WHUtil.dip2px(mContext, 80))
                                        + "/q/" + 100 + rurl2;
                                productShowView.setLeftImage(imageLoader, url);
                                productShowView.setLeftProductName(leftAvobj.getString("productName"), 18);
                                productShowView.setLeftPrice(leftAvobj.getInt("productOnSalePrice") + "", 18);

                                productShowView.setRightImage(imageLoader, rurl);
                                productShowView.setRightProductName(rightAvobj.getString("productName"), 18);
                                productShowView.setRightPrice(rightAvobj.getInt("productOnSalePrice") + "", 18);

                                content.addView(productShowView);
                            }

                            final   AVObject leftAvobj=productList.get(productList.size()-1);
                            ProductShowView productShowView=new ProductShowView(mContext);
                            productShowView.setLeftAndRightImageLayoutWH(deviceWidth/2,
                                    deviceWidth/2-WHUtil.dip2px(mContext,40));
                            productShowView.setLeftClick(new ProductShowView.onLeftClickListener() {
                                @Override
                                public void onLeftClick() {
                                    Bundle bundle =new Bundle();
                                    bundle.putString("product_object",leftAvobj.toString());
                                    startActivity(new Intent(ProductListActivity.this, ProductDetailActivity.class)
                                            .putExtras(bundle));
                                }
                            });
                            String url=leftAvobj.getString("thumbUrl");
                            int pos1=url.indexOf("/w");
                            int pos2=url.indexOf("/format");
                            String url1=url.substring(0, pos1);
                            String url2=url.substring(pos2,url.length());
                            url=url1+"/w/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/h/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/q/"+100+url2;
                            ToastUtil.showshort(mContext, url);
                            productShowView.setLeftImage(imageLoader, url);
                            productShowView.setRightLayoutVisiable(false);
                            productShowView.setLeftProductName(leftAvobj.getString("productName"), 18);
                            productShowView.setLeftPrice(leftAvobj.getInt("productOnSalePrice") + "", 18);
                            content.addView(productShowView);
                        }else {
                            final   AVObject leftAvobj=productList.get(0);
                            ProductShowView productShowView=new ProductShowView(mContext);
                            productShowView.setLeftAndRightImageLayoutWH(deviceWidth / 2,
                                    deviceWidth / 2 - WHUtil.dip2px(mContext, 40));
                            productShowView.setLeftClick(new ProductShowView.onLeftClickListener() {
                                @Override
                                public void onLeftClick() {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("product_object", leftAvobj.toString());
                                    startActivity(new Intent(ProductListActivity.this, ProductDetailActivity.class)
                                            .putExtras(bundle));
                                }
                            });
                            String url=leftAvobj.getString("thumbUrl");
                            int pos1=url.indexOf("/w");
                            int pos2=url.indexOf("/format");
                            String url1=url.substring(0, pos1);
                            String url2=url.substring(pos2,url.length());
                            url=url1+"/w/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/h/"+(deviceWidth/2-WHUtil.dip2px(mContext,80))
                                    +"/q/"+100+url2;
                            ToastUtil.showshort(mContext, url);
                            productShowView.setLeftImage(imageLoader, url);
                            productShowView.setRightLayoutVisiable(false);
                            productShowView.setLeftProductName(leftAvobj.getString("productName"), 18);
                            productShowView.setLeftPrice(leftAvobj.getInt("productOnSalePrice") + "", 18);
                            content.addView(productShowView);
                        }
                    }

                    break;
            }
        }
    };
}
