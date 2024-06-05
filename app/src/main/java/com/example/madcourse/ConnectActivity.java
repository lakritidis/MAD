package com.example.madcourse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ConnectActivity extends AppCompatActivity {

    private EditText ed_remote_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        ed_remote_content = findViewById(R.id.ed_urlcontent);
        EditText ed_url = findViewById(R.id.ed_url);

        Button btn_fetch = findViewById(R.id.btn_fetch);
        btn_fetch.setOnClickListener(
                v -> {
                    RemoteContentTask RCT = new RemoteContentTask();
                    String url = ed_url.getText().toString();
                    RCT.execute(url);
                }
        );

        ed_url.addTextChangedListener(new TextWatcher()  {
                public void afterTextChanged(Editable s) { }
                public void beforeTextChanged(CharSequence s, int start, int count, int after)  { }
                public void onTextChanged(CharSequence s, int start, int before, int count)  {
                    ed_remote_content.setText("");
                }
            }
        );

        Button btn_fetchvolley = findViewById(R.id.btn_fetchvolley);
        btn_fetchvolley.setOnClickListener(
                v -> {
                    RequestQueue queue = Volley.newRequestQueue(ConnectActivity.this);
                    String url = ed_url.getText().toString();

                    StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                            response -> {
                                System.out.println("Fetch OK");
                                ed_remote_content.setText(response);
                            },
                            error -> {
                                System.out.println("error: " + error.toString());
                                ed_remote_content.setText(error.toString());
                            });
                    queue.add(stringRequest);
                }
        );

    }

    private class RemoteContentTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            WebRequest request = new WebRequest(urls[0]);
            return request.GetResponse();
        }

        @Override
        protected void onPostExecute(String result) {
            System.out.println(result);
            ed_remote_content.setText(result);
        }
    }
}