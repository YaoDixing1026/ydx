package com.product.yao.myapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.product.yao.myapp.entity.FirstType;
import com.product.yao.myapp.entity.MyAccount;
import com.product.yao.myapp.entity.MyRole;
import com.product.yao.myapp.entity.MyUser;
import com.product.yao.myapp.entity.Product;
import com.product.yao.myapp.entity.ProductPhoto;
import com.product.yao.myapp.entity.SecondType;
import com.product.yao.myapp.entity.ThirdType;

/**
 * Created by paichufang on 15-11-2.
 */
public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //跟踪统计应用的打开情况
        AVAnalytics.trackAppOpened(getIntent());
    }
}
