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
    private FirstType productFirstType;
    private SecondType productSecondType;
    private ThirdType productThirdType;
    private double productPrice;
    private double productOnSalePrice;
    private boolean productStatus;
    private int productCount;
    private String productDescription;
    private List<ProductPhoto> productPhoto;
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

    public FirstType getProductFirstType() {
        return productFirstType;
    }

    public void setProductFirstType(FirstType productFirstType) {
        this.productFirstType = productFirstType;
    }

    public SecondType getProductSecondType() {
        return productSecondType;
    }

    public void setProductSecondType(SecondType productSecondType) {
        this.productSecondType = productSecondType;
    }

    public ThirdType getProductThirdType() {
        return productThirdType;
    }

    public void setProductThirdType(ThirdType productThirdType) {
        this.productThirdType = productThirdType;
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

    public List<ProductPhoto> getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(List<ProductPhoto> productPhoto) {
        this.productPhoto = productPhoto;
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
}
