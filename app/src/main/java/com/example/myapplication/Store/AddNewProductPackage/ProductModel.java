package com.example.myapplication.Store.AddNewProductPackage;

public class ProductModel {
    private static String productName;
    private static String productAmount;

    public static String getProductAmount() {
        return productAmount;
    }

    public static void setProductAmount(String productAmount) {
        ProductModel.productAmount = productAmount;
    }

    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        ProductModel.productName = productName;
    }
}
