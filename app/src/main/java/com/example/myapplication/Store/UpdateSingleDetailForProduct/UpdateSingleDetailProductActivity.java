package com.example.myapplication.Store.UpdateSingleDetailForProduct;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Common.DateAndDay;
import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;
import com.example.myapplication.R;
import com.example.myapplication.Store.AddNewProductPackage.DifferentGomlaPriceAlert;
import com.example.myapplication.Store.AddNewProductPackage.ProductModel;
import com.example.myapplication.Store.AddNewProductPackage.SameGomlaPriceAlert;
import com.example.myapplication.Store.ProductDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class UpdateSingleDetailProductActivity extends AppCompatActivity implements View.OnClickListener {

    EditText
            amount,
            gomlaPrice,
            sellPrice;
    AutoCompleteTextView productName , nameOfMowared;
    Button updateBtn;
    List<String> productsUnique = new ArrayList<>();
    List<String> mowaredUnique = new ArrayList<>();
    Double amountRestForEachProduct;
    int checkIfProductIsAlreadyExisted = 0  , isGomlaPriceTheSameWithEnteredOne = 0 , isTheSameNameOfMowared = 0; //0 means no
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_single_detail_product);
        setTitle(SelectedProductSingleDetailModel.getProductName());
        productName = findViewById(R.id.edit_name_of_product);
        amount = findViewById(R.id.edit_amount_of_product);
        gomlaPrice = findViewById(R.id.edit_gomla_of_product);
        sellPrice = findViewById(R.id.edit_sell_of_product);
        nameOfMowared = findViewById(R.id.edit_name_of_mowared);
        updateBtn = findViewById(R.id.btn_new_product);


        productName.setText(SelectedProductSingleDetailModel.getProductName());
        amount.setText(SelectedProductSingleDetailModel.getProductAmount());
        gomlaPrice.setText(SelectedProductSingleDetailModel.getProductGomlaPrice());
        sellPrice.setText(SelectedProductSingleDetailModel.getProductSellPrice());
        nameOfMowared.setText(SelectedProductSingleDetailModel.getMowaredName());

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        productsUnique.addAll(db.storeDao().getUniqueNamesForProducts());
        mowaredUnique.addAll(db.storeDao().getUniqueNamesForMowared());
        ArrayAdapter<String> adapterForProductNames = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , productsUnique);
        ArrayAdapter<String> adapterForMowaredNames = new ArrayAdapter<String>(this , R.layout.support_simple_spinner_dropdown_item , mowaredUnique);
        productName.setAdapter(adapterForProductNames);
        nameOfMowared.setAdapter(adapterForMowaredNames);

        updateBtn.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_new_product:

                if(!SelectedProductSingleDetailModel.getProductGomlaPrice().equalsIgnoreCase(gomlaPrice.getText().toString()))//0 = no
                {
                    DifferentGomlaPriceAlertInUpdatingDetails differentGomlaPriceAlert = new DifferentGomlaPriceAlertInUpdatingDetails(productName , amount , gomlaPrice , sellPrice , nameOfMowared  , isTheSameNameOfMowared);
                    differentGomlaPriceAlert.show(getSupportFragmentManager() , "Inquiry With different Gomla Price");
                    Toast.makeText(getApplicationContext() , "تم تحديث بيانات المنتج بنجاح" , Toast.LENGTH_LONG).show();
                }
                else{
                    //Same productName and Gomla Price
                    //Update The Amount of The product  and its another data
                    String formattedDate = new DateAndDay().getDate();
                    String dayOfTheWeek = new DateAndDay().getDay();
                    AppDatabase db = AppDatabase.getInstance(getApplicationContext());
                    db.storeDao().updateSelectedProductDetail(productName.getText().toString(), amount.getText().toString() , gomlaPrice.getText().toString() , sellPrice.getText().toString() ,formattedDate , dayOfTheWeek ,nameOfMowared.getText().toString() , SelectedProductSingleDetailModel.getProductName() , SelectedProductSingleDetailModel.getMowaredName() , SelectedProductSingleDetailModel.getProductGomlaPrice());
                    getTotalAmountOfProduct();
                    updateProductRest();
                    Toast.makeText(getApplicationContext() , "تم تحديث المنتج بنجاح" , Toast.LENGTH_LONG).show();


                }

                break;
        }
    }


    private void getTotalAmountOfProduct() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        amountRestForEachProduct = 0.0;
        List<String> totalAmountRest = new ArrayList<>();
        totalAmountRest.addAll(db.storeDao().getRestAmountForProduct(productName.getText().toString()));
        amountRestForEachProduct = Double.parseDouble(totalAmountRest.get(0).toString());
    }
    private void updateProductRest() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        Log.i("UpdateSingleDetailP" ,  "" + amountRestForEachProduct +"," + amount.getText().toString());
        if(Double.parseDouble(SelectedProductSingleDetailModel.getProductAmount()) < Double.parseDouble(amount.getText().toString()))
        {
            db.storeDao().updateProductRest(String.valueOf(amountRestForEachProduct + (Double.parseDouble(amount.getText().toString()) - Double.parseDouble(SelectedProductSingleDetailModel.getProductAmount()))) , productName.getText().toString());
        }
        else{
            db.storeDao().updateProductRest(String.valueOf(amountRestForEachProduct - (Double.parseDouble(SelectedProductSingleDetailModel.getProductAmount()) - Double.parseDouble(amount.getText().toString()))) , productName.getText().toString());
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext() , ProductDetailsActivity.class);
        startActivity(intent);
    }
}