package com.example.foodshop.UI.ShopCart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodshop.Model.Grocery;
import com.example.foodshop.R;
import com.example.foodshop.UI.Main.RecyclerViewMainAdapter;

import java.util.List;

public class RecyclerViewCartAdapter extends RecyclerView.Adapter<RecyclerViewCartAdapter.ViewHolder>{

    private List<Grocery> groceryList;
    private Context context;

    public RecyclerViewCartAdapter(Context context, List<Grocery> groceryList) {
        this.context = context;
        this.groceryList = groceryList;
    }

    @NonNull
    @Override
    public RecyclerViewCartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_shop_cart_cell, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewCartAdapter.ViewHolder holder, int position) {

        Grocery grocery = groceryList.get(position);
        holder.shopName.setText(grocery.getName());
        holder.shopPrice.setText(grocery.getPrice());
    }

    @Override
    public int getItemCount() {return groceryList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView shopName, shopPrice;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);

            context = ctx;
            shopName = itemView.findViewById(R.id.cellShopName);
            shopPrice = itemView.findViewById(R.id.cellShopPrice);
        }
    }
}
