package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-2.
 */
@AVClassName("ThirdType")
public class ThirdType extends AVObject{
    private String thirdTypeId;
    private String thirdTypeName;
    private FirstType firstType;
    private SecondType secondType;

    public ThirdType(){}

    public ThirdType(String thirdTypeName, String thirdTypeId, FirstType firstType, SecondType secondType) {
        this.thirdTypeName = thirdTypeName;
        this.thirdTypeId = thirdTypeId;
        this.firstType = firstType;
        this.secondType = secondType;
    }

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

    public FirstType getFirstType() {
        return firstType;
    }

    public void setFirstType(FirstType firstType) {
        this.firstType = firstType;
    }

    public SecondType getSecondType() {
        return secondType;
    }

    public void setSecondType(SecondType secondType) {
        this.secondType = secondType;
    }
}
