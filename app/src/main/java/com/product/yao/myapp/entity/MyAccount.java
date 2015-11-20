package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by paichufang on 15-11-3.
 */
@AVClassName("MyAccount")
public class MyAccount extends AVObject{
    private MyUser myUser;
    private int accountPassword;
    private int bankNumber;
    private String accountId;
    private int accountBalance;
}
