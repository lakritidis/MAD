package com.example.madcourse;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.Locale;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {
    Context ct;
    LayoutInflater mInflater;
    LinkedList<Product> mProductList;

    // Provide a reference to the views for each data item. Complex data items may need more than
    // one view per item, and you provide access to all the views for a data item in a view holder.
    public static class ProductListViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View item_layout;
        public TextView tv_product_id;
        public TextView tv_product_title;
        public TextView tv_product_price;
        public TextView tv_product_qty;
        public ImageButton btn_inc;
        public ImageButton btn_dec;
        public ImageButton btn_del;

        public ProductListViewHolder(View v, Context ctx) {
            super(v);
            item_layout = v;
            tv_product_id = v.findViewById(R.id.tv_product_id);
            tv_product_title = v.findViewById(R.id.tv_product_title);
            tv_product_price = v.findViewById(R.id.tv_product_price);
            tv_product_qty = v.findViewById(R.id.tv_product_qty);
            btn_inc = v.findViewById(R.id.btn_inc);
            btn_dec = v.findViewById(R.id.btn_dec);
            btn_del = v.findViewById(R.id.btn_del);

            btn_inc.setOnClickListener(
                v1 -> {
                    int upd_qty = parseInt(tv_product_qty.getText().toString()) + 1;
                    tv_product_qty.setText(String.valueOf(upd_qty));
                }
            );

            btn_dec.setOnClickListener(
                v12 -> {
                    int upd_qty = parseInt(tv_product_qty.getText().toString()) - 1;
                    if (upd_qty >= 0) {
                        tv_product_qty.setText(String.valueOf(upd_qty));
                    }
                }
            );

            btn_del.setOnClickListener(
                v_del -> {
                    System.out.println("Delete button for " + tv_product_id.getText().toString());

                    ConfirmDelete cd = new ConfirmDelete( parseInt(tv_product_id.getText().toString()));
                    cd.show( ((AppCompatActivity)ctx).getSupportFragmentManager(), "delete");
                }
            );

        }
    }

    public ProductListAdapter(Context context, LinkedList<Product> ProductList) {
        ct = context;
        mInflater = LayoutInflater.from(context);
        mProductList = ProductList;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view from layout
        View mItemView = mInflater.inflate(R.layout.product_layout, parent, false);
        return new ProductListViewHolder(mItemView, ct);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, final int position) {
        // Retrieve the data for that position
        Product mCurrent = mProductList.get(position);

        // Add the data to the view
        holder.tv_product_id.setText(String.valueOf(mCurrent.getId()));
        holder.tv_product_qty.setText("0");
        holder.tv_product_title.setText(mCurrent.getTitle());
        holder.tv_product_price.setText(
            String.format(Locale.getDefault(), "%.2f $", mCurrent.getPrice()));

        if (ct.getClass().getSimpleName().equals("DbActivity")) {
            holder.btn_inc.setVisibility(View.GONE);
            holder.btn_dec.setVisibility(View.GONE);
            holder.tv_product_qty.setVisibility(View.GONE);

            holder.btn_del.setVisibility(View.VISIBLE);

            holder.item_layout.setOnClickListener(view -> {
                Intent intent = new Intent(ct, RecordActivity.class);

                Bundle b = new Bundle();
                b.putInt("product_id", mCurrent.getId());
                intent.putExtras(b);

                ct.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        // Return the number of data items to display
        return mProductList.size();
    }
}
