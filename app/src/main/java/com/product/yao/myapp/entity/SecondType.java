package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by ydx on 15-11-2.
 */
@AVClassName("SecondType")
public class SecondType  extends AVObject{
    private String secondTypeId;
    private String secondTypeName;
    private FirstType firstType;

    public SecondType() {
    }

    public SecondType(String secondTypeId, String secondTypeName, FirstType firstType) {
        this.secondTypeId = secondTypeId;
        this.secondTypeName = secondTypeName;
        this.firstType = firstType;
    }

    public String getSecondTypeId() {
        return secondTypeId;
    }

    public void setSecondTypeId(String secondTypeId) {
        this.secondTypeId = secondTypeId;
    }

    public String getSecondTypeName() {
        return secondTypeName;
    }

    public void setSecondTypeName(String secondTypeName) {
        this.secondTypeName = secondTypeName;
    }

    public FirstType getFirstType() {
        return firstType;
    }

    public void setFirstType(FirstType firstType) {
        this.firstType = firstType;
    }
}
