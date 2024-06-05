package com.example.madcourse;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class DbActivity extends AppCompatActivity {
    public Context ct;
    public RecyclerView mRecyclerView;
    private SQLiteDatabase myDB;
    private LinkedList<Product> ProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db);

        ct = this;

        ImageButton btn_src = findViewById(R.id.btn_src);
        ImageButton btn_new_rec = findViewById(R.id.btn_new_rec);
        final EditText et_src = findViewById(R.id.et_src);

        btn_src.setOnClickListener(v -> {
            Cursor c1 = myDB.rawQuery(
             "SELECT * FROM products WHERE " +
                  "product_title LIKE '%" + et_src.getText() + "%'", null);
            int Column1 = c1.getColumnIndex("product_id");
            int Column2 = c1.getColumnIndex("product_title");
            int Column3 = c1.getColumnIndex("product_price");

            ProductList.clear();

            // Check if our result was valid.
            c1.moveToFirst();

            // Iterate through all Results
            do {
                int product_id = c1.getInt(Column1);
                String product_title = c1.getString(Column2);
                String product_price = c1.getString(Column3);
                ProductList.add( new Product(
                        product_id, product_title, Float.parseFloat(product_price)
                ));
            } while (c1.moveToNext());

            c1.close();
            ProductListAdapter AnotherAdapter = new ProductListAdapter(ct, ProductList);
            mRecyclerView.setAdapter(AnotherAdapter);
        });

        btn_new_rec.setOnClickListener(v -> {
            Intent intent = new Intent(this, RecordActivity.class);
            Bundle b = new Bundle();
            b.putInt("product_id", 0);
            intent.putExtras(b);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        PopulateRecycler();
    }

    public void PopulateRecycler() {
        mRecyclerView = findViewById(R.id.db_product_recycler);
        ProductList = new LinkedList<>();
        myDB = this.openOrCreateDatabase("products_db", MODE_PRIVATE, null);

        Cursor c = myDB.rawQuery("SELECT * FROM products", null);
        if (c.moveToFirst()) {
            int Column_ID = c.getColumnIndex("product_id");
            int Column_Title = c.getColumnIndex("product_title");
            int Column_Price = c.getColumnIndex("product_price");

            // Check if our result was valid.
            c.moveToFirst();
            // Loop through all Results
            do {
                int product_id = c.getInt(Column_ID);
                String product_title = c.getString(Column_Title);
                float product_price = c.getFloat(Column_Price);

                ProductList.add(  new Product(product_id, product_title, product_price)  );
            } while (c.moveToNext());

            c.close();
        }

        ProductListAdapter mAdapter = new ProductListAdapter(this, ProductList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
