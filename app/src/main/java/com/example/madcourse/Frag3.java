package com.example.madcourse;

import static android.content.Context.MODE_PRIVATE;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag3 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag3.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag3 newInstance(String param1, String param2) {
        Frag3 fragment = new Frag3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_frag3, null);

        SQLiteDatabase myDB = getActivity().openOrCreateDatabase
                ("products_db", MODE_PRIVATE, null);

        Button btn_create_db = root.findViewById(R.id.btn_create_db);
        btn_create_db.setOnClickListener(
                v -> {
                    myDB.execSQL("DROP TABLE IF EXISTS products");
                    myDB.execSQL("CREATE TABLE IF NOT EXISTS products " +
                            "(product_id integer primary key autoincrement, " +
                            "product_title text not null, " +
                            "product_price real)");

                    myDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "customers " +
                            "(customer_id integer primary key autoincrement, " +
                            "customer_name text not null, " +
                            "customer_city text)");

                    myDB.execSQL("CREATE TABLE IF NOT EXISTS " +
                            "suppliers " +
                            "(supplier_id integer primary key autoincrement, " +
                            "supplier_name text not null, " +
                            "supplier_city text)");

                    myDB.execSQL("INSERT INTO products " +
                            "(product_title, product_price) VALUES " +
                            "('Sony Playstation 3', '16.60')," +
                            "('LG 42.5 Monitor', '167.30')," +
                            "('Intel Core I7 7200 CPU', '500.30')," +
                            "('Apple iPhone 7 - 32 GB', '600.30')," +
                            "('Samsung Galaxy Note 3', '140.20')," +
                            "('Xiaomi Redmi Note 4', '125.30')," +
                            "('S-Band Microwave Radio Link', '900.20')," +
                            "('Solid State Power Amplifier', '3000.00')," +
                            "('Philips Headphones', '14.25')," +
                            "('Logitech microphone', '17.90')"
                    );
                }
        );
        return root;
    }
}