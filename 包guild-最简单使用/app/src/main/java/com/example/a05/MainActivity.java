package com.example.a05;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageLoad);
    }

    public void onLoadImageClick(View v){
//        loadUrlImage("https://www.imooc.com/static/img/index/logo2020.png");

        glideLoadImage("https://www.imooc.com/static/img/index/logo2020.png");
    }


    /**
     * 加载网络图片
     */
    private void loadUrlImage(String str) {
        new Thread(){
            @Override
            public void run() {
                super.run();

                Message msg = new Message();
                try {
                    URL url = new URL(str);

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");

                    int code = conn.getResponseCode();

                    if (code == 200) {
                        InputStream is = conn.getInputStream();
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        msg.what = 1;
                        msg.obj = bitmap;
                    }

                } catch (IOException e) {
                    msg.what = -1;

                    throw new RuntimeException(e);
                } finally {
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case 1:
                    // 更新UI
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                default:
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    break;
            }
        }
    };



    /**
     * 通过glide加载网络图片
     */
    private void glideLoadImage(String img){
        Glide.with(this).load(img)
                .apply(GlideOptonsUtils.baseOptions())
                .into(imageView);
    }


}