package com.example.okhttpdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button mBtnGet;
    private Button mBtnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGet = findViewById(R.id.btn_get);
        mBtnGet.setOnClickListener((view) -> get());
        mBtnPost = findViewById(R.id.btn_post);
        mBtnPost.setOnClickListener((view) -> post());
    }

    private void post() {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/JSON; charset=utf-8");
        Gson gson = new Gson();
        Person person = new Person("zhangsan", "pwd");
        String jsonStr = gson.toJson(person);
        RequestBody body = RequestBody.create(mediaType, jsonStr);
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .post(body)
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
            public void onResponse(Call call, Response response) throws IOException {
                final String result = response.body().string();
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "onSuccess: " + result,
                                Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    private void get() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://github.com/")
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

class Person {
    String id;
    String pwd;

    public Person() {
    }

    public Person(String id, String pwd) {
        this.id = id;
        this.pwd = pwd;
    }
}
