package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.GoodsToShopPackage.GoodsToShopActivity;
import com.example.myapplication.RequirementPackage.RequirementsActivity;
import com.example.myapplication.ShopPackage.ShopActivity;
import com.example.myapplication.Store.AddNewProductPackage.StoreActivity;

public class MainActivity extends AppCompatActivity {

    CardView shop , requirements , goodsToShop, store;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shop = findViewById(R.id.shop);
        requirements = findViewById(R.id.requirements);
        goodsToShop = findViewById(R.id.goods_for_shop);
        store = findViewById(R.id.store);

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , StoreActivity.class);
                startActivity(intent);
            }
        });

        requirements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , RequirementsActivity.class);
                startActivity(intent);
            }
        });

        goodsToShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , GoodsToShopActivity.class);
                startActivity(intent);
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext() , ShopActivity.class);
                    startActivity(intent);
            }
        });
    }
}