package com.example.madcourse;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {
    private SQLiteDatabase myDB;
    private int product_id;
    private Context ct;
    public EditText et_prodrec_id;
    public EditText et_prodrec_title;
    public EditText et_prodrec_price;
    public Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            product_id = b.getInt("product_id");
        } else {
            product_id = 0;
        }

        et_prodrec_id = findViewById(R.id.et_prodrec_id);
        et_prodrec_title = findViewById(R.id.et_prodrec_title);
        et_prodrec_price = findViewById(R.id.et_prodrec_price);
        btn_submit = findViewById(R.id.btn_submit);

        et_prodrec_id.setText(String.valueOf(product_id));

        if (product_id > 0) {
            myDB = this.openOrCreateDatabase("products_db", MODE_PRIVATE, null);

            Cursor c = myDB.rawQuery("SELECT * FROM" +
                    " products WHERE product_id = " + product_id, null);

            int Column1 = c.getColumnIndex("product_title");
            int Column2 = c.getColumnIndex("product_price");

            // Check if our result was valid.
            c.moveToFirst();
            String product_title = c.getString(Column1);
            String product_price = c.getString(Column2);

            et_prodrec_title.setText(product_title);
            et_prodrec_price.setText(product_price);
            c.close();
        }


        ct = this;
        btn_submit.setOnClickListener( v -> {
            myDB = ct.openOrCreateDatabase("products_db", MODE_PRIVATE, null);

            if (product_id == 0) {
                myDB.execSQL("INSERT INTO products " +
                        "(product_id, product_title, product_price) VALUES " +
                        "(null, '" +
                        et_prodrec_title.getText().toString() + "', '" +
                        et_prodrec_price.getText().toString() + "')"
                );
            } else if (product_id > 0) {
                myDB.execSQL("UPDATE products SET " +
                  "product_title = '" + et_prodrec_title.getText().toString() + "', " +
                  "product_price = '" + et_prodrec_price.getText().toString() + "' " +
                  "WHERE product_id = " + et_prodrec_id.getText().toString()
                );
            }
            finish();
        });
    }
}
