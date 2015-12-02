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
    private String firstTypeId;

    public SecondType() {
    }

    public SecondType(String secondTypeId, String secondTypeName, String firstTypeId) {
        this.secondTypeId = secondTypeId;
        this.secondTypeName = secondTypeName;
        this.firstTypeId = firstTypeId;
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

    public String getFirstTypeId() {
        return firstTypeId;
    }

    public void setFirstTypeId(String firstTypeId) {
        this.firstTypeId = firstTypeId;
    }
}
