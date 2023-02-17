package com.example.a02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private final OkHttpClient client = new OkHttpClient();
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.tvContent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mentGet:
                get();
                break;
            case R.id.menuResponse:
                response();
                break;
            case R.id.submitPost:
                post();
        }
        return super.onOptionsItemSelected(item);
    }

    private void post() {
        Request.Builder builder = new Request.Builder();
        builder.url("http://144.202.43.41:81/viewing/v1/login/submit");

        builder.post(RequestBody.create("username=123&password=123", MediaType.parse("application/x-www-form-urlencoded")));

        Request request = builder.build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (response.isSuccessful()){

                    String string = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(string);
                        }
                    });

                }
            }
        });
    }

    private void response() {
        Request.Builder builder = new Request.Builder();
        builder.url("https://github.com/zoujingli/ThinkAdmin/blob/v6/readme.md");

        Request request = builder.build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "onFailure: ", e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                int code = response.code();
                Headers headers = response.headers();
                String content = response.body().string();

                String contentType = headers.get("Content-Type");

                Log.d(TAG, "onResponse: " + contentType);

                final StringBuilder buf = new StringBuilder();
                buf.append("code: ").append(code).append("\n");
                buf.append("headers: ").append(headers).append("\n");
                buf.append("content: ").append(content).append("\n");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(buf.toString());
                    }
                });

            }
        });
    }

    private void get() {

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(new Runnable() {
            @Override
            public void run() {
                Request.Builder builder = new Request.Builder();

                builder.url("https://github.com/zoujingli/ThinkAdmin/blob/v6/readme.md");

                Request request = builder.build();

                Call call = client.newCall(request);

                try {
                    Response response = call.execute();

                    if (response.isSuccessful()){

                        String string = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.setText("aaaaa");
                            }
                        });
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        executor.shutdown();

    }
}