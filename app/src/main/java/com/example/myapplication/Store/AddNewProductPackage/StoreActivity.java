package com.example.myapplication.Store.AddNewProductPackage;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Common.DateAndDay;
import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;
import com.example.myapplication.R;
import com.example.myapplication.Store.ShowStorePackage.ShowStoreActivity;

import java.util.ArrayList;
import java.util.List;

public class StoreActivity extends AppCompatActivity implements View.OnClickListener {

    EditText
            amount,
            gomlaPrice,
            sellPrice;
    AutoCompleteTextView productName , nameOfMowared;
    Button addNew,
            showStore;

    int checkIfProductIsAlreadyExisted = 0  , isGomlaPriceTheSameWithEnteredOne = 0 , isTheSameNameOfMowared = 0; //0 means no
    List<StoreTable> totalAmountRest ;
    List<String> productsUnique = new ArrayList<>();
    List<String> mowaredUnique = new ArrayList<>();
    Double amountRestForEachProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        setTitle("ادخال منتج للمخزن");
        productName = findViewById(R.id.edit_name_of_product);
        amount = findViewById(R.id.edit_amount_of_product);
        gomlaPrice = findViewById(R.id.edit_gomla_of_product);
        sellPrice = findViewById(R.id.edit_sell_of_product);
        nameOfMowared = findViewById(R.id.edit_name_of_mowared);

        addNew = findViewById(R.id.btn_new_product);
        showStore = findViewById(R.id.btn_show_store);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        productsUnique.addAll(db.storeDao().getUniqueNamesForProducts());
        mowaredUnique.addAll(db.storeDao().getUniqueNamesForMowared());
        ArrayAdapter<String> adapterForProductNames = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , productsUnique);
        ArrayAdapter<String> adapterForMowaredNames = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , mowaredUnique);
        productName.setAdapter(adapterForProductNames);
        nameOfMowared.setAdapter(adapterForMowaredNames);

        addNew.setOnClickListener(this);
        showStore.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_new_product:
                initAddButton();
                break;
            case R.id.btn_show_store:
                initShowStoreButton();
                break;
        }



}

    private void initShowStoreButton() {
        Intent intent = new Intent(StoreActivity.this , ShowStoreActivity.class);
        startActivity(intent);
    }

    private void initAddButton() {
        checkTheProductNameIfExists();
        if(checkIfProductIsAlreadyExisted == 0)
        {
            getTotalAmountOfProduct();
            insertNewProduct();
            updateProductRest();
        }
        else{

            // IF Product Name is Existed but With Different Gomla Price
            if(isGomlaPriceTheSameWithEnteredOne == 0)
            {
                DifferentGomlaPriceAlert differentGomlaPriceAlert = new DifferentGomlaPriceAlert(productName , amount , gomlaPrice , sellPrice , nameOfMowared  , isTheSameNameOfMowared);
                differentGomlaPriceAlert.show(getSupportFragmentManager() , "Inquiry With different Gomla Price");
                Toast.makeText(getApplicationContext() , "تم تحديث بيانات المنتج بنجاح" , Toast.LENGTH_LONG).show();
            }
            else{
                //Same productName and Gomla Price
                //Update The Amount of The product  and its another data

                SameGomlaPriceAlert sameGomlaPriceAlert = new SameGomlaPriceAlert(productName , amount , gomlaPrice , sellPrice , nameOfMowared , isTheSameNameOfMowared);
                sameGomlaPriceAlert.show(getSupportFragmentManager() , "Inquiry With Same Gomla Price");
                Toast.makeText(getApplicationContext() , "تم تحديث بيانات المنتج بنجاح" , Toast.LENGTH_LONG).show();
            }

        }
        checkIfProductIsAlreadyExisted = 0  ; isGomlaPriceTheSameWithEnteredOne = 0 ; isTheSameNameOfMowared = 0;
    }


    private void updateProductRest() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.storeDao().updateProductRest(String.valueOf(amountRestForEachProduct + Double.parseDouble(amount.getText().toString())) , productName.getText().toString());
    }
    private void checkTheProductNameIfExists()
    {
        List<StoreTable> allProducts = new ArrayList<>();
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        allProducts.addAll(db.storeDao().getProductDetails(productName.getText().toString()));
        for(int i = 0 ; i < allProducts.size() ; i++)
        {

            if(productName.getText().toString().equalsIgnoreCase(allProducts.get(i).getPRODUCT_NAME()))
            {
                checkIfProductIsAlreadyExisted++;
            }
            Log.i("StoreActivity" , allProducts.get(i).getPRODUCT_GOMLA_PRICE());
            if(productName.getText().toString().equalsIgnoreCase(allProducts.get(i).getPRODUCT_NAME()) && gomlaPrice.getText().toString().equalsIgnoreCase(allProducts.get(i).getPRODUCT_GOMLA_PRICE()))
            {
                ProductModel.setProductName(allProducts.get(i).getPRODUCT_NAME());
                ProductModel.setProductAmount(allProducts.get(i).getPRODUCT_AMOUNT());
                isGomlaPriceTheSameWithEnteredOne++;
            }
            if(nameOfMowared.getText().toString().equalsIgnoreCase(allProducts.get(i).getNAME_OF_MOWARED()))
            {
                isTheSameNameOfMowared++;
            }
        }
    }
    private void getTotalAmountOfProduct() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        amountRestForEachProduct = 0.0;
        totalAmountRest = new ArrayList<>();
        totalAmountRest.addAll(db.storeDao().getProductDetails(productName.getText().toString()));
        for(int i = 0 ; i < totalAmountRest.size() ; i++)
        {
            amountRestForEachProduct = amountRestForEachProduct + Double.parseDouble(totalAmountRest.get(i).getPRODUCT_AMOUNT().toString());
        }
    }
    private void insertNewProduct()
    {
        String formattedDate = new DateAndDay().getDate();
        String dayOfTheWeek = new DateAndDay().getDay();
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.storeDao().insert(new StoreTable(productName.getText().toString() ,
                amount.getText().toString(),
                gomlaPrice.getText().toString(),
                sellPrice.getText().toString(),
                String.valueOf(Double.parseDouble(amount.getText().toString()) * Double.parseDouble(gomlaPrice.getText().toString())),
                String.valueOf(Double.parseDouble(amount.getText().toString()) * Double.parseDouble(sellPrice.getText().toString())),
                String.valueOf(formattedDate),
                dayOfTheWeek.toString() , nameOfMowared.getText().toString(),
                amount.getText().toString()
        ));
        Toast.makeText(getApplicationContext() , "تم اضافة المنتج بنجاح" , Toast.LENGTH_LONG).show();
    }


}