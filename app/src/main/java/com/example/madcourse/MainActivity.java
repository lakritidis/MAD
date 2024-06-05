package com.example.madcourse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            String str = savedInstanceState.getString("strToSave");
            Toast.makeText(MainActivity.this, "I have some data" + str, Toast.LENGTH_LONG).show();
        }

        // Set properties for EditText
        EditText et = findViewById(R.id.edit_surname);
        registerForContextMenu(et);

        EditText et_f = findViewById(R.id.edit_firstname);
        registerForContextMenu(et_f);

        et.setText(R.string.cap_hello);
//        et.setEnabled(false);
        et.setVisibility(View.VISIBLE);
        et.setTextSize(20.0F);
//        et.setTextColor(Color.parseColor("#bb2539"));
//        et.setBackgroundColor(Color.GRAY);

        // Set properties for Checkbox
        CheckBox ch_rem = findViewById(R.id.ch_remember);
        ch_rem.setChecked(true);

        // Set properties for Radio Button
        RadioButton rd = findViewById(R.id.rd_large);
        rd.setChecked(true);

        // Get properties for EditText
        String surname = et.getText().toString();
        Log.d(MainActivity.class.getSimpleName(), surname);

        // Get if a check box is checked
        Boolean is_checked = ch_rem.isChecked();

        // get the selection from a Radio Group
        RadioGroup rg = findViewById(R.id.size_radiogroup);
        int sel_radio_id = rg.getCheckedRadioButtonId();
        RadioButton selected_radio_button = findViewById(sel_radio_id);
        String radio_selection = selected_radio_button.getText().toString();
        Log.d(MainActivity.class.getSimpleName(), "Your selection is " + radio_selection);

        // Fill the Spinner with options
        String [] spinner_options = {"Mr", "Mrs", "Miss", "Dr", "Professor", "General"};
        Spinner spinner = findViewById(R.id.spinner_id);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_options = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinner_options);

        // Specify the layout to use when the list of choices appears
        adapter_options.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter_options);

        // Set the default selected item
        spinner.setSelection(0);

        // Get the selected item index
        int sel_index = spinner.getSelectedItemPosition();

        // Get the selected item text
        String sel_elem = spinner.getSelectedItem().toString();
        Log.d(MainActivity.class.getSimpleName(), "Spinner selection: " + sel_index + ": " + sel_elem);

        // Display a message with Toast
        Toast.makeText(MainActivity.this, "app started successfully", Toast.LENGTH_LONG).show();

        Button btn_submit = findViewById(R.id.btn_submit);

        // A simplified (still equivalent) onClickListener with Lambda expression
        btn_submit.setOnClickListener((View v) -> {
            Toast.makeText(MainActivity.this, "the button was pressed", Toast.LENGTH_LONG).show();
        });

        // An onLongClickListener for our button
        btn_submit.setOnLongClickListener((View v) -> {
            EditText et_surname = findViewById(R.id.edit_surname);
            String current_surname = et_surname.getText().toString();

            EditText et_firstname = findViewById(R.id.edit_firstname);
            String current_firstname = et_firstname.getText().toString();

            CheckBox cb_rem = findViewById(R.id.ch_remember);
            boolean current_rem = cb_rem.isChecked();

            String data = current_surname + " " + current_surname + ": " + current_rem;
            Toast.makeText(MainActivity.this, "form data:" + data, Toast.LENGTH_LONG).show();

            return false;
        });

        Button btn_citadel = findViewById(R.id.btn_citadel);
        btn_citadel.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, AnotherActivity.class);

                    Bundle b = new Bundle();

                    b.putString("monument", et.getText().toString());
                    b.putString("footballclub", "Liverpool");
                    b.putInt("temperature", 20);
                    b.putIntArray("product_ids", new int[]{20,308,407} );
                    b.putFloat("price", 10.5F);

                    MyIntent.putExtras(b);

                    startActivity(MyIntent);
                }
        );

        Button btn_recycler = findViewById(R.id.btn_recycler);
        btn_recycler.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, RecyclerActivity.class);
                    startActivity(MyIntent);
                }
        );

        Button btn_connect = findViewById(R.id.btn_connect);
        btn_connect.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, ConnectActivity.class);
                    startActivity(MyIntent);
                }
        );

        Button btn_tab = findViewById(R.id.btn_tab);
        btn_tab.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, TabActivity.class);
                    startActivity(MyIntent);
                }
        );

        Button btn_alert = findViewById(R.id.btn_alert);
        btn_alert.setOnClickListener(
                v -> {
                     ConfirmDelete cd = new ConfirmDelete();
                     cd.show(getSupportFragmentManager(), "delete");
                }
        );

        Button btn_birthdate = findViewById(R.id.btn_birthdate);
        btn_birthdate.setOnClickListener(
                v -> {
                    Intent MyIntent = new Intent(MainActivity.this, BirthdateActivity.class);
                    startActivity(MyIntent);
                }
        );

        Button btn_products_app = findViewById(R.id.btn_product_app);
        btn_products_app.setOnClickListener(
                v -> {
                    Intent MyIntent = new Intent(MainActivity.this, DbActivity.class);
                    startActivity(MyIntent);
                }
        );

        Button btn_manager = findViewById(R.id.btn_manager);
        btn_manager.setOnClickListener(
                v-> {
                    Intent MyIntent = new Intent(MainActivity.this, ManagerActivity.class);
                    startActivity(MyIntent);
                }
        );
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mainmenu_add) {
            Toast.makeText(MainActivity.this,
                    item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mainmenu_search) {
            Toast.makeText(MainActivity.this,
                    item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.mainmenu_settings) {
            Toast.makeText(MainActivity.this,
                    item.getTitle().toString(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("strToSave", "Save Me");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        if(v.getId() == R.id.edit_surname) {
            inflater.inflate(R.menu.menu_context_2, menu);
        } else if (v.getId() == R.id.edit_firstname) {
            inflater.inflate(R.menu.menu_context, menu);
        }
    }

    @Override public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.contextmenu_cancel) {
            Toast.makeText(MainActivity.this, "You pressed cancel.",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.contextmenu_ok) {
            Toast.makeText(MainActivity.this, "You pressed OK.",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.contextmenu_copy) {
            Toast.makeText(MainActivity.this, "You pressed Copy.",
                    Toast.LENGTH_SHORT).show();
        } else if (id == R.id.contextmenu_paste) {
            Toast.makeText(MainActivity.this, "You pressed Paste.",
                    Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }
}
