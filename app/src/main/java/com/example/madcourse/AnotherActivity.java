package com.example.madcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

public class AnotherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);

        if (savedInstanceState != null) {
            String str = savedInstanceState.getString("strToSave");
            Toast.makeText(AnotherActivity.this, "I have some data" + str, Toast.LENGTH_LONG).show();
        }
        Bundle b = getIntent().getExtras();
        ImageView img_view = findViewById(R.id.img_view);

        String draw_monument = null;
        if (b != null) {
            draw_monument = b.getString("monument");
            int pid = b.getInt("product_id");
            float price = b.getFloat("price");

            if (draw_monument != null) {
                switch (draw_monument) {
                    case "white tower":
                        img_view.setImageResource(R.drawable.whitetower);
                        break;
                    case "citadel":
                        img_view.setImageResource(R.drawable.acropolis);
                        break;
                    case "cat03":
                        img_view.setImageResource(R.drawable.prod_cat03);
                        break;
                }
            }
        }

        Button btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(
                v -> {
                    AnotherActivity.this.finish();

//                    Intent MyIntent = new Intent(AnotherActivity.this, MainActivity.class);
//                    startActivity(MyIntent);
                }
        );


        ImageButton btn_email = findViewById(R.id.btn_email);
        btn_email.setOnClickListener(
                v -> {
                    PopupMenu popup = new PopupMenu(AnotherActivity.this, btn_email);
                    popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());

                    popup.setOnMenuItemClickListener(item -> {
                        int id = item.getItemId();
                        if (id == R.id.popupmenu_forward) {
                            Toast.makeText(AnotherActivity.this, "Forwarding email...",
                                    Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.popupmenu_replyall) {
                            Toast.makeText(AnotherActivity.this, "Replying to all...",
                                    Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        return false;
                    });
                    popup.show();
                }
        );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("strToSave", "Save Me");
    }
}