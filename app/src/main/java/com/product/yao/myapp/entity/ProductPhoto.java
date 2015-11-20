package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-3.
 */
@AVClassName("ProductPhoto")
public class ProductPhoto extends AVObject{
    private String productPhotoName;
    private AVFile productPhotoFile;
    private String productId;
    private String productPhotoType;
}
