package com.example.myapplication.Store.UpdateSingleDetailForProduct;

public class SelectedProductSingleDetailModel {
    private static String productName;
    private static String productAmount;
    private static String productGomlaPrice;
    private static String productSellPrice;
    private static String mowaredName;

    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        SelectedProductSingleDetailModel.productName = productName;
    }

    public static String getProductAmount() {
        return productAmount;
    }

    public static void setProductAmount(String productModel) {
        SelectedProductSingleDetailModel.productAmount = productModel;
    }

    public static String getProductGomlaPrice() {
        return productGomlaPrice;
    }

    public static void setProductGomlaPrice(String productGomlaPrice) {
        SelectedProductSingleDetailModel.productGomlaPrice = productGomlaPrice;
    }

    public static String getProductSellPrice() {
        return productSellPrice;
    }

    public static void setProductSellPrice(String productSellPrice) {
        SelectedProductSingleDetailModel.productSellPrice = productSellPrice;
    }

    public static String getMowaredName() {
        return mowaredName;
    }

    public static void setMowaredName(String mowaredName) {
        SelectedProductSingleDetailModel.mowaredName = mowaredName;
    }
}
