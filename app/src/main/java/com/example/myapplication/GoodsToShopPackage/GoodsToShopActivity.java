package com.example.myapplication.GoodsToShopPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class GoodsToShopActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinnerProductName , spinnerSellPrices ;
    EditText productAmount;
    AppDatabase db ;
    List<String> productNames ;
    List<String> productSellPrices ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_to_shop);
        spinnerProductName = findViewById(R.id.spinner_name_of_product);
        spinnerSellPrices = findViewById(R.id.spinner_sell_of_product);
        productAmount = findViewById(R.id.edit_amount_of_product);

        initProductsNamesSpinner();


    }

    private void initProductSellPricesSpinner(String productName) {
        productSellPrices = new ArrayList<>();
        db = AppDatabase.getInstance(getApplicationContext());
        productSellPrices.addAll(db.storeDao().getUniqueSellPricesForProduct(productName));

        ArrayAdapter<String> adapterForProductSellPrices = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , productSellPrices);
        adapterForProductSellPrices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSellPrices.setAdapter(adapterForProductSellPrices);
    }

    private void initProductsNamesSpinner() {
        productNames = new ArrayList<>();
        db = AppDatabase.getInstance(getApplicationContext());
        productNames.addAll(db.storeDao().getUniqueNamesForProducts());
        ArrayAdapter<String> adapterForProductNames = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , productNames);
        adapterForProductNames.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProductName.setAdapter(adapterForProductNames);
        spinnerProductName.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        initProductSellPricesSpinner(text);
        Toast.makeText(parent.getContext() , text , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}