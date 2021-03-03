package com.example.myapplication.Store.AddNewProductPackage;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.Common.DateAndDay;
import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;

import java.util.ArrayList;
import java.util.List;

public class DifferentGomlaPriceAlert extends AppCompatDialogFragment {
    EditText productName,
            amount,
            gomlaPrice,
            sellPrice,
            nameOfMowared;
    int nameOfMowaredIsSame ;
    List<StoreTable> totalAmountRest ;
    Double amountRestForEachProduct;
    public DifferentGomlaPriceAlert(EditText productName, EditText amount, EditText gomlaPrice, EditText sellPrice ,EditText nameOfMowared , int nameOfMowaredIsSame) {
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
        builder.setMessage("لقد اختلف سعر الجملة للمنتج " + productName.getText() + " ليكون " + gomlaPrice.getText() +  " جنيها هل تريد تغيير سعر البيع لكل المنتج القديم و الجديد ليكون " + sellPrice.getText() + " ؟");

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
        builder.setPositiveButton("نعم", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        getTotalAmountOfProduct();
                        insertNewProduct();
                        updateSellPriceForProduct();
                        updateProductRest();
                    }
                });

                // A null listener allows the button to dismiss the dialog and take no further action.
        builder.setNegativeButton("لا", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getTotalAmountOfProduct();
                        insertNewProduct();
                        updateProductRest();
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

    private void updateSellPriceForProduct() {
        AppDatabase db = AppDatabase.getInstance(getActivity());
        db.storeDao().updateSellPrice(sellPrice.getText().toString() , productName.getText().toString());
        Toast.makeText(getActivity() , "تم تحديث سعر البيع للمنتج " + productName.getText().toString() , Toast.LENGTH_LONG).show();
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
                dayOfTheWeek.toString() , nameOfMowared.getText().toString() ,
                amount.getText().toString()
        ));

    }
}
