package com.example.myapplication.Store.ProductDetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DatabaseRoom.StoreTable;
import com.example.myapplication.R;
import com.example.myapplication.Store.UpdateSingleDetailForProduct.SelectedProductSingleDetailModel;
import com.example.myapplication.Store.UpdateSingleDetailForProduct.UpdateSingleDetailProductActivity;

import java.util.ArrayList;
import java.util.List;

public class ListProductDetailsAdapter extends RecyclerView.Adapter<ListProductDetailsAdapter.ProductDetailsDataHolder> {
    private List<StoreTable> productDetails = new ArrayList<>();
    private Context context;




    @NonNull
    @Override
    public ListProductDetailsAdapter.ProductDetailsDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_details_item, parent, false
                );

        return new ProductDetailsDataHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProductDetailsDataHolder holder, int position) {


        holder.productName.setText(productDetails.get(position).getPRODUCT_NAME());
        holder.mowaredName.setText("اسم المورد : " + productDetails.get(position).getNAME_OF_MOWARED());
        holder.productAmount.setText("الكمية : " + productDetails.get(position).getPRODUCT_AMOUNT());
        holder.gomlaPrice.setText("سعر الجملة : " + productDetails.get(position).getPRODUCT_GOMLA_PRICE());
        holder.sellPrice.setText("سعر البيع : " + productDetails.get(position).getPRODUCT_SELL_PRICE());
        double totalInGomla = Double.parseDouble(productDetails.get(position).getTOTAL_GOMLA());
        double totalInSell = Double.parseDouble(productDetails.get(position).getTOTAL_SELL());
        holder.incomeIdle.setText("الربح المثالي :  " + (totalInSell - totalInGomla) + " جنيها ");
        holder.date.setText("التاريخ : " + productDetails.get(position).getDATE());
        holder.day.setText("اليوم : " + productDetails.get(position).getDAY());
        holder.updateProductDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedProductSingleDetailModel.setMowaredName(productDetails.get(position).getNAME_OF_MOWARED());
                SelectedProductSingleDetailModel.setProductName(productDetails.get(position).getPRODUCT_NAME());
                SelectedProductSingleDetailModel.setProductGomlaPrice(productDetails.get(position).getPRODUCT_GOMLA_PRICE());
                SelectedProductSingleDetailModel.setProductSellPrice(productDetails.get(position).getPRODUCT_SELL_PRICE());
                SelectedProductSingleDetailModel.setProductAmount(productDetails.get(position).getPRODUCT_AMOUNT());

                Intent intent = new Intent(context , UpdateSingleDetailProductActivity.class);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return productDetails.size();
    }

    public void setList(List<StoreTable> productDetails , Context context) {
        this.productDetails = productDetails;
        this.context = context;

        notifyDataSetChanged();
    }

    public StoreTable getProductDetailPosition(int position)
    {
        return productDetails.get(position);
    }


    public class ProductDetailsDataHolder extends RecyclerView.ViewHolder{
        CardView cardProductDetail;

        TextView productName , productAmount , gomlaPrice , sellPrice , incomeIdle , mowaredName ,date , day;
        Button updateProductDetailsBtn;
        public ProductDetailsDataHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productAmount =itemView.findViewById(R.id.productAmount);
            mowaredName = itemView.findViewById(R.id.mowaredName);
            gomlaPrice = itemView.findViewById(R.id.gomlaPrice);
            sellPrice = itemView.findViewById(R.id.sellPrice);
            incomeIdle = itemView.findViewById(R.id.incomeIdle);
            date = itemView.findViewById(R.id.date);
            day = itemView.findViewById(R.id.day);
            cardProductDetail = itemView.findViewById(R.id.card_product_detail);
            updateProductDetailsBtn = itemView.findViewById(R.id.updateProductDetailsBtn);


        }
    }
}