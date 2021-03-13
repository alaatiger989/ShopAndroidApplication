package com.example.myapplication.Store.AddNewProductPackage;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Common.DateAndDay;
import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;

import java.util.ArrayList;
import java.util.List;

public class SameGomlaPriceAlert extends AppCompatDialogFragment {
    EditText productName,
            amount,
            gomlaPrice,
            sellPrice,
            nameOfMowared;
    int nameOfMowaredIsSame ;
    List<StoreTable> totalAmountRest ;

    Double amountRestForEachProduct , currentAmountOfEnteredMowared =0.0;
    public SameGomlaPriceAlert(EditText productName, EditText amount, EditText gomlaPrice, EditText sellPrice  ,EditText nameOfMowared , int nameOfMowaredIsSame) {
        this.productName = productName;
        this.amount = amount;
        this.gomlaPrice = gomlaPrice;
        this.sellPrice = sellPrice;
        this.nameOfMowared = nameOfMowared;
        this.nameOfMowaredIsSame = nameOfMowaredIsSame;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("استفسار");
        builder.setMessage("هذا المنتج موجود مسبقا هل تريد زبادة القيمة المخزنة بهذه القيمة الجديدة؟");
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(nameOfMowaredIsSame != 0)// 0 means no --- NOT 0 means yes
                        {
                            getTotalAmountOfProduct();
                            getSpecificCategoryInProduct();
                            updateExistedProductWithSameGomlaPrice();
                            updateProductRest();

                        }


                    }
        });

        // A null listener allows the button to dismiss the dialog and take no further action.
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                productName.setText("");
                amount.setText("");
                gomlaPrice.setText("");
                sellPrice.setText("");
            }
        });
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        return  builder.create();

    }

    private void updateProductRest() {
        AppDatabase db = AppDatabase.getInstance(getActivity());
        db.storeDao().updateProductRest(String.valueOf(amountRestForEachProduct + Double.parseDouble(amount.getText().toString())) , productName.getText().toString());

    }
    private void getTotalAmountOfProduct() {
        AppDatabase db = AppDatabase.getInstance(getActivity());
        amountRestForEachProduct = 0.0;
        List<String> totalAmountRest = new ArrayList<>();
        totalAmountRest.addAll(db.storeDao().getRestAmountForProduct(productName.getText().toString()));
        amountRestForEachProduct = Double.parseDouble(totalAmountRest.get(0).toString());
    }

    private void getSpecificCategoryInProduct()
    {
        List<StoreTable> specificCategory = new ArrayList<>();
        AppDatabase db = AppDatabase.getInstance(getActivity());
        specificCategory.addAll(db.storeDao().getSpecificCategory(productName.getText().toString(), gomlaPrice.getText().toString() , nameOfMowared.getText().toString()));
        Log.i("SameGomlaPriceAlert" , "" + specificCategory.size());
        if (specificCategory.size() > 0)
        {
            Toast.makeText(getActivity() , "هذا المورد موجود مسبقا لهذا المنتج بنفس سعر الجملة" , Toast.LENGTH_LONG).show();
            for(int i = 0 ; i<specificCategory.size() ; i++)
            {
                currentAmountOfEnteredMowared+=Double.parseDouble(specificCategory.get(i).getPRODUCT_AMOUNT());
            }
        }

    }
    private void updateSellPriceForAllProduct() {

        AppDatabase db = AppDatabase.getInstance(getActivity());

        db.storeDao().updateSellPrice(sellPrice.getText().toString() , productName.getText().toString());
    }

    private void updateExistedProductWithSameGomlaPrice() {
        String formattedDate = new DateAndDay().getDate();
        String dayOfTheWeek = new DateAndDay().getDay();
        String newAmount = String.valueOf(currentAmountOfEnteredMowared + Double.parseDouble(amount.getText().toString()));
        AppDatabase db = AppDatabase.getInstance(getActivity());
        db.storeDao().update(productName.getText().toString() ,
                newAmount,
                gomlaPrice.getText().toString(),
                sellPrice.getText().toString(),
                String.valueOf(Double.parseDouble(newAmount) * Double.parseDouble(gomlaPrice.getText().toString())),
                String.valueOf(Double.parseDouble(newAmount) * Double.parseDouble(sellPrice.getText().toString())),
                String.valueOf(formattedDate),
                dayOfTheWeek.toString() ,nameOfMowared.getText().toString() ,  ProductModel.getProductName());

    }

    private void insertNewProduct()
    {
        String formattedDate = new DateAndDay().getDate();
        String dayOfTheWeek = new DateAndDay().getDay();
        AppDatabase db = AppDatabase.getInstance(getActivity());
        db.storeDao().insert(new StoreTable(productName.getText().toString() ,
                amount.getText().toString(),
                gomlaPrice.getText().toString(),
                sellPrice.getText().toString(),
                String.valueOf(Double.parseDouble(amount.getText().toString()) * Double.parseDouble(gomlaPrice.getText().toString())),
                String.valueOf(Double.parseDouble(amount.getText().toString()) * Double.parseDouble(sellPrice.getText().toString())),
                String.valueOf(formattedDate),
                dayOfTheWeek.toString() , nameOfMowared.getText().toString() , amount.getText().toString()));

    }
}
