package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-2.
 */
@AVClassName("FirstType")
public class FirstType extends AVObject {
    private String firstTypeId;
    private String firstTypeName;

    public FirstType() {

    }

    public FirstType(String theClassName) {
        super(theClassName);
    }

    public String getFirstTypeName() {
        return firstTypeName;
    }

    public void setFirstTypeName(String firstTypeName) {
        this.firstTypeName = firstTypeName;
    }

    public String getFirstTypeId() {
        return firstTypeId;
    }

    public void setFirstTypeId(String firstTypeId) {
        this.firstTypeId = firstTypeId;
    }
}
