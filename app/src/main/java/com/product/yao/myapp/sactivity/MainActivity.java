package com.product.yao.myapp.sactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.product.yao.myapp.R;
import com.product.yao.myapp.base.BaseActivity;
import com.product.yao.myapp.sactivity.create.ChooseUploadActivity;
import com.product.yao.myapp.sactivity.create.CreateFirstTypeAcitvity;
import com.product.yao.myapp.sactivity.create.CreateProductActivity;
import com.product.yao.myapp.sactivity.create.CreateSecondTypeActivity;
import com.product.yao.myapp.sactivity.create.CreateThirdTypeActivity;
import com.product.yao.myapp.sactivity.create.CreateUserActivity;
import com.product.yao.myapp.sactivity.create.ProductPhotoUploadActivity;

/**
 * Created by paichufang on 15-11-6.
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sactivity_main);
        //创建一级类别
        findViewById(R.id.create_first_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateFirstTypeAcitvity.class));
            }
        });
        //创建二级类别
        findViewById(R.id.create_second_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateSecondTypeActivity.class));
            }
        });
        //创建三级类别
        findViewById(R.id.create_third_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateThirdTypeActivity.class));
            }
        });
        //创建用户
        findViewById(R.id.create_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateUserActivity.class));
            }
        });
        //创建产品
        findViewById(R.id.create_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateProductActivity.class));
            }
        });
        //上传产品图片
        findViewById(R.id.up_load_product_photo_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductPhotoUploadActivity.class));
            }
        });
    }
}
