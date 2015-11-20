package com.product.yao.myapp.base;

import android.app.Application;
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
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVObject.registerSubclass(FirstType.class);
        AVObject.registerSubclass(SecondType.class);
        AVObject.registerSubclass(ThirdType.class);
        AVObject.registerSubclass(MyRole.class);
        AVObject.registerSubclass(MyUser.class);
        AVObject.registerSubclass(MyAccount.class);
        AVObject.registerSubclass(ProductPhoto.class);
        AVObject.registerSubclass(Product.class);
        AVOSCloud.initialize(this, "1GKsLR64N3dlBn7w5jrc6QtU", "cjfo17K3iMmcr4iMMrsjyPwb");
    }
}
