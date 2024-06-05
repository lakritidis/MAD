package com.example.madcourse;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Frag1 extends Fragment {
    public Frag1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        @SuppressLint("InflateParams")
        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment_frag1, null);

        EditText ed_urlcontent = root.findViewById(R.id.ed_urlcontent_frag);
        EditText ed_url = root.findViewById(R.id.ed_url_frag);

        Button btn_fetchvolley_frag = root.findViewById(R.id.btn_fetchvolley_frag);
        btn_fetchvolley_frag.setOnClickListener(
                v -> {
                    System.out.println("Event fired");
                    RequestQueue queue = Volley.newRequestQueue(Frag1.this.requireContext());
                    String url = ed_url.getText().toString();

                    StringRequest stringRequest = new StringRequest (Request.Method.GET, url,
                            response -> {
                                System.out.println("Data fetched");
                                ed_urlcontent.setText(response);
                            },
                            error -> {
                                System.out.println("Error: " + error.toString());
                                ed_urlcontent.setText(error.toString());
                            });
                    queue.add(stringRequest);
                }
        );

        Button btn_fetchexec_frag = root.findViewById(R.id.btn_fetchexec_frag);
        btn_fetchexec_frag.setOnClickListener(
                v -> {

                    System.out.println("Event fired");
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());

                    executor.execute(() -> {

                        //Background work here
                        String url = ed_url.getText().toString();
                        WebRequest request = new WebRequest(url);

                        String finalResponse = request.GetResponse();
                        handler.post(() -> {
                            // UI Thread work here
                            ed_urlcontent.setText(finalResponse);
                        });
                    });
                });

        return root;
    }
}