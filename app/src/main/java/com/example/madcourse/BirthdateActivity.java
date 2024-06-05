package com.example.madcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.Calendar;

public class BirthdateActivity extends AppCompatActivity {

    DatePickerDialog picker;
    TimePickerDialog picker_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthdate);

        final EditText eText = findViewById(R.id.bd_eText);
        final ImageButton btnGet = findViewById(R.id.bd_btn);
        Resources res = getResources();

        btnGet.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int day = cldr.get(Calendar.DAY_OF_MONTH);
            int month = cldr.get(Calendar.MONTH);
            int year = cldr.get(Calendar.YEAR);

            // date picker dialog
            picker = new DatePickerDialog(BirthdateActivity.this,
                    (view, year1, moy, dom) -> {
                        String date_rs = String.format(res.getString(R.string.date_formatted_str), year1, moy + 1, dom);
                        eText.setText(date_rs);
                    }, year, month, day
            );
            picker.show();
        });

        final EditText eText_time = findViewById(R.id.bd_eText_time);
        final ImageButton btnGet_time = findViewById(R.id.bd_btn_time);

        btnGet_time.setOnClickListener(v -> {
            final Calendar cldr = Calendar.getInstance();
            int hour = cldr.get(Calendar.HOUR_OF_DAY);
            int min = cldr.get(Calendar.MINUTE);

            // time picker dialog
            picker_time = new TimePickerDialog(BirthdateActivity.this,
                    (view, h, m) -> {
                        String ampm, time_rs;
                        if (h >= 0 && h < 12) {
                            ampm = "am";
                        } else {
                            ampm = "pm";
                        }

                        if (m < 10) {
                            time_rs = String.format(res.getString(R.string.time_formatted_str), h, "0" + m, ampm);
                        } else {
                            time_rs = String.format(res.getString(R.string.time_formatted_str), h, "" + m, ampm);
                        }
                        eText_time.setText(time_rs);
                    }, hour, min, true);
            picker_time.show();
        });
    }}