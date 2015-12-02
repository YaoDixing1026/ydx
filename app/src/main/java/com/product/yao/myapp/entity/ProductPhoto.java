package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-3.
 */
public class ProductPhoto {
    private String productPhotoName;
    private AVFile productPhotoFile;
    private String productId;
    private String productPhotoType;


    public String getProductPhotoName() {
        return productPhotoName;
    }

    public void setProductPhotoName(String productPhotoName) {
        this.productPhotoName = productPhotoName;
    }

    public AVFile getProductPhotoFile() {
        return productPhotoFile;
    }

    public void setProductPhotoFile(AVFile productPhotoFile) {
        this.productPhotoFile = productPhotoFile;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPhotoType() {
        return productPhotoType;
    }

    public void setProductPhotoType(String productPhotoType) {
        this.productPhotoType = productPhotoType;
    }
}
