package com.example.myapplication.Store.ShowStorePackage;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseRoom.AppDatabase;
import com.example.myapplication.Store.ProductDetails.ProductDetailsActivity;
import com.example.myapplication.R;
import com.example.myapplication.DatabaseRoom.StoreTable;

import java.util.ArrayList;
import java.util.List;

public class ListProductsAdapter extends RecyclerView.Adapter<ListProductsAdapter.ProductDataHolder> {
    private List<String> products = new ArrayList<>();
    private Context context;

    private double totalIdleIncome = 0.0 , totalAmount = 0.0;


    AppDatabase db ;
    @NonNull
    @Override
    public ListProductsAdapter.ProductDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false
                );

        return new ProductDataHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDataHolder holder, int position) {
        db = AppDatabase.getInstance(context);
        totalIdleIncome = 0.0;
        totalAmount = 0.0;
        List<StoreTable> productDetails = new ArrayList<>();
        productDetails.addAll(db.storeDao().getProductDetails(products.get(position).toString()));

        for(int i = 0 ; i < productDetails.size() ; i++)
        {
            totalIdleIncome = totalIdleIncome + (Double.parseDouble(productDetails.get(i).getTOTAL_SELL()) - Double.parseDouble(productDetails.get(i).getTOTAL_GOMLA()));

        }
        holder.productName.setText(products.get(position));
        holder.productAmount.setText("الكمية : " + db.storeDao().getRestAmountForProduct(products.get(position)));


        holder.cardProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedProductModel.setProductName(products.get(position).toString());
                Intent intent = new Intent(context , ProductDetailsActivity.class);
                context.startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setList(List<String> products , Context context) {
        this.products = products;
        this.context = context;

        notifyDataSetChanged();
    }

    public String getProductPosition(int position)
    {
        return products.get(position);
    }


    public class ProductDataHolder extends RecyclerView.ViewHolder{
        CardView cardProduct;

        TextView productName , productAmount ;
        public ProductDataHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.tv_product_name);
            productAmount =itemView.findViewById(R.id.tv_product_amount);

            cardProduct = itemView.findViewById(R.id.card_product);

        }
    }
}
