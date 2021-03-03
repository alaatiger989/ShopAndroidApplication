package com.example.myapplication.Store.ShowStorePackage;

public class SelectedProductModel {
    private static String productName;


    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        SelectedProductModel.productName = productName;
    }
}
