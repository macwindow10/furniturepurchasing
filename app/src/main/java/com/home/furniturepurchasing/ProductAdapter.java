package com.home.furniturepurchasing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>
        implements View.OnClickListener {

    private Context context;
    private RecyclerView recyclerView;
    private List<Product> products;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View view) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewProduct;
        public TextView textViewCategory;
        public TextView textViewPrice;

        public MyViewHolder(View view) {
            super(view);
            textViewProduct = view.findViewById(R.id.textview_product);
            textViewCategory = view.findViewById(R.id.textview_category);
            textViewPrice = view.findViewById(R.id.textview_price);
        }
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item, parent, false);
        return new ProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.MyViewHolder holder, int position) {
        Product product = this.products.get(position);
        holder.textViewProduct.setText("Product : " + product.name);
        holder.textViewCategory.setText("Category : " + product.getCategoryName());
        holder.textViewPrice.setText("Price : " + product.price);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
