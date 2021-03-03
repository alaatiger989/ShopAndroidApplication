package com.example.myapplication.Store.ProductDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;
import com.example.myapplication.R;
import com.example.myapplication.Store.ShowStorePackage.SelectedProductModel;
import com.example.myapplication.Store.ShowStorePackage.ShowStoreActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {


    RecyclerView listProductDetailsRecyclerView;
    TextView totalAmount , totalIncome;
    AppDatabase db;
    Double totalIdleIncome ,totalAmountProduct ,newAmountAfterDeletion =0.0;
    ListProductDetailsAdapter listProductDetailsAdapter;
    String amountDeleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTotalAmountAndIdleIncome();
        setContentView(R.layout.activity_product_details);
        setTitle(SelectedProductModel.getProductName());
        totalAmount = findViewById(R.id.totalAmount);
        totalIncome = findViewById(R.id.totalIncome);
        listProductDetailsRecyclerView = findViewById(R.id.list_products_recyclerView);
        totalAmount.setText("الكمية المتبقية : " + totalAmountProduct);
        totalIncome.setText("اجمالي الربح المثالي : " + totalIdleIncome);

        initRecyclerView();


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback( 0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                String productName = listProductDetailsAdapter.getProductDetailPosition(viewHolder.getAdapterPosition()).getPRODUCT_NAME();
                String productGomlaPrice = listProductDetailsAdapter.getProductDetailPosition(viewHolder.getAdapterPosition()).getPRODUCT_GOMLA_PRICE();
                String nameOfMowared = listProductDetailsAdapter.getProductDetailPosition(viewHolder.getAdapterPosition()).getNAME_OF_MOWARED();
                amountDeleted = listProductDetailsAdapter.getProductDetailPosition(viewHolder.getAdapterPosition()).getPRODUCT_AMOUNT();
                deleteSingleDetailFromDB(productName , productGomlaPrice , nameOfMowared);
                Toast.makeText(getApplicationContext() ,"لقد تم حذف المنتج " + listProductDetailsAdapter.getProductDetailPosition(viewHolder.getAdapterPosition()) .getPRODUCT_NAME()+ " ينجاح " , Toast.LENGTH_LONG).show();
                initRecyclerView();
            }
        }).attachToRecyclerView(listProductDetailsRecyclerView);
    }

    private void deleteSingleDetailFromDB(String productName , String productGomlaPrice , String nameOfMowared)
    {
        db.storeDao().deleteSingleDetail(productName, productGomlaPrice , nameOfMowared);
        updateProduct();
    }

    private void updateProduct() {
        getTotalAmountAndIdleIncome();
        newAmountAfterDeletion = 0.0;
        newAmountAfterDeletion = totalAmountProduct - Double.parseDouble(amountDeleted);
        db.storeDao().updateProductRest(String.valueOf(newAmountAfterDeletion) , SelectedProductModel.getProductName());
        totalAmount.setText("الكمية المتبقية : " + newAmountAfterDeletion);
        totalIncome.setText("اجمالي الربح المثالي : " + totalIdleIncome);
    }

    private void initRecyclerView() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        List<StoreTable> productDetails = new ArrayList<>();
        productDetails.addAll(db.storeDao().getProductDetails(SelectedProductModel.getProductName()));
        listProductDetailsAdapter = new ListProductDetailsAdapter();
        listProductDetailsAdapter.setList(productDetails , ProductDetailsActivity.this);
        listProductDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listProductDetailsRecyclerView.setAdapter(listProductDetailsAdapter);
    }

    private void getTotalAmountAndIdleIncome() {
        db = AppDatabase.getInstance(getApplicationContext());
        totalIdleIncome = 0.0;
        totalAmountProduct = 0.0;
        List<StoreTable> productDetails = new ArrayList<>();
        productDetails.addAll(db.storeDao().getProductDetails(SelectedProductModel.getProductName()));

        for(int i = 0 ; i < productDetails.size() ; i++)
        {
            totalIdleIncome = totalIdleIncome + (Double.parseDouble(productDetails.get(i).getTOTAL_SELL()) - Double.parseDouble(productDetails.get(i).getTOTAL_GOMLA()));
        }
        if(productDetails.size() == 0)
        {
            Toast.makeText(getApplicationContext() , "لقد تم حذف جميع التقاصيل الخاصة بالمنتج بالكامل" , Toast.LENGTH_LONG).show();
            newAmountAfterDeletion = 0.0;
        }
        else{
            totalAmountProduct = Double.parseDouble(productDetails.get(0).getPRODUCT_REST().toString());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext() , ShowStoreActivity.class);
        startActivity(intent);
    }
}