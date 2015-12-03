package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-2.
 */
@AVClassName("ThirdType")
public class ThirdType extends AVObject{
    private String thirdTypeId;
    private String thirdTypeName;
    private String firstTypeId;
    private String secondTypeId;
    private String photoUrl;

    public ThirdType(){}

    public String getThirdTypeId() {
        return thirdTypeId;
    }

    public void setThirdTypeId(String thirdTypeId) {
        this.thirdTypeId = thirdTypeId;
    }

    public String getThirdTypeName() {
        return thirdTypeName;
    }

    public void setThirdTypeName(String thirdTypeName) {
        this.thirdTypeName = thirdTypeName;
    }

    public String getFirstTypeId() {
        return firstTypeId;
    }

    public void setFirstTypeId(String firstTypeId) {
        this.firstTypeId = firstTypeId;
    }

    public String getSecondTypeId() {
        return secondTypeId;
    }

    public void setSecondTypeId(String secondTypeId) {
        this.secondTypeId = secondTypeId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
