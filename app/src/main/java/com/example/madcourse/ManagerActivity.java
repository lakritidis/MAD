package com.example.madcourse;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ManagerActivity extends AppCompatActivity implements LocationListener {
    private TextView tv_coord;
    private TextView tv_altit;
    private TextView tv_speed;
    private TextView tv_brng;
    private EditText ed_response;

    double latitude;
    double longitude;
    double altitude;
    double speed;
    double bearing;

    private String Android_ID;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        tv_coord = findViewById(R.id.tv_coord);
        tv_altit = findViewById(R.id.tv_altit);
        tv_speed = findViewById(R.id.tv_speed);
        tv_brng = findViewById(R.id.tv_brng);
        ed_response = findViewById(R.id.ed_response);

        StartLocationManager();
//        DisplayNotification("location changed", "your location has changed");

        mHandler = new Handler();

        Android_ID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        Button btn_start = findViewById(R.id.btn_start_task);
        btn_start.setOnClickListener( v -> mStatusChecker.run() );

        Button btn_stop = findViewById(R.id.btn_stop_task);
        btn_stop.setOnClickListener( v -> stopRepeatingTask() );

    }

    private void StartLocationManager() {
        // Location Manager initializes the GPS locator
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    StartLocationManager();
                } else {
                    tv_coord.setText(R.string.na);
                    tv_altit.setText(R.string.na);
                    tv_speed.setText(R.string.na);
                    tv_brng.setText(R.string.na);
                }
            });


    // Every time the GPS position changes, update local latitude and longitude variables
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        altitude = location.getAltitude();
        speed = location.getSpeed();
        bearing = location.getBearing();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRepeatingTask();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                Resources res = getResources();
                String msg = String.format(res.getString(R.string.send_key), latitude, longitude);
                Toast.makeText(ManagerActivity.this, msg, Toast.LENGTH_LONG).show();

                RequestQueue queue = Volley.newRequestQueue(ManagerActivity.this);
                String url = "http://mad.mywork.gr/userlocation.php?" +
                        "uid=" + Android_ID +
                        "&lat=" + latitude +
                        "&lon=" + longitude +
                        "&alt=" + altitude +
                        "&spd=" + speed +
                        "&brn=" + bearing;

                StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                        response -> {
                            ed_response.setText(response);
                        },
                        error -> {
                            ed_response.setText(error.toString());
                        });
                queue.add(stringRequest);

                tv_coord.setText(String.format(res.getString(R.string.coord_key), latitude, longitude));
                tv_altit.setText(String.format(res.getString(R.string.double_key), altitude));
                tv_speed.setText(String.format(res.getString(R.string.double_key), speed));
                tv_brng.setText(String.format(res.getString(R.string.double_key), bearing));

            } finally {
                // 100% guarantee that this always happens, even if
                // your update method throws an exception
                int mInterval = 10000;
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

}