package com.example.myapplication.DatabaseRoom;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class StoreTable {

    @PrimaryKey(autoGenerate = true)
    public int id;
    /**************  Data of Each PRODUCT  **************/
    @ColumnInfo(name = "name_of_product")
    public String PRODUCT_NAME;

    @ColumnInfo(name = "amount_of_product")
    public String PRODUCT_AMOUNT;

    @ColumnInfo(name = "gomla_price")
    public String PRODUCT_GOMLA_PRICE;

    @ColumnInfo(name = "sell_price")
    public String PRODUCT_SELL_PRICE;

    @ColumnInfo(name = "total_in_gomla")
    public String TOTAL_GOMLA;

    @ColumnInfo(name = "total_in_sell")
    public String TOTAL_SELL;

    @ColumnInfo(name = "date")
    public String DATE;
    @ColumnInfo(name = "day")
    public String DAY;

    @ColumnInfo(name = "name_of_mowared")
    public String NAME_OF_MOWARED;

    @ColumnInfo(name = "rest")
    public String PRODUCT_REST;
   /***************************************************************/

    public StoreTable(String PRODUCT_NAME, String PRODUCT_AMOUNT, String PRODUCT_GOMLA_PRICE, String PRODUCT_SELL_PRICE, String TOTAL_GOMLA, String TOTAL_SELL , String DATE , String DAY , String NAME_OF_MOWARED , String PRODUCT_REST) {

        this.PRODUCT_NAME = PRODUCT_NAME;
        this.PRODUCT_AMOUNT = PRODUCT_AMOUNT;
        this.PRODUCT_GOMLA_PRICE = PRODUCT_GOMLA_PRICE;
        this.PRODUCT_SELL_PRICE = PRODUCT_SELL_PRICE;
        this.TOTAL_GOMLA = TOTAL_GOMLA;
        this.TOTAL_SELL = TOTAL_SELL;
        this.DATE = DATE;
        this.DAY = DAY;
        this.NAME_OF_MOWARED = NAME_OF_MOWARED;
        this.PRODUCT_REST = PRODUCT_REST;
    }

    public String getPRODUCT_REST() {
        return PRODUCT_REST;
    }

    public String getNAME_OF_MOWARED() {
        return NAME_OF_MOWARED;
    }

    public String getDATE() {
        return DATE;
    }

    public String getDAY() {
        return DAY;
    }

    public String getPRODUCT_NAME() {
        return PRODUCT_NAME;
    }

    public String getPRODUCT_AMOUNT() {
        return PRODUCT_AMOUNT;
    }

    public String getPRODUCT_GOMLA_PRICE() {
        return PRODUCT_GOMLA_PRICE;
    }

    public String getPRODUCT_SELL_PRICE() {
        return PRODUCT_SELL_PRICE;
    }

    public String getTOTAL_GOMLA() {
        return TOTAL_GOMLA;
    }

    public String getTOTAL_SELL() {
        return TOTAL_SELL;
    }
}
