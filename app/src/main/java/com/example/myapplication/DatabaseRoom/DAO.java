package com.example.myapplication.DatabaseRoom;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;


@Dao
public interface DAO{

    @Dao
    interface StoreProducts{
        @Query("SELECT * FROM store_table")
        List<StoreTable> getProducts();



        @Query("SELECT * FROM store_table WHERE name_of_product = :productName")
        List<StoreTable> getProductDetails(String productName);

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(StoreTable product);
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insertAll(List<StoreTable> products);

        @Query("SELECT COUNT(*) FROM store_table")
        int getCount();

        @Query("DELETE FROM store_table where name_of_product = :productName")
        void delete(String productName);

        @Query("Update store_table set name_of_product = :productName , amount_of_product = :productAmount , gomla_price = :gomlaPrice , sell_price = :sellPrice , total_in_gomla = :totalInGomla , total_in_sell = :totalInSell , date = :lastDate , day = :lastDay  WHERE name_of_product =  :currentNameBeforeUpdating And gomla_price = :gomlaPrice AND name_of_mowared = :nameOfMowared" )
        void update(String productName , String productAmount , String gomlaPrice , String sellPrice, String totalInGomla , String totalInSell , String lastDate, String lastDay , String nameOfMowared ,   String currentNameBeforeUpdating);

        @Query("Update store_table set sell_price = :sellPrice ,total_in_sell = (:sellPrice * amount_of_product)   where name_of_product = :productName")
        void updateSellPrice(String sellPrice , String productName);

        @Query("Update store_table set rest = :rest   where name_of_product = :productName")
        void updateProductRest(String rest , String productName);



        @Query("SELECT DISTINCT(name_of_product) from store_table")
        List<String> getUniqueNamesForProducts();

        @Query("SELECT rest from store_table where name_of_product = :productName LIMIT 1")
        List<String> getRestAmountForProduct(String productName);

        @Query("SELECT DISTINCT(name_of_mowared) from store_table")
        List<String> getUniqueNamesForMowared();

        @Query("Update store_table set name_of_product = :productName , amount_of_product = :productAmount , gomla_price = :gomlaPrice , sell_price = :sellPrice , total_in_gomla = (:gomlaPrice * :productAmount) , total_in_sell = (:sellPrice * :productAmount) , date = :lastDate , day = :lastDay , name_of_mowared = :nameOfMowared WHERE name_of_product =  :currentNameBeforeUpdating And gomla_price = :currentGomlaPrice AND name_of_mowared = :currentMowaredNameBeforeUpdating" )
        void updateSelectedProductDetail(String productName , String productAmount , String gomlaPrice , String sellPrice,  String lastDate, String lastDay , String nameOfMowared ,   String currentNameBeforeUpdating , String currentMowaredNameBeforeUpdating , String currentGomlaPrice);



        @Query("DELETE FROM store_table where name_of_product = :productName")
        void deleteProductByName(String productName);
        @Query("DELETE FROM store_table where name_of_product = :productName AND gomla_price = :gomlaPrice AND name_of_mowared = :nameOfMowared")
        void deleteSingleDetail(String productName ,String gomlaPrice , String nameOfMowared);

    }



}


