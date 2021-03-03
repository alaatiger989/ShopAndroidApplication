package com.example.myapplication.Store.ShowStorePackage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.DatabaseRoom.StoreTable;
import com.example.myapplication.R;
import com.example.myapplication.Store.AddNewProductPackage.StoreActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowStoreActivity extends AppCompatActivity {

    private RecyclerView listProductsRecyclerView;

    private ListProductsAdapter listProductsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_store);
        setTitle("المخزن");

        initRecyclerView();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback( 0 , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                listProductsAdapter.getProductPosition(viewHolder.getAdapterPosition());
                deleteProductFromDB(listProductsAdapter.getProductPosition(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext() ,"لقد تم حذف المنتج " + listProductsAdapter.getProductPosition(viewHolder.getAdapterPosition()) + " ينجاح " , Toast.LENGTH_LONG).show();
                initRecyclerView();
            }
        }).attachToRecyclerView(listProductsRecyclerView);




    }

    private void initRecyclerView()
    {
        List<String> products = new ArrayList<>();
        listProductsRecyclerView = findViewById(R.id.list_products_recyclerView);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        products.addAll(db.storeDao().getUniqueNamesForProducts());

        listProductsAdapter = new ListProductsAdapter();
        listProductsAdapter.setList(products , getApplicationContext());
        listProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listProductsRecyclerView.setHasFixedSize(true);
        listProductsRecyclerView.setAdapter(listProductsAdapter);
    }
    private void deleteProductFromDB(String productName) {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        db.storeDao().deleteProductByName(productName);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext() , StoreActivity.class);
        startActivity(intent);
    }
}