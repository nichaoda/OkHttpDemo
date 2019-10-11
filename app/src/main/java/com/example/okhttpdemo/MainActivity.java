package com.example.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mBtnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGet = findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener((view) -> get());
    }

    private void get() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.csdn.net/")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                            Toast.makeText(MainActivity.this, "onFailure",
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG, e.getMessage());
                        }
                );
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "onSuccess: " + result,
                                Toast.LENGTH_SHORT).show()
                );
            }
        });
    }
}
