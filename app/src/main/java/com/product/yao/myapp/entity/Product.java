package com.product.yao.myapp.entity;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.List;

/**
 * Created by paichufang on 15-11-2.
 */
@AVClassName("Product")
public class Product extends AVObject{
    private String productId;
    private String productName;
    private String productFirstTypeId;
    private String productSecondTypeId;
    private String productThirdTypeId;
    private double productPrice;
    private double productOnSalePrice;
    private boolean productStatus;
    private int productCount;
    private String productDescription;
    private List<String> productPhotoId;
    private String productionDate;
    private String productionLife;

    public Product() {
    }

    public Product(String theClassName) {
        super(theClassName);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductOnSaleProce() {
        return productOnSalePrice;
    }

    public void setProductOnSaleProce(double productOnSalePrice) {
        this.productOnSalePrice = productOnSalePrice;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getProductionLife() {
        return productionLife;
    }

    public void setProductionLife(String productionLife) {
        this.productionLife = productionLife;
    }

    public String getProductFirstTypeId() {
        return productFirstTypeId;
    }

    public void setProductFirstTypeId(String productFirstTypeId) {
        this.productFirstTypeId = productFirstTypeId;
    }

    public List<String> getProductPhotoId() {
        return productPhotoId;
    }

    public void setProductPhotoId(List<String> productPhotoId) {
        this.productPhotoId = productPhotoId;
    }

    public double getProductOnSalePrice() {
        return productOnSalePrice;
    }

    public void setProductOnSalePrice(double productOnSalePrice) {
        this.productOnSalePrice = productOnSalePrice;
    }

    public String getProductThirdTypeId() {
        return productThirdTypeId;
    }

    public void setProductThirdTypeId(String productThirdTypeId) {
        this.productThirdTypeId = productThirdTypeId;
    }

    public String getProductSecondTypeId() {
        return productSecondTypeId;
    }

    public void setProductSecondTypeId(String productSecondTypeId) {
        this.productSecondTypeId = productSecondTypeId;
    }
}
